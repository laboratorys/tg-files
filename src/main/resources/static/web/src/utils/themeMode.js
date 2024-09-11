import { ref } from "vue";
import { lightTheme, darkTheme } from "naive-ui";
import { useStore } from "@/utils/store.js";

const currentTheme = ref(lightTheme);
const updateThemeMode = () => {
  const store = useStore();
  if (store.ThemeMode === "auto") {
    store.ThemeMode = "light";
  } else if (store.ThemeMode === "dark") {
    store.ThemeMode = "light";
  } else if (store.ThemeMode === "light") {
    store.ThemeMode = "auto";
  }
  initThemeMode();
};
const mql = window.matchMedia("(prefers-color-scheme: dark)");
mql.addEventListener("change", updateThemeMode);
const initThemeMode = () => {
  const store = useStore();
  const mode = store.ThemeMode;
  if (mode === "dark") {
    currentTheme.value = darkTheme;
  } else if (mode === "auto") {
    currentTheme.value = mql.matches ? darkTheme : lightTheme;
  } else {
    currentTheme.value = lightTheme;
  }
};
export { currentTheme, updateThemeMode, initThemeMode };
