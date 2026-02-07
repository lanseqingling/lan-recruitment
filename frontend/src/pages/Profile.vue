<template>
  <el-card>
    <template #header>个人中心（占位）</template>
    <el-descriptions :column="1" border>
      <el-descriptions-item label="用户名">demo</el-descriptions-item>
      <el-descriptions-item label="角色">{{ role }}</el-descriptions-item>
    </el-descriptions>

    <div class="actions">
      <el-button @click="goLogin">退出登录</el-button>
    </div>
  </el-card>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const role = ref(localStorage.getItem('role') || 'USER')

function goLogin() {
  router.push('/login')
}
</script>

<style scoped>
.actions {
  margin-top: 16px;
}
</style>
