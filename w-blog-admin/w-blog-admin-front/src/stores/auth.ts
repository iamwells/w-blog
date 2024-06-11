import { ref } from 'vue'
import { defineStore, type Store } from 'pinia'
import { postSignIn } from '@/views/Sign/api/index'

interface User {
  username?: string
}

export const useAuthStore = defineStore(
  'auth',
  () => {
    const token = ref('')
    const user = ref<User>({})
    const toSignInFrom = ref<string | undefined>()
    const whiteList = ref<string[]>(['/sign/in', '/sign/up'])

    function isAuthenticated(): boolean {
      return !!token.value;
    }

    function signIn(username: string, password: string, rememberMe: boolean) {
      user.value!.username = username
      return postSignIn(username, password, rememberMe)
    }

    function signOut(this: Store) {
      this.$reset()
      user.value = { username: '' }
      token.value = ''
    }

    return { token, user, toSignInFrom, whiteList, signIn, signOut, isAuthenticated }
  },
  {
    persist: true
  }
)
