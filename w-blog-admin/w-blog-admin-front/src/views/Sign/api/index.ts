import axios from '@/utils/axios'

export const postSignIn = (username: string, password: string, rememberMe: boolean) => {
    return axios.post(`/sign/in?rememberMe=${rememberMe}`, {username, password})
}
