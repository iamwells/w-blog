import { ref } from 'vue'
import { defineStore } from 'pinia'
type SignType = 'usernamePassword' | 'emailCode'

export const useSignPageStore = defineStore(
  'signPage',
  () => {
    const rememberMe = ref(true)
    const signBy = ref<SignType>('usernamePassword')

    return { rememberMe, signBy }
  },
  {
    persist: true
  }
)
