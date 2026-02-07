<template>
  <div class="wrap">
    <el-card class="card">
      <div class="title">智能招聘系统</div>
      <div class="desc">毕业设计演示版（页面与接口骨架）</div>

      <el-tabs v-model="loginType" class="tabs" stretch>
        <el-tab-pane label="邮箱验证码登录" name="email">
          <el-form :model="emailForm" label-position="top">
            <el-form-item label="邮箱">
              <el-input v-model="emailForm.email" placeholder="请输入邮箱" />
            </el-form-item>
            <el-form-item label="验证码">
              <el-input v-model="emailForm.code" placeholder="请输入验证码">
                <template #append>
                  <el-button :disabled="loginCooldown > 0" @click="onSendLoginCode">
                    {{ loginCooldown > 0 ? `${loginCooldown}s` : '发送' }}
                  </el-button>
                </template>
              </el-input>
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
import { onBeforeUnmount, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { loginByEmail, loginByPassword, sendEmailCode } from '../api/auth'
import { getMe } from '../api/user'

const router = useRouter()

const loginType = ref('password')

const emailForm = reactive({ email: '', code: '' })
const pwdForm = reactive({ username: '', password: '' })
const loginCooldown = ref(0)
let loginTimer = null

function startLoginCooldown() {
  loginCooldown.value = 30
  if (loginTimer) clearInterval(loginTimer)
  loginTimer = setInterval(() => {
    loginCooldown.value -= 1
    if (loginCooldown.value <= 0) {
      loginCooldown.value = 0
      clearInterval(loginTimer)
      loginTimer = null
    }
  }, 1000)
}

async function onSendLoginCode() {
  if (!emailForm.email) {
    ElMessage.warning('请先填写邮箱')
    return
  }
  if (loginCooldown.value > 0) return
  try {
    await sendEmailCode(emailForm.email, 'LOGIN')
    ElMessage.success('验证码已发送')
    startLoginCooldown()
  } catch (e) {
    return
  }
}

async function onLogin() {
  try {
    let token = ''
    if (loginType.value === 'email') {
      token = await loginByEmail(emailForm.email, emailForm.code)
    } else {
      token = await loginByPassword(pwdForm.username, pwdForm.password)
    }
    localStorage.setItem('token', token)
    const me = await getMe()
    localStorage.setItem('role', me.role)
    router.push('/home')
  } catch (e) {
    return
  }
}

function goRegister() {
  router.push('/register')
}

onBeforeUnmount(() => {
  if (loginTimer) clearInterval(loginTimer)
})
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

.tabs {
  margin-top: 8px;
}

.btn {
  width: 100%;
  margin-top: 10px;
}

.btn + .btn {
  margin-left: 0;
}
</style>
