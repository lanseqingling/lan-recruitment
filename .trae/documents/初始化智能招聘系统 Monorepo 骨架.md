## 目标与边界
- 基于 4 份文档搭建 monorepo 工程骨架：backend(Spring Boot + MyBatis-Plus + Sa-Token + MySQL) 与 frontend(Vue3 + Vite + Axios + Element Plus)。
- 只搭“可运行的空壳”：页面/接口/分层齐全，但不实现具体业务逻辑与算法。

## 目录结构
- 在仓库根目录创建：backend/、frontend/（docs/ 保持不动），符合文档中的分层与命名约定。

## 后端（backend）初始化内容
- Maven 工程（Java 8 + Spring Boot）：pom.xml
  - 依赖：spring-boot-starter-web、spring-boot-starter-validation、mybatis-plus-boot-starter、mysql-connector-j(或 mysql-connector-java 与 Spring Boot 版本匹配)、sa-token-spring-boot-starter、hutool-all、lombok。
- 代码分层骨架（严格 controller → service → mapper，service 区分接口与 impl）：
  - Application.java（启动类）
  - common：统一返回体 Response（success/data/message/code）
  - exception：BizException + GlobalExceptionHandler（@ExceptionHandler 统一异常）
  - config：Sa-Token 基础配置、MyBatis-Plus 配置、WebMvc/CORS 配置
  - entity：按库表设计建立基础实体（User/Tag/Resume/ResumeTag/Job/JobTag/JobApply/MatchScore/Notice/LoginLog）
  - mapper：对应 Mapper 接口（MyBatis-Plus BaseMapper），不使用 XML
  - service / service/impl：对应 Service 接口与实现（方法先留空或返回占位数据）
  - controller：仅用 @GetMapping/@PostMapping 暴露占位接口（例如 health、auth、profile、job、resume、tag、notice 等模块），controller 内不写业务
  - utils：基于 Hutool 的 EmailClient 空实现（仅封装发送接口，不接入真实账号）
- resources：application.yml
  - server 端口、MySQL 连接占位、MyBatis-Plus 配置、Sa-Token 基础配置（tokenName、timeout 等）。
- backend/sql：提供 init.sql（按《库表设计》落表，但移除外键定义以满足“禁止外键”规范）。

## 前端（frontend）初始化内容
- Vite + Vue3 工程：package.json、vite.config.js、index.html
- Element Plus 全局引入、基础样式（尽量少自定义 CSS）
- Axios 封装：src/api/request.js（baseURL 来自 .env）
- Router：src/router/index.js
- 页面级为主（不做复杂组件拆分），按《前端页面》做统一布局：
  - 登录页：支持“邮箱验证码/用户名密码”两种 Tab 的静态表单（不接真实接口）
  - 注册页：用户名/密码/邮箱/验证码静态表单
  - 主页：左右布局（左侧筛选卡片、右侧岗位卡片列表占位 + 推荐位占位）
  - 个人中心页占位
  - 简历管理/岗位管理/标签库/公告 等入口页占位（根据角色显示菜单，角色暂用本地存储模拟）

## 联调与可运行性验证
- 后端：能启动、health 接口返回 Response；Swagger 不引入（文档未要求）。
- 前端：能启动、路由可跳转、Element Plus 正常渲染；请求层可用但不强依赖后端。
- 提供根目录 README（运行命令、端口、环境变量示例）。

## 交付物清单
- 完整 monorepo 目录与基础工程文件
- 后端分层代码骨架 + 统一异常/返回体 + Sa-Token 基础接入
- 前端基础布局 + 路由 + 页面占位 + Axios 封装
- init.sql（无外键版） + 运行说明