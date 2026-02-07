<template>
  <el-card>
    <template #header>
      <div class="header">
        <div>岗位管理</div>
        <el-button type="primary" size="small" @click="openCreate">发布岗位</el-button>
      </div>
    </template>

    <el-table :data="jobs" size="small" style="width: 100%">
      <el-table-column prop="jobName" label="岗位名称" min-width="160" />
      <el-table-column prop="city" label="城市" width="100" />
      <el-table-column prop="jobType" label="类型" width="100" />
      <el-table-column prop="salaryRange" label="薪资" width="130" />
      <el-table-column prop="status" label="状态" width="80">
        <template #default="{ row }">
          <el-tag v-if="row.status === 1" type="success">招聘中</el-tag>
          <el-tag v-else type="info">下架</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="auditStatus" label="审核" width="90">
        <template #default="{ row }">
          <el-tag v-if="row.auditStatus === 0" type="warning">待审核</el-tag>
          <el-tag v-else-if="row.auditStatus === 1" type="success">通过</el-tag>
          <el-tag v-else type="danger">拒绝</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="360">
        <template #default="{ row }">
          <el-button size="small" @click="openEdit(row)">编辑</el-button>
          <el-button size="small" @click="openTags(row)">标签</el-button>
          <el-button size="small" @click="openApplies(row)">投递</el-button>
          <el-popconfirm title="确认下架该岗位？" @confirm="remove(row)">
            <template #reference>
              <el-button size="small" type="danger">下架</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
  </el-card>

  <el-dialog v-model="editVisible" :title="editForm.id ? '编辑岗位' : '发布岗位'" width="560px">
    <el-form :model="editForm" label-position="top">
      <el-form-item label="岗位名称">
        <el-input v-model="editForm.jobName" />
      </el-form-item>
      <el-form-item label="城市">
        <el-input v-model="editForm.city" />
      </el-form-item>
      <el-form-item label="岗位类型">
        <el-input v-model="editForm.jobType" />
      </el-form-item>
      <el-form-item label="薪资范围">
        <el-input v-model="editForm.salaryRange" />
      </el-form-item>
      <el-form-item label="岗位描述">
        <el-input v-model="editForm.description" type="textarea" :rows="3" />
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="editForm.status" style="width: 120px">
          <el-option label="招聘中" :value="1" />
          <el-option label="下架" :value="0" />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="editVisible = false">取消</el-button>
      <el-button type="primary" @click="save">保存</el-button>
    </template>
  </el-dialog>

  <el-dialog v-model="tagVisible" title="配置岗位标签" width="720px">
    <div v-if="currentJob" class="tag-tip">当前岗位：{{ currentJob.jobName }}</div>
    <el-table :data="tags" size="small" style="width: 100%; margin-top: 8px">
      <el-table-column label="选择" width="60">
        <template #default="{ row }">
          <el-checkbox v-model="checkedMap[row.id]" />
        </template>
      </el-table-column>
      <el-table-column prop="tagName" label="标签" min-width="160" />
      <el-table-column prop="tagType" label="类型" width="120" />
      <el-table-column label="熟练度(技能)" width="160">
        <template #default="{ row }">
          <el-select v-if="row.tagType === 'SKILL'" v-model="proficiencyMap[row.id]" style="width: 120px">
            <el-option label="了解(1)" :value="1" />
            <el-option label="熟悉(2)" :value="2" />
            <el-option label="精通(3)" :value="3" />
          </el-select>
          <span v-else>-</span>
        </template>
      </el-table-column>
    </el-table>
    <template #footer>
      <el-button @click="tagVisible = false">取消</el-button>
      <el-button type="primary" @click="saveTags">保存标签</el-button>
    </template>
  </el-dialog>

  <el-dialog v-model="applyVisible" title="投递简历列表" width="860px">
    <el-table :data="applications" size="small" style="width: 100%">
      <el-table-column prop="resume.realName" label="姓名" width="100" />
      <el-table-column prop="resume.education" label="学历" width="110" />
      <el-table-column prop="resume.city" label="城市" width="100" />
      <el-table-column prop="matchScore" label="匹配度" width="100" />
      <el-table-column prop="applyStatus" label="状态" width="100">
        <template #default="{ row }">
          <el-tag v-if="row.applyStatus === 0" type="info">已投递</el-tag>
          <el-tag v-else-if="row.applyStatus === 1" type="warning">已查看</el-tag>
          <el-tag v-else-if="row.applyStatus === 2" type="success">通过</el-tag>
          <el-tag v-else type="danger">拒绝</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="220">
        <template #default="{ row }">
          <el-button size="small" @click="updateApply(row, 1)">标记已看</el-button>
          <el-button size="small" type="success" @click="updateApply(row, 2)">通过</el-button>
          <el-button size="small" type="danger" @click="updateApply(row, 3)">拒绝</el-button>
        </template>
      </el-table-column>
    </el-table>
    <template #footer>
      <el-button @click="applyVisible = false">关闭</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessage } from 'element-plus'
