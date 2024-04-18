import {fileURLToPath, URL} from 'node:url'

import {defineConfig, loadEnv} from 'vite'
import vue from '@vitejs/plugin-vue'
import vueJsx from '@vitejs/plugin-vue-jsx'
import VueDevTools from 'vite-plugin-vue-devtools'
import Components from 'unplugin-vue-components/vite'
import {AntDesignVueResolver} from 'unplugin-vue-components/resolvers'

// https://vitejs.dev/config/
export default defineConfig((env) => {
  console.log('@@@current mode', env.mode)
  process.env = {...process.env, ...loadEnv(env.mode, process.cwd())}

  return {
    plugins: [
      vue(),
      vueJsx(),
      VueDevTools(),
      Components({
        resolvers: [
          AntDesignVueResolver({
            importStyle: false, // css in js
            resolveIcons: true
          })
        ]
      })
    ],
    resolve: {
      alias: {
        '@': fileURLToPath(new URL('./src', import.meta.url))
      }
    },
    server: {
      proxy: {
        '/api': {
          target: process.env.VITE_BACK_BASE_URL,
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/api/, '')
        }
      }
    }
  }
})
