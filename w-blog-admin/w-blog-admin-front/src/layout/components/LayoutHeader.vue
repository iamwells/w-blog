<script lang="ts" setup>
import { useRouter } from 'vue-router'
import { useAuthStore } from '@/stores/auth'
import { storeToRefs } from 'pinia'

const router = useRouter()
const authStore = useAuthStore()
const { user } = storeToRefs(authStore)
function logout() {
  authStore.signOut()
  router.push('/sign/in')
}
</script>

<template>
  <a-layout-header class="layout-header">
    <a-dropdown>
      <a class="ant-dropdown-link" @click.prevent>
        <a-avatar style="color: #f56a00; background-color: #fde3cf">{{ user.username }}</a-avatar>
      </a>
      <template #overlay>
        <a-menu>
          <a-menu-item>
            <UserOutlined />
            <a href="javascript:;">个人资料</a>
          </a-menu-item>
          <a-menu-item @click="logout">
            <LoginOutlined />
            <a href="javascript:;">注销</a>
          </a-menu-item>
        </a-menu>
      </template>
    </a-dropdown>
  </a-layout-header>
</template>

<style scoped></style>
