<template>
  <n-flex align="center" vertical style="width: 100vw; height: 81vh">
    <n-input-group class="upload_items">
      <n-button type="primary"> 密钥 </n-button>
      <n-input
        type="text"
        v-model:value="store.UserToken"
        placeholder="上传密钥"
        @change="handleKeyChange"
        clearable />
    </n-input-group>

    <n-upload
      multiple
      action="/api/public/file/upload"
      :multiple="true"
      name="files"
      list-type="image"
      :headers="uploadHeaders"
      :show-file-list="false"
      v-model:file-list="fileListRef"
      show-download-button
      @finish="handleFinish"
      @remove="handleRemove"
      @change="handleUploadChange"
      @preview="handlePreview"
      @before-upload="validUploadRequest"
      class="upload_items">
      <n-upload-dragger>
        <div style="margin-bottom: 12px">
          <n-icon size="48" color="rgb(46 222 174)">
            <svg
              xmlns="http://www.w3.org/2000/svg"
              xmlns:xlink="http://www.w3.org/1999/xlink"
              viewBox="0 0 24 24">
              <path
                d="M19.35 10.04A7.49 7.49 0 0 0 12 4C9.11 4 6.6 5.64 5.35 8.04A5.994 5.994 0 0 0 0 14c0 3.31 2.69 6 6 6h13c2.76 0 5-2.24 5-5c0-2.64-2.05-4.78-4.65-4.96zM14 13v4h-4v-4H7l4.65-4.65c.2-.2.51-.2.71 0L17 13h-3z"
                fill="currentColor"></path>
            </svg>
          </n-icon>
        </div>
        <n-text style="font-size: 16px">
          点击上传或将文件拖拽到此处，也可以拷贝图片链接或图片内容后按Ctrl+V上传
        </n-text>
        <n-p depth="3" style="margin: 8px 0 0 0">
          最大可上传{{ store.Info.maxSizeFmt }}的图片，单次同时可选择 5
          张，本站已上传文件：{{ store.Info.totalCount }}
        </n-p>
      </n-upload-dragger>
      <n-card
        style="margin-top: 6px"
        title="文件列表"
        v-show="showUrl || fileListRef.length > 0"
        @click.stop="clickFileList()">
        <n-upload-file-list />
        <n-pagination
          align="center"
          v-model:page="pageNo"
          :page-count="totalPages"
          v-show="totalPages > 1"
          @update:page="handlePageChange"
          style="margin-top: 6px"
          simple />
      </n-card>
    </n-upload>
    <n-card v-show="showUrl" class="upload_items" content-style="padding: 10px">
      <n-flex justify="center" align="center" vertical>
        <n-button-group v-show="showUrl">
          <n-button type="primary" @click="copyClick('URL')">
            <template #icon>
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1024 1024">
                <path
                  d="M574 665.4a8.03 8.03 0 0 0-11.3 0L446.5 781.6c-53.8 53.8-144.6 59.5-204 0c-59.5-59.5-53.8-150.2 0-204l116.2-116.2c3.1-3.1 3.1-8.2 0-11.3l-39.8-39.8a8.03 8.03 0 0 0-11.3 0L191.4 526.5c-84.6 84.6-84.6 221.5 0 306s221.5 84.6 306 0l116.2-116.2c3.1-3.1 3.1-8.2 0-11.3L574 665.4zm258.6-474c-84.6-84.6-221.5-84.6-306 0L410.3 307.6a8.03 8.03 0 0 0 0 11.3l39.7 39.7c3.1 3.1 8.2 3.1 11.3 0l116.2-116.2c53.8-53.8 144.6-59.5 204 0c59.5 59.5 53.8 150.2 0 204L665.3 562.6a8.03 8.03 0 0 0 0 11.3l39.8 39.8c3.1 3.1 8.2 3.1 11.3 0l116.2-116.2c84.5-84.6 84.5-221.5 0-306.1zM610.1 372.3a8.03 8.03 0 0 0-11.3 0L372.3 598.7a8.03 8.03 0 0 0 0 11.3l39.6 39.6c3.1 3.1 8.2 3.1 11.3 0l226.4-226.4c3.1-3.1 3.1-8.2 0-11.3l-39.5-39.6z"
                  fill="currentColor"></path>
              </svg>
            </template>
            URL
          </n-button>
          <n-button type="info" @click="copyClick('Markdown')">
            <template #icon>
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                <g
                  fill="none"
                  stroke="currentColor"
                  stroke-width="2"
                  stroke-linecap="round"
                  stroke-linejoin="round">
                  <rect x="3" y="5" width="18" height="14" rx="2"></rect>
                  <path d="M7 15V9l2 2l2-2v6"></path>
                  <path d="M14 13l2 2l2-2m-2 2V9"></path>
                </g>
              </svg>
            </template>
            Markdown
          </n-button>
          <n-button type="warning" @click="copyClick('BBCode')">
            <template #icon>
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
                <path
                  d="M20 2H4c-1.1 0-2 .9-2 2v18l4-4h14c1.1 0 2-.9 2-2V4c0-1.1-.9-2-2-2zm0 14H6l-2 2V4h16v12z"
                  fill="currentColor"></path>
              </svg>
            </template>
            BBCode
          </n-button>
        </n-button-group>
        <n-input
          v-model:value="content"
          type="textarea"
          v-show="showUrl"></n-input>
        <n-modal
          v-model:show="showModal"
          preset="card"
          style="width: 600px"
          title="预览">
          <img :src="previewImageUrl" style="width: 100%" />
        </n-modal>
      </n-flex>
    </n-card>
  </n-flex>
