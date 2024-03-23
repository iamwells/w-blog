import { ref } from 'vue'
import { defineStore } from 'pinia'

export const useMenuConfigStore = defineStore(
  'menuConfig',
  () => {
    const collapsed = ref(false)

    return { collapsed }
  },
  {
    persist: true
  }
)
