import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
import EditUser from './components/EditUser.vue'
import EditAirline from './components/EditAirline.vue'
import Hotels from './views/Hotels.vue'
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
  ]
})