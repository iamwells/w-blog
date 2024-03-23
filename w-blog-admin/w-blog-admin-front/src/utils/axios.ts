import { useAuthStore } from '@/stores/auth'
import axios from 'axios'
import { message } from 'ant-design-vue'
import progress from '@/utils/nprogress'

// axios.defaults.baseURL = import.meta.env.DEV ? '/api' : import.meta.env.VITE_BACK_BASE_URL
// axios.defaults.withCredentials = true
const headerName = import.meta.env.VITE_AUTH_TOKEN_NAME

// 添加请求拦截器
axios.interceptors.request.use(
  function (config) {
    // 在发送请求之前加上authtoken
    const authStore = useAuthStore()
    config.headers[headerName] = authStore.token
    progress.start()

    return config
  },
  function (error) {
    progress.done()
    // 对请求错误做些什么
    message.error(error)
    return Promise.reject(error)
  }
)

// 添加响应拦截器
axios.interceptors.response.use(
  function (response) {
    progress.done()
    // 2xx 范围内的状态码都会触发该函数。
    // 对响应数据做点什么
    return response
  },
  function (error) {
    progress.done()
    // 超出 2xx 范围的状态码都会触发该函数。
    // 对响应错误做点什么
    message.error(error)
    return Promise.reject(error)
  }
)

export default axios
