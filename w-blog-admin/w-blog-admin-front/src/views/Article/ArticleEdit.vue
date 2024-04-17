<script setup lang="ts">
import { defineProps } from 'vue'
import { MdEditor } from 'md-editor-v3'
import 'md-editor-v3/lib/style.css'

defineProps({
  text: {
    type: String,
    required: true
  }
})
</script>

<template>
  <MdEditor :model-value="text" @on-change="(str) => $emit('update:text', str)" />
</template>

<style></style>