import { listTags } from '../api/tag'
import {
  hrDeleteJob,
  hrListJobTags,
  hrListApplications,
  hrListJobs,
  hrSaveJob,
  hrSaveJobTags,
  hrUpdateApplyStatus
} from '../api/job'

const jobs = ref([])
const tags = ref([])

const editVisible = ref(false)
const tagVisible = ref(false)
const applyVisible = ref(false)

const currentJob = ref(null)
const applications = ref([])

const editForm = reactive({
  id: null,
  jobName: '',
  city: '',
  salaryRange: '',
  jobType: '',
  description: '',
  status: 1
})

const checkedMap = reactive({})
const proficiencyMap = reactive({})

async function loadJobs() {
  jobs.value = await hrListJobs()
}

async function loadTags() {
  tags.value = await listTags()
  tags.value.forEach((t) => {
    if (typeof proficiencyMap[t.id] === 'undefined') proficiencyMap[t.id] = 1
    if (typeof checkedMap[t.id] === 'undefined') checkedMap[t.id] = false
  })
}

function openCreate() {
  Object.assign(editForm, {
    id: null,
    jobName: '',
    city: '',
    salaryRange: '',
    jobType: '',
    description: '',
    status: 1
  })
  editVisible.value = true
}

function openEdit(row) {
  Object.assign(editForm, JSON.parse(JSON.stringify(row)))
  editVisible.value = true
}

async function save() {
  await hrSaveJob(editForm)
  ElMessage.success('保存成功（岗位将进入待审核）')
  editVisible.value = false
  await loadJobs()
}

async function remove(row) {
  await hrDeleteJob(row.id)
  ElMessage.success('已下架')
  await loadJobs()
}

async function openTags(row) {
  currentJob.value = row
  Object.keys(checkedMap).forEach((k) => (checkedMap[k] = false))
  const list = await hrListJobTags(row.id)
  ;(list || []).forEach((t) => {
    checkedMap[t.tagId] = true
    if (t.proficiency != null) proficiencyMap[t.tagId] = t.proficiency
  })
  tagVisible.value = true
}

async function saveTags() {
  const items = tags.value
    .filter((t) => checkedMap[t.id])
    .map((t) => ({
      tagId: t.id,
      proficiency: t.tagType === 'SKILL' ? proficiencyMap[t.id] : null
    }))
  await hrSaveJobTags(currentJob.value.id, items)
  ElMessage.success('标签已保存')
  tagVisible.value = false
}

async function openApplies(row) {
  currentJob.value = row
  applications.value = await hrListApplications(row.id)
  applyVisible.value = true
}

async function updateApply(row, status) {
  await hrUpdateApplyStatus(row.applyId, status)
  ElMessage.success('已更新')
  applications.value = await hrListApplications(currentJob.value.id)
}

onMounted(async () => {
  await loadJobs()
  await loadTags()
})
</script>

<style scoped>
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.tag-tip {
  font-size: 12px;
  color: #606266;
}
</style>
