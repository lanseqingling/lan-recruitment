import axios from 'axios'

const request = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL || '',
  timeout: 15000,
  withCredentials: true
})

request.interceptors.response.use(
  (resp) => resp.data,
  (error) => Promise.reject(error)
)

export default request
