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
          <el-button type="primary" style="width: 100%" @click="onSearch">查询</el-button>
        </el-form>
      </el-card>
    </div>

    <div class="right">
      <el-card class="mb">
        <template #header>推荐岗位（占位）</template>
        <el-alert
          type="info"
          :closable="false"
          title="推荐结果将基于余弦相似度对岗位与简历标签向量计算得分，并按得分排序展示。"
        />
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
          <el-tag type="success">匹配度：{{ job.matchScore }}</el-tag>
          <el-button size="small">查看详情</el-button>
          <el-button size="small" type="primary">投递简历</el-button>
        </div>
      </el-card>

      <el-button style="width: 100%" @click="loadMore">加载更多</el-button>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from 'vue'

const filters = reactive({ keyword: '', city: '', jobType: '' })

const jobs = ref([
  {
    id: 1,
    jobName: '后端开发工程师',
    city: '北京',
    jobType: '后端',
    salaryRange: '15k-25k',
    matchScore: '0.8231',
    description: '岗位描述占位：Java/Spring Boot/MySQL，熟悉常见后端开发流程。'
  },
  {
    id: 2,
    jobName: '前端开发工程师',
    city: '上海',
    jobType: '前端',
    salaryRange: '12k-20k',
    matchScore: '0.7645',
    description: '岗位描述占位：Vue 3/Vite/Element Plus，能独立完成页面开发。'
  }
])

function onSearch() {
}

function loadMore() {
  const nextId = jobs.value.length + 1
  jobs.value.push({
    id: nextId,
    jobName: '岗位标题（占位）',
    city: filters.city || '城市',
    jobType: filters.jobType || '类型',
    salaryRange: '薪资范围',
    matchScore: '0.0000',
    description: '岗位描述占位。'
  })
}
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
</style>
