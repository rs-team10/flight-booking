import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home.vue'
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
      path: '/hotels',
      name: 'hotels',
      component: Hotels
    }
  ]
})
