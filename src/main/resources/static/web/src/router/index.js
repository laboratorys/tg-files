import { createRouter, createWebHistory } from "vue-router";
import { useStore } from "@/utils/store.js";
import { basicRoutes as routes } from "@/router/routes";
export const router = createRouter({
  history: createWebHistory("/"),
  routes,
  scrollBehavior: () => ({ left: 0, top: 0 }),
});
router.beforeEach((to, from, next) => {
  if (to.matched.some((record) => record.meta.requiresAuth)) {
    const store = useStore();
    if (store.LoginInfo.isLogin === true) {
      next();
    } else {
      next("/");
    }
  } else {
    next();
  }
});
export function setupRouter(app) {
  app.use(router);
}
