<template>
  <div class="home">
    <div class="left">
      <el-card>
        <template #header>筛选条件</template>
        <el-form label-position="top">
          <el-form-item label="关键词">
            <el-input v-model="filters.keyword" placeholder="岗位名称 / 技能" clearable />
          </el-form-item>
          <el-form-item label="城市">
            <el-input v-model="filters.city" placeholder="如：北京" clearable />
          </el-form-item>
          <el-form-item label="岗位类型">
            <el-input v-model="filters.jobType" placeholder="如：后端开发" clearable />
          </el-form-item>
          <el-button type="primary" style="width: 100%" :loading="loading" @click="onSearch">查询</el-button>
        </el-form>
      </el-card>
    </div>

    <div class="right">
      <el-card v-if="role === 'USER'" class="mb">
        <template #header>推荐岗位</template>
        <div v-if="recommendJobs.length === 0" class="empty">暂无推荐（请先创建简历并配置标签）</div>
        <el-table v-else :data="recommendJobs" size="small" style="width: 100%; margin-top: 10px">
          <el-table-column prop="jobName" label="岗位" min-width="160" />
          <el-table-column prop="city" label="城市" width="90" />
          <el-table-column prop="jobType" label="类型" width="90" />
          <el-table-column prop="salaryRange" label="薪资" width="120" />
          <el-table-column prop="matchScore" label="匹配度" width="90" />
          <el-table-column label="操作" width="140">
            <template #default="{ row }">
              <el-button size="small" @click="openDetail(row)">详情</el-button>
              <el-button size="small" type="primary" @click="onApply(row)">投递</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-card>

      <el-card v-for="job in jobs" :key="job.id" class="mb">
        <div class="job-title">{{ job.jobName }}</div>
        <div class="job-meta">
          <span>城市：{{ job.city }}</span>
          <span>类型：{{ job.jobType }}</span>
          <span>薪资：{{ job.salaryRange }}</span>
        </div>
        <div class="job-desc">{{ job.description }}</div>
        <div class="job-actions">
          <el-button size="small" @click="openDetail(job)">查看详情</el-button>
          <el-button v-if="role === 'USER'" size="small" type="primary" @click="onApply(job)">投递简历</el-button>
        </div>
      </el-card>

      <el-button style="width: 100%" :disabled="loading">加载更多</el-button>
    </div>
  </div>

  <el-dialog v-model="detailVisible" title="岗位详情" width="520px">
    <el-descriptions v-if="currentJob" :column="1" border>
      <el-descriptions-item label="岗位名称">{{ currentJob.jobName }}</el-descriptions-item>
      <el-descriptions-item label="城市">{{ currentJob.city }}</el-descriptions-item>
      <el-descriptions-item label="类型">{{ currentJob.jobType }}</el-descriptions-item>
      <el-descriptions-item label="薪资">{{ currentJob.salaryRange }}</el-descriptions-item>
      <el-descriptions-item v-if="currentJob.matchScore != null" label="匹配度">{{ currentJob.matchScore }}</el-descriptions-item>
      <el-descriptions-item label="描述">{{ currentJob.description }}</el-descriptions-item>
    </el-descriptions>
    <template #footer>
      <el-button @click="detailVisible = false">关闭</el-button>
      <el-button v-if="role === 'USER'" type="primary" @click="onApply(currentJob)">投递</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { onMounted, reactive, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { listJobs, recommendJobs as fetchRecommendJobsApi } from '../api/job'
import { applyJob, listMyResumes } from '../api/resume'

const filters = reactive({ keyword: '', city: '', jobType: '' })
const route = useRoute()
const router = useRouter()

const role = ref(localStorage.getItem('role') || 'USER')
const loading = ref(false)

const jobs = ref([])
const recommendJobs = ref([])
const resumes = ref([])

const detailVisible = ref(false)
const currentJob = ref(null)

async function fetchPublicJobs() {
  loading.value = true
  try {
    jobs.value = await listJobs({
      keyword: filters.keyword,
      city: filters.city,
      jobType: filters.jobType
    })
  } finally {
    loading.value = false
  }
}

async function fetchRecommendJobs() {
  if (role.value !== 'USER') return
  try {
    recommendJobs.value = await fetchRecommendJobsApi()
  } catch (e) {
    recommendJobs.value = []
  }
}

async function onSearch() {
  router.push({ path: '/home', query: { keyword: filters.keyword || '' } })
  await fetchPublicJobs()
}

function openDetail(job) {
  currentJob.value = job
  detailVisible.value = true
}

async function ensureResumes() {
  if (resumes.value.length > 0) return
  resumes.value = await listMyResumes()
}

async function onApply(job) {
  if (!job) return
  await ensureResumes()
  if (resumes.value.length === 0) {
    ElMessage.warning('请先创建简历')
    router.push('/resume')
    return
  }
  const defaultResume = resumes.value.find((r) => r.isDefault === 1) || resumes.value[0]
  await applyJob(job.id, defaultResume.id)
  ElMessage.success('已投递')
}

onMounted(async () => {
  filters.keyword = route.query.keyword || ''
  await fetchPublicJobs()
  await fetchRecommendJobs()
})

watch(
  () => route.query.keyword,
  async (val) => {
    filters.keyword = val || ''
    await fetchPublicJobs()
  }
)
</script>

<style scoped>
.home {
  display: flex;
  gap: 16px;
}

.left {
  width: 320px;
}

.right {
  flex: 1;
  min-width: 0;
}

.mb {
  margin-bottom: 12px;
}

.job-title {
  font-weight: 600;
  margin-bottom: 8px;
}

.job-meta {
  display: flex;
  gap: 16px;
  font-size: 12px;
  color: #606266;
  margin-bottom: 10px;
}

.job-desc {
  font-size: 13px;
  color: #303133;
  margin-bottom: 12px;
}

.job-actions {
  display: flex;
  gap: 10px;
  align-items: center;
}

.empty {
  padding: 10px 0;
  color: #909399;
  font-size: 12px;
}
</style>
