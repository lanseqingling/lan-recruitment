## 现状盘点（基于现有实现）
- 用户侧：后端已有 `GET /api/user/apply/list` 返回 `JobApply`（仅 jobId/resumeId/status/createdAt），前端缺少页面/路由/侧边栏入口；也缺少“查看岗位信息/查看投递简历/进度卡片/更换投递简历”的完整交互。
- HR侧：已具备「岗位投递列表」与「通过/拒绝/标记已看」能力（`GET /api/hr/job/applications` + `POST /api/hr/job/apply/status`），但缺少“筛选条件（学历/标签等）”与更完整的“查看简历信息（含附件链接/标签展示）”体验。

## 目标功能（按你的需求对齐）
### 1）用户：已投递岗位查询（侧边栏入口 + 卡片展示）
- 新增侧边栏菜单：投递记录（USER 可见）。
- 新增页面：以卡片形式展示每条投递：岗位信息、投递时间、投递简历、投递状态进度。
- 支持：
  - 查看已投递岗位信息（复用岗位详情弹窗样式）。
  - 查看本次投递使用的具体简历信息（弹窗/抽屉展示 ResumeVO）。
  - 修改本次投递所使用的简历（选择自己的简历并更新）。

### 2）HR：岗位简历筛选与查看
- 在现有「岗位管理 → 投递简历列表」弹窗内增强：
  - 增加筛选栏：学历、标签（多选）、最低匹配度。
  - 列表中展示投递人简历标签、附件下载入口。
  - 支持打开简历详情抽屉，并在同处执行“通过/拒绝/标记已看”。

## 后端改造（保持现有风格：Controller + Service + VO/DTO）
### A. 用户投递记录：返回增强 VO（避免前端 N+1 查询）
- 保留原接口兼容性：`GET /api/user/apply/list` 不改或仅内部复用。
- 新增接口（推荐）：`GET /api/user/apply/list/detail` 返回 `List<UserApplyVO>`：
  - 字段：applyId、applyStatus、applyTime、job（含 jobName/city/jobType/salaryRange/createdAt 等）、resume（含 resumeName/realName/education/fileUrl 等）。
  - 实现：按 userId 查 applies → 批量查 Job/Resume → 组装 VO。

### B. 用户“更换投递简历”接口
- 新增接口：`POST /api/user/apply/resume/update`（DTO：applyId、resumeId）。
- 校验：
  - apply 属于当前用户；新 resume 属于当前用户且可用；
  - 只允许状态为 0/1（已投递/已查看）时更换；2/3（通过/拒绝）禁止更换；
  - 防止同一岗位重复绑定同一简历（避免 jobId+resumeId 冲突）。

### C. HR 侧筛选所需数据补齐（简历标签）
- 扩展 `HrJobApplicationVO`：增加 `resumeTags: List<ResumeTagVO>`（或放在 resume 内）。
- `HrJobServiceImpl.listApplications` 中批量查询 `resume_tag` + `tag`，组装标签列表，供前端按标签筛选与展示。

## 前端改造（保持现有 Vue3 + ElementPlus 风格）
### A. 用户侧：投递记录页面
- 新增页面组件：`frontend/src/pages/ApplyManage.vue`（命名沿用现有 *Manage.vue* 风格）。
- 新增路由：`/apply`（仅 USER 允许访问）。
- 侧边栏新增菜单项（USER 可见）：投递记录。
- 页面交互：
  - 顶部可选简单筛选：状态筛选、关键字（岗位名）。
  - 卡片内容：岗位信息 + 发布时间/投递时间 + 状态 Tag + Steps（投递→已查看→结果）。
  - 操作：查看岗位 / 查看简历 / 更换投递简历（下拉选择 + 按钮，状态>=2禁用）。
- 新增前端 API：
  - `listMyApplyDetail()` 调用新接口；
  - `updateApplyResume(applyId, resumeId)` 调用更新接口。

### B. HR 侧：岗位投递列表增强
- 在 `JobManage.vue` 的“投递简历列表”弹窗：
  - 增加筛选栏（学历、标签、最低匹配度）。
  - 表格新增列：标签（`el-tag` 列表）。
  - 新增“查看简历”按钮：抽屉展示简历字段与附件链接（有 fileUrl 时提供打开/下载）。

## 验证与回归
- 后端：`mvn -DskipTests package`（保证编译通过）。
- 前端：`npm run build`（保证构建通过）。
- 基本用例：
  - USER 投递后在“投递记录”可见、状态随 HR 操作变化；可在 0/1 状态更换简历。
  - HR 在岗位投递列表可按学历/标签/匹配度筛选，并对申请执行通过/拒绝。

如果你确认该方案，我将按上述步骤开始逐项实现（优先把用户投递记录与 HR 筛选闭环跑通）。