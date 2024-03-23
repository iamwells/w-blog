<script lang="ts" setup>
import { computed } from 'vue'
import LayoutSider from './components/LayoutSider.vue'
import LayoutHeader from './components/LayoutHeader.vue'
import LayoutFooter from './components/LayoutFooter.vue'
import LayoutContent from './components/LayoutContent.vue'
import { useMenuConfigStore } from '@/stores/config/menu'
import { storeToRefs } from 'pinia'

const menuConfig = useMenuConfigStore()

const { collapsed } = storeToRefs(menuConfig)

const siderWidth = computed(() => (collapsed.value ? 80 : 200) + 'px')
</script>

<template>
  <div>
    <a-layout style="min-height: 100vh">
      <LayoutSider v-model:collapsed="collapsed" class="lo-side"></LayoutSider>
      <a-layout v-auto-animate="{ duration: 200 }" :style="{ marginLeft: siderWidth }">
        <LayoutHeader class="lo-head"></LayoutHeader>
        <LayoutContent>
          <slot></slot>
        </LayoutContent>
        <LayoutFooter></LayoutFooter>
      </a-layout>
    </a-layout>
  </div>
</template>

<style scoped>
.lo-side {
  overflow: auto;
  height: 100vh;
  position: fixed;
  top: 0;
  left: 0;
  z-index: 2;
}
.lo-head {
  background: #fff;
  position: sticky;
  z-index: 1;
  top: 0;
  box-shadow:
    0 1px 2px 0 rgba(0, 0, 0, 0.03),
    0 1px 6px -1px rgba(0, 0, 0, 0.02),
    0 2px 4px 0 rgba(0, 0, 0, 0.02);

  display: flex;
  justify-content: right;
  align-items: center;
  padding: 0 16px;
}
</style>
