import Vue from 'vue'
import './plugins/vuetify'
import Vuetify from 'vuetify'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import Vuelidate from 'vuelidate'
import VueSweetalert2 from 'vue-sweetalert2';
import VuetifyGoogleAutocomplete from 'vuetify-google-autocomplete';

Vue.prototype.$axios = axios
Vue.config.productionTip = false

Vue.use(Vuetify)
Vue.use(Vuelidate)
Vue.use(VueSweetalert2);
Vue.use(VuetifyGoogleAutocomplete, {
  apiKey: 'AIzaSyCoYMbcCf9ot8Dyoy5oxEFDaT5cVitKME4',
});

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')