<template>
  <n-modal
    v-model:show="store.showLogin"
    :mask-closable="false"
    @after-leave="closeBack"
    title="登录"
    preset="card"
    style="max-width: 500px">
    <n-tabs
      class="card-tabs"
      v-model:value="form.tab"
      :default-value="form.tab"
      size="large"
      animated>
      <n-tab-pane name="signin" tab="登录">
        <n-form
          ref="loginFormRef"
          :model="form.login"
          :rules="loginRules"
          label-placement="left"
          label-width="auto">
          <n-form-item label="账号" prop="userName" path="userName">
            <n-input v-model:value="form.login.userName" placeholder="账号">
              <template #prefix>
                <n-icon :component="Person24Regular" />
              </template>
            </n-input>
          </n-form-item>
          <n-form-item label="密码" prop="password" path="password">
            <n-input
              type="password"
              placeholder="密码"
              show-password-on="click"
              v-model:value="form.login.password">
              <template #prefix>
                <n-icon :component="LockClosed24Regular" />
              </template>
              <template #password-visible-icon>
                <n-icon :size="16" :component="Glasses24Regular" />
              </template>
              <template #password-invisible-icon>
                <n-icon :size="16" :component="GlassesOff24Regular" />
              </template>
            </n-input>
          </n-form-item>
        </n-form>
        <n-button
          type="primary"
          block
          strong
          @click="userLogin"
          :loading="form.loginLoading">
          <template #icon>
            <n-icon :component="Fingerprint24Regular" /> </template
          >登录
        </n-button>
      </n-tab-pane>
    </n-tabs>
  </n-modal>
</template>

<script setup>
import { ref, reactive } from "vue";
import { useStore } from "@/utils/store.js";
import { doLogin } from "@/utils/api.js";
import { message } from "@/utils/message.js";
import {
  Person24Regular,
  LockClosed24Regular,
  Fingerprint24Regular,
  GlassesOff24Regular,
  Glasses24Regular,
} from "@vicons/fluent";
import { useRouter } from "vue-router";
const router = useRouter();
const form = reactive({
  login: { userName: null, password: null },
  tab: "signin",
  loginLoading: false,
});
const store = useStore();

function closeCall() {
  store.showLogin = false;
}

const loginRules = {
  userName: [{ required: true, message: "请输入账号", trigger: "blur" }],
  password: [{ required: true, message: "请输入密码", trigger: "blur" }],
};

const loginFormRef = ref(null);

function userLogin() {
  const formInstance = loginFormRef.value;
  formInstance.validate((valid) => {
    if (!valid) {
      form.loginLoading = true;
      const promise = doLogin(form.login);
      promise.then((data) => {
        form.loginLoading = false;
        if (data.code == 200) {
          store.LoginInfo.token = data.data.tokenValue;
          store.LoginInfo.isLogin = true;
          store.LoginInfo.userName = data.data.loginId;
          message.success("登录成功");
          closeCall();
          router.push("/admin");
        }
      });
    }
  });
}
const closeBack = () => {
  store.showLogin = false;
};
</script>
