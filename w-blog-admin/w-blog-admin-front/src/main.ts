import 'reset-css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'
import { autoAnimatePlugin } from '@formkit/auto-animate/vue'
import * as Icons from '@ant-design/icons-vue'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

import App from './App.vue'
import router from './router'

// 创建vue实例
const app = createApp(App)

// 创建pinia
const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)

// 给组合式store内部添加重置
pinia.use(({ store }) => {
  const initialState = JSON.parse(JSON.stringify(store.$state))
  store.$reset = () => {
    store.$state = JSON.parse(JSON.stringify(initialState))
  }
})

// 装载pinia状态管理
app.use(pinia)

// 装载路由
app.use(router)

// 装载动画插件
autoAnimatePlugin
app.use(autoAnimatePlugin)

// 装载全部图标，方便动态加载
for (const name in Icons) {
  app.component(name, (Icons as any)[name])
}

// 挂载到dom上
app.mount('#app')
