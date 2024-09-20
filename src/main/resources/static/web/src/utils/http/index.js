import axios from "axios";
import { resResolve, resReject, reqResolve, reqReject } from "./interceptors";
export function createAxios(options = {}) {
  const defaultOptions = {
    //baseURL: import.meta.env.VITE_APP_BASE_API,
    timeout: 120000,
    headers: {
      "Content-Type": "application/json",
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
