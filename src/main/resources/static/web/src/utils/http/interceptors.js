import { message } from "@/utils/message.js";
import { useStore } from "@/utils/store.js";
export function reqResolve(config) {
  const store = useStore();
  // 防止缓存，给get请求加上时间戳
  if (config.method === "get") {
    config.params = { ...config.params, t: new Date().getTime() };
  }
  config.headers.Authorization = store.LoginInfo.token;
  return config;
}

export function reqReject(error) {
  return Promise.reject(error);
}

export function resResolve(response) {
  if (response.data && response.data.code && response.data.code != 200) {
    message.error(response.data.msg);
    if (response.data.code == 401) {
      store.LoginInfo = { isLogin: false, token: "", userName: "" };
    }
  }
  return response?.data;
}

export function resReject(error) {
  let { code, message } = error.response?.data || {};
  if (code === null || typeof code === "undefined") {
    // 未知错误
    code = -1;
    message = "接口异常！";
  } else {
    switch (code) {
      case 401:
        message = message || "未授权的访问";
        break;
      case 403:
        message = message || "没有权限";
        break;
      case 404:
        message = message || "资源不存在";
        break;
      default:
        message = message || "未知异常";
        break;
    }
  }
  //console.error(`【${code}】 ${error}`);
  return Promise.resolve({ code, message, error });
}
