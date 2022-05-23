import Vue from "vue";
import Router from "vue-router";
import Traces from "./components/trace/Traces.vue";
import TraceForm from "./components/trace/TraceForm.vue";

Vue.use(Router);

const routes = [
  {
    path: "/",
    component: Traces,
    children: [
      {
        path: "",
        component: TraceForm
      }
    ]
  }
];
const router = new Router({
  routes,
  mode: "history"
});

export default router;
