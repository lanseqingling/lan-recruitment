import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../pages/Layout.vue'
import Login from '../pages/Login.vue'
import Register from '../pages/Register.vue'
import Home from '../pages/Home.vue'
import Profile from '../pages/Profile.vue'
import ResumeManage from '../pages/ResumeManage.vue'
import ApplyManage from '../pages/ApplyManage.vue'
import JobManage from '../pages/JobManage.vue'
import TagManage from '../pages/TagManage.vue'
import NoticeManage from '../pages/NoticeManage.vue'
import AuditManage from '../pages/AuditManage.vue'

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
      { path: 'apply', component: ApplyManage },
      { path: 'job', component: JobManage },
      { path: 'audit', component: AuditManage },
      { path: 'tag', component: TagManage },
      { path: 'notice', component: NoticeManage }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to) => {
  const publicPaths = ['/login', '/register']
  if (publicPaths.includes(to.path)) return true

  const token = localStorage.getItem('token')
  if (!token) return '/login'

  const role = localStorage.getItem('role')
  if (to.path === '/resume' && role !== 'USER') return '/home'
  if (to.path === '/apply' && role !== 'USER') return '/home'
  if (to.path === '/job' && role !== 'HR') return '/home'
  if ((to.path === '/audit' || to.path === '/tag' || to.path === '/notice') && role !== 'ADMIN') return '/home'
  return true
})

export default router
