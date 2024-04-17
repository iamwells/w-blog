<script lang="ts" setup>
import { useMenuConfigStore } from '@/stores/config/menu'
import { storeToRefs } from 'pinia'
import { ref, onMounted, watch } from 'vue'
import { useRouter } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'

const menuConfig = useMenuConfigStore()

const { collapsed } = storeToRefs(menuConfig)
const selectedKeys = ref<string[]>([])
const router = useRouter()
const menu = ref<RouteRecordRaw[]>([])
router.getRoutes().forEach((route) => {
  if (route.path === '/') {
    menu.value.push(...route.children)
  }
})
watch(collapsed, () => {})

onMounted(() => {
  selectedKeys.value.push(router.currentRoute.value.path.replace('/', ''))
})
function toDashboard(key: any) {
  router.push(key)
  selectedKeys.value.splice(0, selectedKeys.value.length, key)
}
</script>

<template>
  <a-layout-sider v-model:collapsed="collapsed" collapsible class="layout-sider">
    <div class="logo" @click="toDashboard('dashboard')">
      {{ collapsed ? 'W-Blog' : 'W-Blog管理后台' }}
    </div>
    <a-menu
      v-model:selectedKeys="selectedKeys"
      theme="dark"
      mode="inline"
      @select="
        ({ item, key, selectedKeys }: any) => {
          $router.push(key)
        }
      "
    >
      <template v-for="item in menu" :key="item.path">
        <a-sub-menu
          v-if="item.children && item.children.length > 0"
          :key="item.path + ''"
          :lable="item.meta?.title || item.name"
        >
          <a-menu-item v-for="iitem in item.children" :key="iitem.path">{{
            item.meta?.title || item.name
          }}</a-menu-item>
        </a-sub-menu>
        <a-menu-item v-else :key="item.path">
          <template #icon>
            <component :is="item.meta?.icon" two-tone-color="#aaa"></component>
          </template>
          {{ item.meta?.title || item.name }}
        </a-menu-item>
      </template>
    </a-menu>
  </a-layout-sider>
</template>

<style scoped>
.logo {
  height: 32px;
  margin: 16px;
  background: rgba(255, 255, 255, 0.3);
  cursor: pointer;
  text-align: center;
  line-height: 32px;
  user-select: none;
}
.site-layout .site-layout-background {
  background: #fff;
}
[data-theme='dark'] .site-layout .site-layout-background {
  background: #141414;
}
</style>
