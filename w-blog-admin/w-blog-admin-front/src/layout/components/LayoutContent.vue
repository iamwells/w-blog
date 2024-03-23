<script lang="ts" setup></script>

<template>
  <a-layout-content style="margin: 0 16px">
    <a-breadcrumb style="margin: 16px 0; user-select: none">
      <a-breadcrumb-item v-for="item in $route.matched" :key="item.path">
        <a-button
          :disabled="item.path === $route.fullPath"
          type="link"
          @click="$router.push(item.path)"
          >{{ item.meta?.title || item.name }}</a-button
        ></a-breadcrumb-item
      >
    </a-breadcrumb>
    <slot></slot>
  </a-layout-content>
</template>

<style scoped></style>
