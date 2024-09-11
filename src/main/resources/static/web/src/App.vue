<template>
  <n-config-provider
    :theme="currentTheme"
    :locale="zhCN"
    :date-locale="dateZhCN">
    <Index />
  </n-config-provider>
</template>
<script setup>
import { NConfigProvider, zhCN, dateZhCN } from "naive-ui";
import Index from "@/components/layout/Index.vue";
import { currentTheme, initThemeMode } from "@/utils/themeMode.js";
initThemeMode();
</script>
<style scoped></style>
