package com.tim10.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tim10.domain.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, Long>{
	
	Authority findByRole(String role);
	<S extends Authority> S save(S authority);

}
