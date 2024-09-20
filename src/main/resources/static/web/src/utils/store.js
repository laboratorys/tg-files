import { defineStore } from "pinia";

export const useStore = defineStore("main", {
  state: () => ({
    ThemeMode: "dark",
    UserToken: "",
    showLogin: false,
    FilesList: [],
    Info: {},
    LoginInfo: { isLogin: false, token: "", userName: "" },
    activeKey: "files",
  }),
  persist: true,
});
