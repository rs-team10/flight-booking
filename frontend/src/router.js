import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
import EditUser from './components/EditUser.vue'
import EditAirline from './components/EditAirline.vue'
import Hotels from './views/Hotels.vue'
import AddAirline from './components/AddAirline.vue'
import AddRentACar from './components/AddRentACar.vue'
import RentACars from './views/RentACars.vue'

import RentACar from './components/rentACarComp/RentACar.vue'


import Signup from './components/login&signup/signup.vue'
import SignupAdmin from './components/login&signup/signupAdmin.vue'
import Login from './components/login&signup/login.vue'
import Logout from './components/login&signup/logout.vue'


/*
import ViewBranchOfficesA from './components/rentACarComp/ViewBranchOfficesA.vue'
import AddVehicle from './components/rentACarComp/AddVehicle.vue'
import EditVehicle from './components/rentACarComp/EditVehicle.vue'
import ViewVehiclesA from './components/rentACarComp/ViewVehiclesA.vue'
*/

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/editUser',
      name: 'editUser',
      component: EditUser
    },
    {
      path: '/editAirline',
      name: 'editAirline',
      component: EditAirline
    },
    {
      path: '/hotels',
      name: 'hotels',
      component: Hotels
    },
    {
      path: '/rentACars',
      name: 'rentACars',
      component: RentACars
    },
    //=====================================
    //TEST
    
    {
      props: true,
      path: '/rentACar/:rentACarId',
      name: 'rentACar',
      component: RentACar
    },
    
    {
      path: '/signupAdmin',
      name: 'signupAdmin',
      component: SignupAdmin
    },
    {
      path: '/signup',
      name: 'signup',
      component: Signup
    },
    {
      path: '/login',
      name: 'login',
      component: Login
    },
    {
      path: '/logout',
      name: 'logout',
      component: Logout
    },
    //=====================================
    //=====================================
    //PRIVREMENE RUTE, NECE BITI KASNIJE - OVOME SAMO ADMIN SME DA PRISTUPA, I PRISTUPACE U VIDU KOMPONENTE
    /*
    {
      path: '/addVehicle',
      name: 'addVehicle',
      component: AddVehicle
    },
    {
      path: '/editVehicle',
      name: 'editVehicle',
      component: EditVehicle
    },
 
    {
      path: '/viewVehiclesA',
      name: 'viewVehiclesA',
      component: ViewVehiclesA
    },
     {
      path: '/viewBranchOfficesA',
      name: 'viewBranchOfficesA',
      component: ViewBranchOfficesA
    },
    */
  //=====================================



    {
      path: '/addAirline',
      name: 'addAirline',
      component: AddAirline
    },
    //ubacicu kao komponentu u viewer tako da moze da se izbaci odavde
    {
      path: '/addRentACar',
      name: 'addRentACar',
      component: AddRentACar
    }
    //=====================================
  ]
})