<template>
  <div class="wrap">
    <el-card class="card">
      <div class="title">注册账号</div>
      <div class="desc">用户名 + 密码 + 邮箱 + 验证码</div>

      <el-form :model="form" label-position="top">
        <el-form-item label="用户名">
          <el-input v-model="form.username" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="form.password" type="password" show-password placeholder="请输入密码" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" placeholder="请输入邮箱" />
        </el-form-item>
        <el-form-item label="验证码">
          <el-input v-model="form.code" placeholder="请输入验证码" />
        </el-form-item>
      </el-form>

      <el-button type="primary" class="btn" @click="onRegister">注册</el-button>
      <el-button class="btn" @click="goLogin">返回登录</el-button>
    </el-card>
  </div>
</template>

<script setup>
import { reactive } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()
const form = reactive({ username: '', password: '', email: '', code: '' })

function onRegister() {
  router.push('/login')
}

function goLogin() {
  router.push('/login')
}
</script>

<style scoped>
.wrap {
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 24px;
  background: #f5f7fa;
}

.card {
  width: 420px;
}

.title {
  font-size: 18px;
  font-weight: 600;
  margin-bottom: 6px;
}

.desc {
  font-size: 12px;
  color: #909399;
  margin-bottom: 14px;
}

.btn {
  width: 100%;
  margin-top: 10px;
}
</style>
