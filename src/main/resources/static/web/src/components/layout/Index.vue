<template>
  <n-layout
    style="height: 100vh; width: 100vw; display: flex; flex-direction: column">
    <n-layout-header class="layout-header" bordered>
      <n-a
        class="layout-left"
        href="/"
        style="font-size: 20px; text-decoration: none">
        🖼Telegram图床
      </n-a>
      <div class="nav-end">
        <n-dropdown
          trigger="hover"
          :options="themeOptions"
          @select="handleSelect">
          <n-button text style="font-size: 24px">
            <n-icon>
              <DarkTheme24Regular />
            </n-icon>
          </n-button>
        </n-dropdown>
        <n-divider vertical></n-divider>
        <n-button
          text
          style="font-size: 24px"
          @click="OpenUrl('https://github.com')">
          <n-icon>
            <svg
              xmlns="http://www.w3.org/2000/svg"
              xmlns:xlink="http://www.w3.org/1999/xlink"
              viewBox="0 0 496 512">
              <path
                d="M165.9 397.4c0 2-2.3 3.6-5.2 3.6c-3.3.3-5.6-1.3-5.6-3.6c0-2 2.3-3.6 5.2-3.6c3-.3 5.6 1.3 5.6 3.6zm-31.1-4.5c-.7 2 1.3 4.3 4.3 4.9c2.6 1 5.6 0 6.2-2s-1.3-4.3-4.3-5.2c-2.6-.7-5.5.3-6.2 2.3zm44.2-1.7c-2.9.7-4.9 2.6-4.6 4.9c.3 2 2.9 3.3 5.9 2.6c2.9-.7 4.9-2.6 4.6-4.6c-.3-1.9-3-3.2-5.9-2.9zM244.8 8C106.1 8 0 113.3 0 252c0 110.9 69.8 205.8 169.5 239.2c12.8 2.3 17.3-5.6 17.3-12.1c0-6.2-.3-40.4-.3-61.4c0 0-70 15-84.7-29.8c0 0-11.4-29.1-27.8-36.6c0 0-22.9-15.7 1.6-15.4c0 0 24.9 2 38.6 25.8c21.9 38.6 58.6 27.5 72.9 20.9c2.3-16 8.8-27.1 16-33.7c-55.9-6.2-112.3-14.3-112.3-110.5c0-27.5 7.6-41.3 23.6-58.9c-2.6-6.5-11.1-33.3 2.6-67.9c20.9-6.5 69 27 69 27c20-5.6 41.5-8.5 62.8-8.5s42.8 2.9 62.8 8.5c0 0 48.1-33.6 69-27c13.7 34.7 5.2 61.4 2.6 67.9c16 17.7 25.8 31.5 25.8 58.9c0 96.5-58.9 104.2-114.8 110.5c9.2 7.9 17 22.9 17 46.4c0 33.7-.3 75.4-.3 83.6c0 6.5 4.6 14.4 17.3 12.1C428.2 457.8 496 362.9 496 252C496 113.3 383.5 8 244.8 8zM97.2 352.9c-1.3 1-1 3.3.7 5.2c1.6 1.6 3.9 2.3 5.2 1c1.3-1 1-3.3-.7-5.2c-1.6-1.6-3.9-2.3-5.2-1zm-10.8-8.1c-.7 1.3.3 2.9 2.3 3.9c1.6 1 3.6.7 4.3-.7c.7-1.3-.3-2.9-2.3-3.9c-2-.6-3.6-.3-4.3.7zm32.4 35.6c-1.6 1.3-1 4.3 1.3 6.2c2.3 2.3 5.2 2.6 6.5 1c1.3-1.3.7-4.3-1.3-6.2c-2.2-2.3-5.2-2.6-6.5-1zm-11.4-14.7c-1.6 1-1.6 3.6 0 5.9c1.6 2.3 4.3 3.3 5.6 2.3c1.6-1.3 1.6-3.9 0-6.2c-1.4-2.3-4-3.3-5.6-2z"
                fill="currentColor"></path>
            </svg>
          </n-icon>
        </n-button>
        <n-divider vertical></n-divider>
        <n-tag disabled size="small"> {{ store.Info.version }} </n-tag>
      </div>
    </n-layout-header>
    <n-layout style="height: 88vh">
      <n-layout
        :native-scrollbar="false"
        class="content"
        style="margin: 15px; flex-grow: 1">
        <div class="container"><Upload /></div>
      </n-layout>
    </n-layout>
    <n-layout-footer
      bordered
      depth="3"
      style="
        height: 6vh;
        display: flex;
        justify-content: center;
        align-items: center;
        background-color: transparent;
      ">
      <n-text>©2023 Libs. All rights reserved.</n-text>
    </n-layout-footer>
  </n-layout>
</template>

<script setup>
import Upload from "@/components/page/Upload.vue";
import { updateThemeMode } from "@/utils/themeMode.js";
import { NIcon } from "naive-ui";
import { DarkTheme24Regular, Checkmark16Regular } from "@vicons/fluent";
import { useStore } from "@/utils/store.js";
const store = useStore();
const OpenUrl = (url) => {
  window.open(window.open(url, "_blank"), "_blank");
};
const renderIcon = (icon) => {
  return () => {
    return h(NIcon, null, {
      default: () => h(icon),
    });
  };
};
const themeOptions = ref([
  { label: "暗黑", key: "dark" },
  { label: "明亮", key: "light" },
  { label: "跟随系统", key: "auto" },
]);
const setThemeOption = () => {
  themeOptions.value.forEach(function (item, index, arr) {
    if (item.key === store.ThemeMode) {
      item.icon = renderIcon(Checkmark16Regular);
    } else {
      item.icon = null;
    }
  });
};
setThemeOption();
const handleSelect = (key) => {
  updateThemeMode(key);
  setThemeOption();
};
</script>

<style scoped></style>
