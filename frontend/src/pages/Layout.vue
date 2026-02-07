<template>
  <el-container class="page">
    <el-header class="header">
      <div class="header-left">
        <div class="logo">智能招聘系统</div>
      </div>

      <div class="header-center">
        <el-input v-model="keyword" placeholder="搜索岗位 / 内容" clearable @keyup.enter="onSearch" />
      </div>

      <div class="header-right">
        <el-tag effect="plain">{{ roleText }}</el-tag>
        <el-dropdown trigger="click" @command="onUserCommand">
          <div class="user-entry">
            <div class="user-name">{{ displayName }}</div>
            <div class="user-caret">▾</div>
            <el-avatar :size="28" :src="avatarUrl">
              {{ avatarFallback }}
            </el-avatar>
          </div>
          <template #dropdown>
            <el-dropdown-menu>
              <el-dropdown-item command="profile">个人中心</el-dropdown-item>
              <el-dropdown-item divided command="logout">退出登录</el-dropdown-item>
            </el-dropdown-menu>
          </template>
        </el-dropdown>
      </div>
    </el-header>

    <el-container>
      <el-aside width="220px" class="aside">
        <el-menu :default-active="activePath" @select="go">
          <el-menu-item index="/home">首页</el-menu-item>
          <el-menu-item v-if="role === 'USER'" index="/resume">简历管理</el-menu-item>
          <el-menu-item v-if="role === 'HR'" index="/job">岗位管理</el-menu-item>
          <el-menu-item v-if="role === 'ADMIN'" index="/audit">审核管理</el-menu-item>
          <el-menu-item v-if="role === 'ADMIN'" index="/tag">标签库管理</el-menu-item>
          <el-menu-item v-if="role === 'ADMIN'" index="/notice">公告管理</el-menu-item>
        </el-menu>
      </el-aside>

      <el-main class="main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed, onBeforeUnmount, onMounted, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { logout } from '../api/auth'
import { getProfile } from '../api/user'

const router = useRouter()
const route = useRoute()

const keyword = ref('')
const role = ref(localStorage.getItem('role') || 'USER')
const profile = ref(null)

const roleText = computed(() => {
  if (role.value === 'ADMIN') return '管理员'
  if (role.value === 'HR') return 'HR'
  return '求职者'
})

const displayName = computed(() => {
  const p = profile.value
  return (p && (p.realName || p.username)) || '用户'
})

const avatarUrl = computed(() => {
  const p = profile.value
  if (!p || !p.avatar) return ''
  const a = p.avatar
  if (a.startsWith('http://') || a.startsWith('https://')) return a
  const base = import.meta.env.VITE_API_BASE_URL || ''
  return base + a
})

const avatarFallback = computed(() => {
  const name = displayName.value || ''
  return name ? name.slice(0, 1) : 'U'
})

const activePath = computed(() => {
  return route.path === '/' ? '/home' : route.path
})

function go(path) {
  router.push(path)
}

function onSearch() {
  router.push({ path: '/home', query: { keyword: keyword.value } })
}

function onUserCommand(cmd) {
  if (cmd === 'profile') {
    go('/profile')
    return
  }
  if (cmd === 'logout') {
    onLogout()
  }
}

async function onLogout() {
  try {
    await logout()
  } finally {
    localStorage.removeItem('token')
    localStorage.removeItem('role')
    ElMessage.success('已退出登录')
    router.push('/login')
  }
}

async function loadProfile() {
  try {
    profile.value = await getProfile()
  } catch (e) {
    profile.value = null
  }
}

function onProfileChanged() {
  loadProfile()
}

onMounted(() => {
  loadProfile()
  window.addEventListener('profile-changed', onProfileChanged)
})

onBeforeUnmount(() => {
  window.removeEventListener('profile-changed', onProfileChanged)
})
</script>

<style scoped>
.page {
  height: 100%;
}

.header {
  display: flex;
  align-items: center;
  gap: 16px;
  background: #ffffff;
  border-bottom: 1px solid #ebeef5;
}

.header-left {
  width: 220px;
  display: flex;
  align-items: center;
}

.logo {
  font-weight: 600;
  color: #0f4c81;
}

.header-center {
  flex: 1;
  max-width: 520px;
}

.header-right {
  margin-left: auto;
  display: flex;
  align-items: center;
  gap: 12px;
}

.user-entry {
  display: flex;
  align-items: center;
  gap: 8px;
  cursor: pointer;
  padding: 4px 6px;
  border-radius: 4px;
}

.user-entry:hover {
  background: #f5f7fa;
}

.user-name {
  font-size: 13px;
  color: #303133;
  max-width: 120px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.user-caret {
  color: #909399;
  font-size: 12px;
}

.aside {
  background: #ffffff;
  border-right: 1px solid #ebeef5;
}

.main {
  background: #f5f7fa;
}
</style>
