<template>
  <el-card>
    <template #header>个人中心</template>

    <el-descriptions v-if="me" :column="1" border>
      <el-descriptions-item label="用户ID">{{ me.id }}</el-descriptions-item>
      <el-descriptions-item label="用户名">{{ me.username }}</el-descriptions-item>
      <el-descriptions-item label="角色">{{ me.role }}</el-descriptions-item>
    </el-descriptions>

    <el-divider />

    <el-form :model="profileForm" label-position="top" style="max-width: 520px">
      <el-form-item label="姓名">
        <el-input v-model="profileForm.realName" />
      </el-form-item>
      <el-form-item label="手机号">
        <el-input v-model="profileForm.phone" />
      </el-form-item>
      <el-button type="primary" @click="saveProfile">保存资料</el-button>
    </el-form>

    <el-divider />

    <el-form :model="pwdForm" label-position="top" style="max-width: 520px">
      <el-form-item label="旧密码">
        <el-input v-model="pwdForm.oldPassword" type="password" show-password />
      </el-form-item>
      <el-form-item label="新密码">
        <el-input v-model="pwdForm.newPassword" type="password" show-password />
      </el-form-item>
      <el-button type="primary" @click="savePassword">修改密码</el-button>
    </el-form>
  </el-card>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { getMe, getProfile, updateProfile, changePassword } from '../api/user'

const router = useRouter()
const me = ref(null)
const profileForm = reactive({ realName: '', phone: '' })
const pwdForm = reactive({ oldPassword: '', newPassword: '' })

async function load() {
  me.value = await getMe()
  const profile = await getProfile()
  profileForm.realName = profile.realName || ''
  profileForm.phone = profile.phone || ''
}

async function saveProfile() {
  await updateProfile(profileForm.realName, profileForm.phone)
  ElMessage.success('资料已保存')
}

async function savePassword() {
  await changePassword(pwdForm.oldPassword, pwdForm.newPassword)
  ElMessage.success('密码已修改，请重新登录')
  localStorage.removeItem('token')
  localStorage.removeItem('role')
  router.push('/login')
}

onMounted(load)
</script>

<style scoped>
</style>
