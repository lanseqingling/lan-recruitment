<template>
  <div class="wrap">
    <el-card class="card">
      <div class="title">智能招聘系统</div>
      <div class="desc">毕业设计演示版（页面与接口骨架）</div>

      <div class="role-switch">
        <el-radio-group v-model="roleType">
          <el-radio-button label="USER">我要找工作</el-radio-button>
          <el-radio-button label="HR">我要招聘</el-radio-button>
        </el-radio-group>
      </div>

      <el-tabs v-model="loginType" class="tabs" stretch>
        <el-tab-pane label="邮箱验证码登录" name="email">
          <el-form :model="emailForm" label-position="top">
            <el-form-item label="邮箱">
              <el-input v-model="emailForm.email" placeholder="请输入邮箱" />
            </el-form-item>
            <el-form-item label="验证码">
              <el-input v-model="emailForm.code" placeholder="请输入验证码" />
            </el-form-item>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="用户名密码登录" name="password">
          <el-form :model="pwdForm" label-position="top">
            <el-form-item label="用户名">
              <el-input v-model="pwdForm.username" placeholder="请输入用户名" />
            </el-form-item>
            <el-form-item label="密码">
              <el-input v-model="pwdForm.password" type="password" show-password placeholder="请输入密码" />
            </el-form-item>
          </el-form>
        </el-tab-pane>
      </el-tabs>

      <el-button type="primary" class="btn" @click="onLogin">登录</el-button>
      <el-button class="btn" @click="goRegister">注册</el-button>
    </el-card>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'

const router = useRouter()

const roleType = ref('USER')
const loginType = ref('email')

const emailForm = reactive({ email: '', code: '' })
const pwdForm = reactive({ username: '', password: '' })

function onLogin() {
  localStorage.setItem('role', roleType.value)
  router.push('/home')
}

function goRegister() {
  router.push('/register')
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

.role-switch {
  margin-bottom: 12px;
}

.tabs {
  margin-top: 8px;
}

.btn {
  width: 100%;
  margin-top: 10px;
}
</style>
