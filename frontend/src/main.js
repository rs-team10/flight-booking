import Vue from 'vue'
import './plugins/vuetify'
import Vuetify from 'vuetify'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import Vuelidate from 'vuelidate'
import VueSweetalert2 from 'vue-sweetalert2';
import VueCharts from 'vue-chartjs'
//import VuetifyGoogleAutocomplete from 'vuetify-google-autocomplete';
import * as VueGoogleMaps from 'vue2-google-maps'

Vue.prototype.$axios = axios
Vue.config.productionTip = false  

Vue.use(Vuetify)
Vue.use(Vuelidate)
Vue.use(VueSweetalert2);
Vue.use(require('vue-moment'));

Vue.use(VueCharts)
Vue.use(require('vue-moment'));

Vue.use(VueGoogleMaps, {
  load: {
    key: 'AIzaSyCoYMbcCf9ot8Dyoy5oxEFDaT5cVitKME4',
    libraries: 'places',
  },
})

/*
Vue.use(VuetifyGoogleAutocomplete, {
  apiKey: 'AIzaSyCoYMbcCf9ot8Dyoy5oxEFDaT5cVitKME4',
});
*/

router.beforeEach((to, from, next) => {
  if(to.matched.some(record => record.meta.requiresAuth)){
    if(localStorage.getItem('token') == null){
      //neulogovani korisnik zeli da pristupi stranici za admina/reg korisnika
      //preusmeriti ga na login
      next({
        path: '/login'
      })
    }else{
      //korisnik je ulogovan
      //zeli da pristupi stranici za registrovanog korisnika
      if(to.matched.some(record => record.meta.is_registered_user)){
        //proveriti da li je registrovani korisnik


          //zeli da rezervise hotel ili auto
          if(to.matched.some(record => record.meta.is_reservation_active)){
            //mora se proveriti da li je rezervacija aktivna (da li je rezervisao let pre toga)
            if(localStorage.getItem('groupResId') != null)
              next()
            else
              next(false)     //mozda da se preusmeri na flight res???
          }else{
            next()
          }
        
      }
      //zeli da pristupi stranici za airline admina
      else if(to.matched.some(record => record.meta.is_airline_admin)){
        next()
      }
      //zeli da pristupi stranici za hotel admina
      else if(to.matched.some(record => record.meta.is_hotel_admin)){
        if(localStorage.getItem("role") == "ROLE_HOTEL_ADMIN")
          next()
        else
          next(false)
      } 
      //zeli da pristupi stranici za rentacar admina
      else if(to.matched.some(record => record.meta.is_rentacar_admin)){
        next()
      }
      //zeli da pristupi stranici za sys admina
      else if(to.matched.some(record => record.meta.is_sys_admin)){
        if(localStorage.getItem("role") == "ROLE_SYSTEM_ADMIN")
          next()
        else
          next(false)    
      }
    }
  } else if(to.matched.some(record => record.meta.guest)){
      if(localStorage.getItem('token') == null){
        next()    //ako korisnika nije ulogovan moze da nastavi dalje
      }else{
        //ulogovan je, hoce da gleda za neulogovanog
        //baciti mu gresku neku?
        //ostati na istoj stranici <---
        next(false)
      }
  } else{
    next()
  }
})



new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')