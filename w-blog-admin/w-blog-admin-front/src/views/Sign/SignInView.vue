<script lang="ts" setup>
import { reactive, ref, computed, toRaw } from 'vue'
import { useAuthStore } from '@/stores/auth'
import { useSignPageStore } from '@/stores/config/signPage'
import { storeToRefs } from 'pinia'
import { useRouter } from 'vue-router';
import { message } from 'ant-design-vue';


const router = useRouter()
const authStore = useAuthStore()
const { token } = storeToRefs(authStore)

const signPageStore = useSignPageStore()
const { rememberMe, signBy } = storeToRefs(signPageStore)

interface UsernameLoginForm {
  username: string
  password: string
  rememberMe: boolean
}
const usernameLoginForm = ref()
const usernameLoginFormState = reactive<UsernameLoginForm>({
  username: '',
  password: '',
  rememberMe: rememberMe.value
})

const ifAllowSignIn = computed(() => {
  return !(usernameLoginFormState.username && usernameLoginFormState.password)
})

const activeKey = ref(signBy.value as string)

function onSubmit() {
  usernameLoginForm.value
    .validate()
    .then(() => {
      const user = toRaw(usernameLoginFormState)
      // 登录
      authStore.signIn(user.username, user.password, user.rememberMe).then((resp) => {
        console.log(resp)
        if (resp.data.status === 200) {
          token.value = resp.data?.token

          router.push({ name: 'dashboard' })
        } else {
          message.error(resp.data?.errMsg || resp.data.phrase)
        }
      })
    })
    .catch((err: any) => {
      console.log(err)
    })
}
</script>

<template>
  <a-card class="sign-in-card" title="登录" :headStyle="{ textAlign: 'center', fontSize: '2em' }">
    <template #default>
      <a-tabs v-model:activeKey="activeKey">
        <a-tab-pane key="usernamePassword" tab="密码登录">
          <a-form
            ref="usernameLoginForm"
            :model="usernameLoginFormState"
            name="normal_login"
            class="sign-in-form"
            :labelCol="{ span: 24 }"
            hideRequiredMark
          >
            <a-form-item
              label="用户名"
              name="username"
              :rules="[
                { required: true, message: '请输入用户名！' },
                { whitespace: true, message: '空格符号无效！' },
                { min: 5, max: '', message: '字母长度5-32位！' }
              ]"
            >
              <a-input
                v-model:value="usernameLoginFormState.username"
                size="large"
                :allowClear="true"
              >
                <template #prefix>
                  <UserOutlined class="site-form-item-icon" />
                </template>
              </a-input>
            </a-form-item>

            <a-form-item
              label="密码"
              name="password"
              :rules="[
                { required: true, message: '请输入密码！' },
                { whitespace: true, message: '空格符号无效！' },
                { min: 5, max: '', message: '字母长度5-32位！' }
              ]"
            >
              <a-input-password
                v-model:value="usernameLoginFormState.password"
                size="large"
                :allowClear="true"
              >
                <template #prefix>
                  <LockOutlined class="site-form-item-icon" />
                </template>
              </a-input-password>
            </a-form-item>

            <a-form-item>
              <a-form-item name="rememberMe" no-style>
                <a-checkbox v-model:checked="usernameLoginFormState.rememberMe" size="large">
                  <a-typography-text>保持登录</a-typography-text>
                </a-checkbox>
              </a-form-item>
              <a-typography-link href="#" style="float: right" type="danger"
                >忘记密码？</a-typography-link
              >
            </a-form-item>

            <a-form-item>
              <a-button
                :disabled="ifAllowSignIn"
                type="primary"
                html-type="submit"
                class="login-form-button"
                size="large"
                style="width: 100%"
                @click="onSubmit"
              >
                登录
              </a-button>
              <span style="margin-left: 10px">或者</span>
              <a-button type="link" size="large">现在注册</a-button>
            </a-form-item>
          </a-form>
        </a-tab-pane>
        <a-tab-pane key="emailCode" tab="邮箱验证码登录" force-render>邮箱验证码登录</a-tab-pane>
      </a-tabs>
    </template>
  </a-card>
</template>

<style scoped>
.sign-in-card {
  width: 420px;
  height: 500px;
}
</style>
