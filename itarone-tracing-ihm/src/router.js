import Vue from "vue";
import Router from "vue-router";
import Traces from "./components/trace/Traces.vue";
import TraceForm from "./components/trace/TraceForm.vue";

Vue.use(Router);

const routes = [
  {
    path: "/",
    component: Traces,
    meta: {
      requiresAuth: true
    },
    children: [
      {
        path: "",
        component: TraceForm,
        meta: {
          requiresAuth: true,
          role: 'user'
        }
       
      }
    ]
  }
];
const router = new Router({
  routes,
  mode: "history"
});

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    if (router.app.$keycloak.authenticated) {
      if (to.meta.role) {
        if (router.app.$keycloak.hasRealmRole(to.meta.role)) {
          next();
        } else {
          alert('Access Denied');
        }
      } else {
        next();
      }

    } else {
      const loginUrl = router.app.$keycloak.createLoginUrl()
      window.location.replace(loginUrl)
    }
  } else {
    next();
  }
});

export default router;
