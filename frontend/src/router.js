import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
import vCarSearch from './views/vCarSearch.vue'
import EditRcs from './components/EditRcs.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/vCarSearch',
      name: 'vCarSearch',
      component: vCarSearch
    },
    {
      path: '/editRcs',
      name: 'editRcs',
      component: EditRcs
    }
  ]
})