import { createApp } from "vue";
import { createPinia } from "pinia";
import App from "@/App.vue";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
import { setupRouter } from "@/router";
import "@/style.css";

async function setupApp() {
  const pinia = createPinia();
  const app = createApp(App);
  pinia.use(piniaPluginPersistedstate);
  app.use(pinia);
  await setupRouter(app);
  app.mount("#app");
}
setupApp();
