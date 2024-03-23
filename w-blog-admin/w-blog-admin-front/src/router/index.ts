import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import SignView from '@/views/SignView.vue'
import { useAuthStore } from '@/stores/auth'
import { storeToRefs } from 'pinia'
import { message } from 'ant-design-vue'
import progress from '@/utils/nprogress'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
      redirect: 'dashboard',
      meta: { title: '首页' },
      beforeEnter: (to, from, next) => {
        window.document.title = 'W-Blog管理后台'
        next()
      },
      children: [
        {
          path: 'dashboard',
          name: 'dashboard',
          component: () => import('@/views/DashBoard/DashBoardView.vue'),
          meta: {
            title: '仪表盘',
            icon: 'dashboard-two-tone'
          }
        }
      ]
    },
    {
      path: '/sign',
      name: 'sign',
      component: SignView,
      redirect: '/sign/in',
      beforeEnter: (to, _from, next) => {
        window.document.title = to.meta.title as string
        next()
      },
      children: [
        {
          path: 'in',
          name: 'signIn',
          component: () => import('@/views/Sign/SignInView.vue'),
          meta: {
            title: '登录'
          }
        }
      ]
    }
  ]
})

router.beforeEach((to, from, next) => {
  progress.start()

  const authStore = useAuthStore()
  const { token, toSignInFrom, whiteList } = storeToRefs(authStore)

  if (to.fullPath === '/sign/in') {
    toSignInFrom.value = from.fullPath
  }

  if (token.value) {
    if (to.fullPath === '/sign/in') {
      next(toSignInFrom.value || '/')
    } else {
      next()
    }
  } else {
    if (whiteList.value.includes(to.fullPath)) {
      next()
    } else {
      next('/sign/in')
    }
  }
})
router.afterEach((to, from, failure) => {
  if (failure) {
    message.error(failure?.message)
  }
  progress.done()
})

export default router
