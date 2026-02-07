<template>
  <el-container class="page">
    <el-header class="header">
      <div class="header-left">
        <div class="logo">智能招聘系统</div>
      </div>

      <div class="header-center">
        <el-input v-model="keyword" placeholder="搜索岗位 / 内容" clearable />
      </div>

      <div class="header-right">
        <el-select v-model="role" size="small" style="width: 110px" @change="onRoleChange">
          <el-option label="求职者(USER)" value="USER" />
          <el-option label="HR" value="HR" />
          <el-option label="管理员(ADMIN)" value="ADMIN" />
        </el-select>
        <el-button size="small" @click="go('/profile')">个人中心</el-button>
      </div>
    </el-header>

    <el-container>
      <el-aside width="220px" class="aside">
        <el-menu :default-active="activePath" @select="go">
          <el-menu-item index="/home">首页</el-menu-item>
          <el-menu-item v-if="role === 'USER'" index="/resume">简历管理</el-menu-item>
          <el-menu-item v-if="role === 'HR'" index="/job">岗位管理</el-menu-item>
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

const router = useRouter()
const route = useRoute()

const keyword = ref('')
const role = ref(localStorage.getItem('role') || 'USER')

const activePath = computed(() => {
  return route.path === '/' ? '/home' : route.path
})

function go(path) {
  router.push(path)
}

function onRoleChange(val) {
  localStorage.setItem('role', val)
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
