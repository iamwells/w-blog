<script lang="ts" setup></script>

<template>
  <div class="sign-background">
    <div class="sign-content">
      <a-typography-title class="app-title">W-Blog管理后台</a-typography-title>
      <RouterView v-slot="{ Component }">
        <Transition>
          <component :is="Component" />
        </Transition>
      </RouterView>
    </div>
  </div>
</template>

<style scoped>
.sign-background {
  position: fixed; /* 使div固定位置 */
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 10; /* 设置层叠顺序，如果需要的话 */
  background-image: linear-gradient(-20deg, #b721ff 0%, #21d4fd 100%);
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  user-select: none;
}
.app-title {
  text-align: center;
}

.v-enter-active,
.v-leave-active {
  transition: opacity 0.5s ease;
}

.v-enter-from,
.v-leave-to {
  opacity: 0;
}
</style>
