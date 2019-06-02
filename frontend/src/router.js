import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
import EditAirline from './components/EditAirline.vue'
import Hotels from './views/Hotels.vue'
import AddAirline from './components/SysAdmin/AddAirline.vue'
import AddRentACar from './components/SysAdmin/AddRentACar.vue'
import RentACars from './views/RentACars.vue'

import RentACar from './components/rentACarComp/RentACar.vue'


import Signup from './components/login&signup/signup.vue'
import SignupAdmin from './components/login&signup/signupAdmin.vue'
import ViewUserProfile from './views/ViewUserProfile.vue'
import EditUserProfile from './views/EditUserProfile.vue'
import SearchUsers from './views/SearchUsers.vue'
import Login from './components/login&signup/login.vue'
import Logout from './components/login&signup/logout.vue'

import Reserve from './components/HotelReservation/Reserve.vue'
import BarChart from './components/HotelAdmin/Chart.vue'
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
      path: '/userProfile',
      name: 'userProfile',
      component: ViewUserProfile
    },
    {
      path: '/editUserProfile',
      name: 'editUserProfile',
      component: EditUserProfile
    },
    {
      path: '/searchUsers',
      name: 'searchUsers',
      component: SearchUsers
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
    },
    {
      path: '/hotelReservation',
      name: 'hotelReservation',
      component: Reserve
    },
    {
      path: '/temp',
      name: 'barChart',
      component: BarChart
    }
    //=====================================
  ]
})