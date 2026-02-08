<template>
  <div class="home">
    <div class="left">
      <el-card>
        <template #header>筛选岗位</template>
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
          <el-form-item label="标签">
            <el-select
              v-model="filters.tagIds"
              multiple
              filterable
              remote
              clearable
              :remote-method="onSearchTags"
              :loading="tagLoading"
              placeholder="搜索并选择标签"
              style="width: 100%"
            >
              <el-option v-for="t in tagOptions" :key="t.id" :label="t.tagName" :value="t.id" />
            </el-select>
          </el-form-item>
          <el-button type="primary" style="width: 100%" :loading="loading" @click="onSearch">查询</el-button>
        </el-form>
      </el-card>
    </div>

    <div class="right">
      <el-card class="mb">
        <el-tabs v-model="activeTab" class="job-tabs" @tab-change="onTabChange">
          <el-tab-pane v-if="role === 'USER'" label="推荐职位" name="recommend" />
          <el-tab-pane label="最新职位" name="latest" />
        </el-tabs>
      </el-card>

      <div v-if="activeTab === 'recommend' && role === 'USER' && recommendJobs.length === 0" class="empty">
        暂无推荐（请先创建简历并配置标签）
      </div>

      <el-card v-for="job in displayJobs" :key="job.id" class="mb">
        <div class="job-title">{{ job.jobName }}</div>
        <div class="job-meta">
          <span>城市：{{ job.city }}</span>
          <span>类型：{{ job.jobType }}</span>
          <span>薪资：{{ job.salaryRange }}</span>
          <span v-if="job.matchScore != null">匹配度：{{ job.matchScore }}</span>
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
import { computed, onMounted, reactive, ref, watch } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { listJobs, recommendJobs as fetchRecommendJobsApi } from '../api/job'
import { applyJob, listMyResumes } from '../api/resume'
import { searchTags } from '../api/tag'

const filters = reactive({ keyword: '', city: '', jobType: '', tagIds: [] })
const route = useRoute()
const router = useRouter()

const role = ref(localStorage.getItem('role') || 'USER')
const loading = ref(false)
const tagLoading = ref(false)
const tagOptions = ref([])

const jobs = ref([])
const recommendJobs = ref([])
const resumes = ref([])
const activeTab = ref(role.value === 'USER' ? 'recommend' : 'latest')

const detailVisible = ref(false)
const currentJob = ref(null)

const displayJobs = computed(() => {
  if (role.value === 'USER' && activeTab.value === 'recommend') return recommendJobs.value
  return jobs.value
})

async function fetchPublicJobs() {
  loading.value = true
  try {
    jobs.value = await listJobs({
      keyword: filters.keyword,
      city: filters.city,
      jobType: filters.jobType,
      tagIds: Array.isArray(filters.tagIds) && filters.tagIds.length > 0 ? filters.tagIds.join(',') : undefined
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
  activeTab.value = 'latest'
  await fetchPublicJobs()
}

async function onSearchTags(query) {
  const q = (query || '').trim()
  tagLoading.value = true
  try {
    tagOptions.value = await searchTags({ keyword: q, limit: 20 })
  } catch (e) {
    tagOptions.value = []
  } finally {
    tagLoading.value = false
  }
}

async function onTabChange(name) {
  if (name === 'recommend' && role.value === 'USER' && recommendJobs.value.length === 0) {
    await fetchRecommendJobs()
  }
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
  await onSearchTags('')
  await fetchPublicJobs()
  if (role.value === 'USER' && activeTab.value === 'recommend') {
    await fetchRecommendJobs()
  }
})

watch(
  () => route.query.keyword,
  async (val) => {
    filters.keyword = val || ''
    activeTab.value = 'latest'
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

.job-tabs :deep(.el-tabs__header) {
  margin: 0;
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
  flex-wrap: wrap;
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
