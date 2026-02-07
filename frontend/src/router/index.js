import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../pages/Layout.vue'
import Login from '../pages/Login.vue'
import Register from '../pages/Register.vue'
import Home from '../pages/Home.vue'
import Profile from '../pages/Profile.vue'
import ResumeManage from '../pages/ResumeManage.vue'
import JobManage from '../pages/JobManage.vue'
import TagManage from '../pages/TagManage.vue'
import NoticeManage from '../pages/NoticeManage.vue'

const routes = [
  { path: '/', redirect: '/home' },
  { path: '/login', component: Login },
  { path: '/register', component: Register },
  {
    path: '/',
    component: Layout,
    children: [
      { path: 'home', component: Home },
      { path: 'profile', component: Profile },
      { path: 'resume', component: ResumeManage },
      { path: 'job', component: JobManage },
      { path: 'tag', component: TagManage },
      { path: 'notice', component: NoticeManage }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
