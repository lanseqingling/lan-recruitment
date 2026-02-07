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
        <el-button size="small" @click="go('/profile')">个人中心</el-button>
        <el-button size="small" @click="onLogout">退出</el-button>
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
import { computed, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { logout } from '../api/auth'

const router = useRouter()
const route = useRoute()

const keyword = ref('')
const role = ref(localStorage.getItem('role') || 'USER')

const roleText = computed(() => {
  if (role.value === 'ADMIN') return '管理员'
  if (role.value === 'HR') return 'HR'
  return '求职者'
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
  display: flex;
  align-items: center;
  gap: 12px;
}

.aside {
  background: #ffffff;
  border-right: 1px solid #ebeef5;
}

.main {
  background: #f5f7fa;
}
</style>
