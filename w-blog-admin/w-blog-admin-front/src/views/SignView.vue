<script lang="ts" setup>

</script>

<template>
  <div class="sign-background">
    <a-typography-title class="app-title">W-Blog管理后台</a-typography-title>
    <RouterView v-slot="{ Component }">
      <Transition>
        <component :is="Component" />
      </Transition>
    </RouterView>
  </div>
</template>

<style scoped>
.sign-background {
  width: 100%;
  height: 100vh;
  background-image: linear-gradient(-20deg, #b721ff 0%, #21d4fd 100%);
  display: flex;
  flex-direction: column;
  align-items: center;
  user-select: none;
}
.app-title {
  margin: 200px auto 100px auto;
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
