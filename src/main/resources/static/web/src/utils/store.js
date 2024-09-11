import { defineStore } from "pinia";

export const useStore = defineStore("main", {
  state: () => ({
    ThemeMode: "dark",
    UserToken: "",
    FilesList: [],
    Info: {},
  }),
  persist: true,
});
