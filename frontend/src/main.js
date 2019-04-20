import Vue from 'vue'
import './plugins/vuetify'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import Vuelidate from 'vuelidate'
import VueSweetalert2 from 'vue-sweetalert2';

Vue.prototype.$axios = axios
Vue.config.productionTip = false

Vue.use(Vuelidate)
Vue.use(VueSweetalert2);

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')