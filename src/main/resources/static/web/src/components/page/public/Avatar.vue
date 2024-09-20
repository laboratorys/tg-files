<template>
  <n-button
    text
    style="font-size: 28px"
    v-if="!store.LoginInfo.isLogin"
    @click="store.showLogin = true">
    <n-icon>
      <svg
        xmlns="http://www.w3.org/2000/svg"
        xmlns:xlink="http://www.w3.org/1999/xlink"
        viewBox="0 0 512 512">
        <path
          d="M192 176v-40a40 40 0 0 1 40-40h160a40 40 0 0 1 40 40v240a40 40 0 0 1-40 40H240c-22.09 0-48-17.91-48-40v-40"
          fill="none"
          stroke="currentColor"
          stroke-linecap="round"
          stroke-linejoin="round"
          stroke-width="32"></path>
        <path
          fill="none"
          stroke="currentColor"
          stroke-linecap="round"
          stroke-linejoin="round"
          stroke-width="32"
          d="M288 336l80-80l-80-80"></path>
        <path
          fill="none"
          stroke="currentColor"
          stroke-linecap="round"
          stroke-linejoin="round"
          stroke-width="32"
          d="M80 256h272"></path>
      </svg>
    </n-icon>
  </n-button>
  <n-dropdown :options="data.options" v-if="store.LoginInfo.isLogin">
    <n-button text style="font-size: 28px">
      <n-icon>
        <svg
          xmlns="http://www.w3.org/2000/svg"
          xmlns:xlink="http://www.w3.org/1999/xlink"
          viewBox="0 0 512 512">
          <path
            d="M258.9 48C141.92 46.42 46.42 141.92 48 258.9c1.56 112.19 92.91 203.54 205.1 205.1c117 1.6 212.48-93.9 210.88-210.88C462.44 140.91 371.09 49.56 258.9 48zm126.42 327.25a4 4 0 0 1-6.14-.32a124.27 124.27 0 0 0-32.35-29.59C321.37 329 289.11 320 256 320s-65.37 9-90.83 25.34a124.24 124.24 0 0 0-32.35 29.58a4 4 0 0 1-6.14.32A175.32 175.32 0 0 1 80 259c-1.63-97.31 78.22-178.76 175.57-179S432 158.81 432 256a175.32 175.32 0 0 1-46.68 119.25z"
            fill="currentColor"></path>
          <path
            d="M256 144c-19.72 0-37.55 7.39-50.22 20.82s-19 32-17.57 51.93C191.11 256 221.52 288 256 288s64.83-32 67.79-71.24c1.48-19.74-4.8-38.14-17.68-51.82C293.39 151.44 275.59 144 256 144z"
            fill="currentColor"></path>
        </svg>
      </n-icon>
    </n-button>
  </n-dropdown>
</template>
<script setup>
import {
  Person24Regular,
  SignOut24Regular,
  LauncherSettings24Regular,
} from "@vicons/fluent";
import { renderIcon } from "@/utils/icon.js";
import { doLogout } from "@/utils/api.js";
import { useStore } from "@/utils/store.js";
import { useRouter } from "vue-router";
import { message } from "@/utils/message.js";
const store = useStore();
const router = useRouter();
const data = reactive({ options: [] });
onMounted(() => {
  init();
});
function init() {
  data.options = [
    {
      label: store.LoginInfo.userName,
      key: "userName",
      icon: renderIcon(Person24Regular),
    },
    {
      label: "管理",
      key: "manage",
      icon: renderIcon(LauncherSettings24Regular),
      props: {
        onClick: () => {
          router.push("/admin");
        },
      },
    },
    {
      label: store.Info.version,
      key: "version",
    },
    {
      label: "退出登录",
      key: "logout",
      icon: renderIcon(SignOut24Regular),
      props: {
        onClick: () => {
          const promise = doLogout();
          promise.then((data) => {
            if (data.code == 200) {
              store.LoginInfo = { isLogin: false, token: "", userName: "" };
              message.success("退出成功");
              router.push("/");
            }
          });
        },
      },
    },
  ];
}
</script>
