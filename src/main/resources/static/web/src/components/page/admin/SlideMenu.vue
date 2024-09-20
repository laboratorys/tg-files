<template>
  <n-layout-sider
    v-if="store.LoginInfo.isLogin"
    show-trigger
    collapse-mode="width"
    :collapsed="collapsed"
    :collapsed-width="64"
    :width="240"
    :native-scrollbar="false"
    :inverted="inverted">
    <n-menu
      v-model:value="store.activeKey"
      :inverted="inverted"
      :icon-size="22"
      :collapsed-width="64"
      :collapsed-icon-size="26"
      :options="menuOptions" />
  </n-layout-sider>
</template>
<script setup>
import { ref, h, watch } from "vue";
import { renderIcon } from "@/utils/icon.js";
import { RouterLink, useRouter } from "vue-router";
import { useStore } from "@/utils/store.js";
import {
  DocumentCatchUp24Regular,
  Settings24Regular,
  Storage24Regular,
} from "@vicons/fluent";
const router = useRouter();
const store = useStore();
const collapsed = ref(true);
watch(
  () => router.currentRoute.value.path,
  (toPath) => {
    if (toPath == "/admin/files" || toPath == "/admin") {
      store.activeKey = "files";
      document.title = "文件管理 - 图床";
      collapsed.value = false;
    } else if (toPath == "/admin/storage") {
      store.activeKey = "storage";
      document.title = "存储管理 - 图床";
      collapsed.value = false;
    } else if (toPath == "/admin/config") {
      store.activeKey = "config";
      document.title = "配置管理 - 图床";
      collapsed.value = false;
    } else {
      collapsed.value = true;
    }
  },
  { immediate: true, deep: true }
);
const inverted = ref(false);
const menuOptions = [
  {
    label: () =>
      h(
        RouterLink,
        {
          to: {
            path: "/admin/files",
          },
        },
        { default: () => "文件" }
      ),
    key: "files",
    icon: renderIcon(DocumentCatchUp24Regular),
  },
  {
    label: () =>
      h(
        RouterLink,
        {
          to: {
            path: "/admin/storage",
          },
        },
        { default: () => "存储" }
      ),
    key: "storage",
    icon: renderIcon(Storage24Regular),
  },
  {
    label: () =>
      h(
        RouterLink,
        {
          to: {
            path: "/admin/config",
          },
        },
        { default: () => "配置" }
      ),
    key: "config",
    icon: renderIcon(Settings24Regular),
  },
];
</script>
