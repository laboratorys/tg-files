import { defAxios as request } from "@/utils/http";
//获取Token
export function getToken() {
  return request({
    url: "/api/public/token",
    method: "get",
  });
}
//文件列表
export function getFileList(pageNo, pageSize, token) {
  return request({
    url: `/api/public/file/list?pageNo=${pageNo}&pageSize=${pageSize}&token=${token}`,
    method: "get",
  });
}
//删除文件
export function deleteFile(id, token) {
  return request({
    url: `/api/public/file/delete?id=${id}&token=${token}`,
    method: "get",
  });
}
//图床信息
export function getInfo() {
  return request({
    url: `/api/public/file/info`,
    method: "get",
  });
}
