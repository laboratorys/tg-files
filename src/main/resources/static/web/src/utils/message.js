import { currentTheme } from "@/utils/themeMode.js";
import { createDiscreteApi } from "naive-ui";
const configProviderPropsRef = computed(() => ({
  theme: currentTheme.value,
}));
const { message, notification, dialog, loadingBar, modal } = createDiscreteApi(
  ["message", "dialog", "notification", "loadingBar", "modal"],
  {
    configProviderProps: configProviderPropsRef,
  }
);

export { message, notification, dialog, loadingBar, modal };