</template>
<script setup>
import { useStore } from "@/utils/store.js";
import { message, loadingBar } from "@/utils/message.js";
import { getToken, deleteFile, getFileList, getInfo } from "@/utils/api.js";
const showUrl = ref(false);
const fileUrl = ref("");
const fileName = ref("");
const content = ref("");
const uploadHeaders = ref({});
const fileListRef = ref([]);
const pageNo = ref(1);
const totalPages = ref(1);
const pageSize = ref(5);
const store = useStore();
const showModal = ref(false);
const previewImageUrl = ref("");
onMounted(() => {
  if (!store.UserToken) {
    getToken().then((response) => {
      store.UserToken = response.data;
      handleKeyChange();
    });
  } else {
    handleKeyChange();
  }
  getInfo().then((response) => {
    store.Info = response.data;
  });
});
const setFileList = () => {
  loadingBar.start();
  getFileList(pageNo.value, pageSize.value, store.UserToken).then(
    (response) => {
      let data = response.data.content;
      data.forEach(function (item, index, arr) {
        item.url = item.url;
        data[index] = item;
      });
      fileListRef.value = data;
      totalPages.value = response.data.totalPages;
      if (data && data.length > 0) {
        fileUrl.value = data[0].url;
        content.value = fileUrl.value;
        copyToClipboard(fileUrl.value, "URL", false);
        showUrl.value = true;
      } else {
        showUrl.value = false;
      }
      loadingBar.finish();
    }
  );
};
function handlePageChange(currentPage) {
  setFileList();
}
const handleUploadChange = (data) => {
  fileListRef.value = data.fileList;
};
const handlePreview = (file, { event }) => {
  if (file.status === "finished") {
    fileUrl.value = file.url;
    content.value = fileUrl.value;
    copyToClipboard(fileUrl.value, "URL", false);
    showUrl.value = true;
    message.success("链接已复制到剪切板！");
    previewImageUrl.value = file.url;
    showModal.value = true;
  }
  event.preventDefault();
};
const handleFinish = ({ file, event }) => {
  const retData = JSON.parse(event.target.response);
  if (retData.data && retData.data.length > 0) {
    fileUrl.value = retData.data[0].url;
    file.url = fileUrl.value;
    content.value = fileUrl.value;
    copyToClipboard(fileUrl.value, "URL", false);
    showUrl.value = true;
    message.success("文件上传成功，链接已复制到剪切板！");
  } else {
    file.status = "error";
    message.error(retData.msg);
  }
};
const handleRemove = (data) => {
  if (data.file.status === "finished") {
    let parts = data.file.url.split("/");
    let id = parts[parts.length - 1];
    deleteFile(id, store.UserToken).then((response) => {
      message.success("删除成功！");
      setFileList();
    });
  }
};
const copyClick = (type) => {
  var text = fileUrl.value;
  if (type === "Markdown") {
    text = "![" + fileName.value + "](" + fileUrl.value + ")";
  } else if (type === "BBCode") {
    text = "[img]" + fileUrl.value + "[/img]";
  }
  content.value = text;
  copyToClipboard(text, type, true);
};
const copyToClipboard = (text, type, isNotify) => {
  const input = document.createElement("textarea");
  input.value = text;
  document.body.appendChild(input);
  input.select();
  document.execCommand("copy");
  document.body.removeChild(input);
  if (isNotify === true) {
    message.success(type + "已复制到剪贴板");
  }
};
const clickFileList = () => {};
const validUploadRequest = ({ file }) => {
  if (file.file.size > store.Info.maxSize) {
    message.error("上传失败，文件大小已超上限：" + store.Info.maxSizeFmt);
    return false;
  }
  return true;
};
const handleKeyChange = () => {
  uploadHeaders.value = { Authorization: store.UserToken };
  setFileList();
};
</script>
<style scoped></style>
