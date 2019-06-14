import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'

import Hotels from './views/Hotels.vue'

import RentACars from './views/RentACars.vue'

import RentACar from './components/rentACarComp/RentACar.vue'


import Signup from './components/login&signup/signup.vue'
import SignupAdmin from './components/login&signup/signupAdmin.vue'
import ViewUserProfile from './views/ViewUserProfile.vue'
import EditUserProfile from './views/EditUserProfile.vue'
import ViewAirlineProfile from './views/ViewAirlineProfile.vue'
import EditAirlineProfile from './views/EditAirlineProfile.vue'
import SearchFlights from './views/SearchFlights.vue'
import SearchUsers from './views/SearchUsers.vue'
import Login from './components/login&signup/login.vue'
import Logout from './components/login&signup/logout.vue'

import VehicleReservation from'./components/vehicleReservation/VehicleReservation.vue'//coxi
import Reserve from './components/HotelReservation/Reserve.vue'
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
      path: '/airlineProfile',
      name: 'airlineProfile',
      component: ViewAirlineProfile
    },
    {
      path: '/editAirlineProfile',
      name: 'editAirlineProfile',
      component: EditAirlineProfile
    },
    {
      path: '/searchFlights',
      name: 'searchFlights',
      component: SearchFlights
    },

    {
      path: '/rentACars',
      name: 'rentACars',
      component: RentACars
    },
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
    
    {
      path: '/vehicleReservation',
      name: 'vehicleReservation',
      component: VehicleReservation
    },

    //===============REGISTERED USER====================
    {
      path: '/hotelReservation',
      name: 'hotelReservation',
      component: Reserve
    },
    {
      path: '/hotels',
      name: 'hotels',
      component: Hotels
    },
    //==================================================

    //===============HOTEL ADMIN========================
    {
      path: '/hotelAdminProfile',
      name: 'hotelAdminProfile',
      component: () => import('./components/HotelAdmin/HotelAdminProfile.vue'),
      meta: {
        requiresAuth : true,
        is_hotel_admin : true
      }
    },
    //treba i ruta za editovanje hotela njegovog
    //==================================================

    //===============SYS ADMIN==========================
    {
      path : '/sysAdmin',
      name: 'sysAdminDashboard',
      component: () => import('./components/SysAdmin/SysAdminDashboard.vue'),
      meta: {
        requiresAuth : true
      }
    }
    //==================================================
  ]
})