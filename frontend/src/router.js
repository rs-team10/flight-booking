import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
import EditUser from './components/EditUser.vue'
import EditAirline from './components/EditAirline.vue'
import Hotels from './views/Hotels.vue'
import AddAirline from './components/AddAirline.vue'
import AddRentACar from './components/AddRentACar.vue'
import RentACars from './views/RentACars.vue'
import AddVehicle from './components/AddVehicle.vue'
import EditVehicle from './components/EditVehicle.vue'
import ViewVehicles from './components/ViewVehicles.vue'
import Signup from './components/login&signup/signup.vue'
import SignupAdmin from './components/login&signup/signupAdmin.vue'
import UserProfile from './views/UserProfile.vue'

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
      component: UserProfile
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
      path: '/signupAdmin',
      name: 'signupAdmin',
      component: SignupAdmin
    },
    {
      path: '/signup',
      name: 'signup',
      component: Signup
    },
    //=====================================
    //=====================================
    //PRIVREMENE RUTE, NECE BITI KASNIJE
   
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
      path: '/viewVehicles',
      name: 'viewVehicles',
      component: ViewVehicles
    },
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