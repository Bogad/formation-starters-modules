import Vue from "vue";
import VueI18n from "vue-i18n";
import Vuelidate from "vuelidate";
import router from "./router";
import App from "./App.vue";
import translations from "./resources/translations";
import BootstrapVue from "bootstrap-vue";
import "bootstrap/dist/css/bootstrap.css";
import "bootstrap-vue/dist/bootstrap-vue.css";
import VueKeycloakJs from 'omni-vue-keycloak'


var VueScrollTo = require("vue-scrollto");

Vue.use(VueScrollTo);

// You can also pass in the default options
Vue.use(VueScrollTo, {
  container: "body",
  duration: 500,
  easing: "ease",
  offset: 0,
  force: true,
  cancelable: true,
  onStart: false,
  onDone: false,
  onCancel: false,
  x: false,
  y: true
});

Vue.use(BootstrapVue);
Vue.use(VueI18n);
Vue.use(Vuelidate);

const i18n = new VueI18n({
  locale: "fr",
  fallbackLocale: "fr",
  messages: translations
});

Vue.use(VueKeycloakJs, {
  init: {
     onLoad: 'check-sso'
  },
  config: {
      url: process.env.VUE_APP_KEYCLOAK_URL,
      clientId: process.env.VUE_APP_KEYCLOAK_CLIENT_ID,
      realm: process.env.VUE_APP_KEYCLOAK_REALM
  },
  onReady: () => {
      new Vue({
          i18n,
          router,
          render: h => h(App)
      }).$mount('#app')
  }
});





