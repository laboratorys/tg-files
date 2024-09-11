import axios from "axios";
import { resResolve, resReject, reqResolve, reqReject } from "./interceptors";
export function createAxios(options = {}) {
  const tokenInfo = getTokenFromLocalHistory();
  const name = tokenInfo.tokenName;
  const defaultOptions = {
    //baseURL: import.meta.env.VITE_APP_BASE_API,
    timeout: 120000,
    headers: {
      "Content-Type": "application/json",
      [name]: tokenInfo.tokenValue,
    },
  };
  const service = axios.create({
    ...defaultOptions,
    ...options,
  });

  service.interceptors.request.use(reqResolve, reqReject);
  service.interceptors.response.use(resResolve, resReject);
  return service;
}

export const defAxios = createAxios();
function getTokenFromLocalHistory() {
  const settings = localStorage.getItem("tg_files_settings");
  if (settings && settings != "") {
    const settings = JSON.parse(settings);
    const token = settings["token"];
    if (token) {
      return token;
    }
  }
  return { tokenName: "Authoritarian", tokenValue: "" };
}
