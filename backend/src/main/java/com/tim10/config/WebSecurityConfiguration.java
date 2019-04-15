package com.tim10.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.tim10.security.TokenUtils;
import com.tim10.security.auth.RestAuthenticationEntryPoint;
import com.tim10.security.auth.TokenAuthenticationFilter;
import com.tim10.service.CustomUserDetailsService;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	// Implementacija PasswordEncoder-a koriscenjem BCrypt hashing funkcije.
	// BCrypt po defalt-u radi 10 rundi hesiranja prosledjene vrednosti.
 	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Autowired
	private CustomUserDetailsService jwtUserDetailsService;

	// Neautorizovani pristup zastcenim resursima
	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	// Definisemo nacin autentifikacije
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		String password = passwordEncoder().encode("user");
        auth
            .inMemoryAuthentication()
            .withUser("user").password(password).authorities("ROLE_USER")
            .and()
            .withUser("user1").password(password).authorities("ROLE_USER", "ROLE_ADMIN");

   
	    
		//auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}
	

	@Autowired
	TokenUtils tokenUtils;
	
	/*
	 * .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			
			// za neautorizovane zahteve posalji 401 gresku
			.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
			
			.authorizeRequests()
	
			.antMatchers("/**").permitAll()
			
			// svaki zahtev mora biti autorizovan
			.anyRequest().authenticated().and()
			
			// presretni svaki zahtev filterom
			.addFilterBefore(new TokenAuthenticationFilter(tokenUtils, jwtUserDetailsService), BasicAuthenticationFilter.class);
			// komunikacija izmedju klijenta i servera je stateless

	 * */

	// Definisemo prava pristupa odredjenim URL-ovima
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		
		http
			.authorizeRequests()
			
			.antMatchers("/").permitAll()
			.antMatchers("/auth/**").permitAll()

			.antMatchers("/api/hotels").hasAnyRole("USER", "ADMIN")
			.antMatchers("/api/users").hasAnyRole("USER", "ADMIN")
			.antMatchers("/api/rentACars").hasAnyRole("ADMIN")
			
			
			.anyRequest().authenticated()
			.and()
			/*
			.and()
			.formLogin().loginPage("auth/login").permitAll()
			.and()
			.logout().permitAll();
			*/
			
			.addFilterBefore(new TokenAuthenticationFilter(tokenUtils, jwtUserDetailsService), BasicAuthenticationFilter.class);;
			
		
			
		http.csrf().disable();
	}

	// Generalna bezbednost aplikacije
	@Override
	public void configure(WebSecurity web) throws Exception {
		// TokenAuthenticationFilter ce ignorisati sve ispod navedene putanje
		web.ignoring().antMatchers("/");
		//i tako dalje na tu temu...
	}

}