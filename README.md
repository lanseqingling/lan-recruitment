# lan-recruitment（智能招聘系统）

本仓库为 Monorepo 结构：

- backend：后端（Java 8 + Spring Boot + MyBatis-Plus + Sa-Token + MySQL）
- frontend：前端（Vue 3 + Vite + Element Plus + Axios）
- docs：需求与规范文档

## 后端启动

```bash
cd backend
mvn -q -DskipTests spring-boot:run
```

- 默认端口：8080
- 健康检查：GET http://localhost:8080/api/health

## 前端启动

```bash
cd frontend
npm i
npm run dev
```

- 默认端口：5173

## 环境变量

前端请求地址通过 `.env` 配置：

```bash
VITE_API_BASE_URL=http://localhost:8080
```

## 后端配置（application-dev.yml）

后端本地开发配置在 [application-dev.yml](file:///Users/bytedance/java/lan-recruitment/backend/src/main/resources/application-dev.yml)，其中包含端口、数据库、Sa-Token、邮箱验证码等配置；默认启用 `dev` profile（见 [application.yml](file:///Users/bytedance/java/lan-recruitment/backend/src/main/resources/application.yml) 的 `spring.profiles.active`）。

### 数据库

请按本机 MySQL 实际情况修改：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/lan_recruitment?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: 123456
```

### 邮箱验证码（SMTP）

系统发送邮箱验证码读取 `email.*` 配置（`LOGIN`/`REGISTER` 的验证码发送接口均会触发发信）。

在 `application-dev.yml` 配置示例（以 QQ 邮箱为例）：

```yaml
email:
  host: smtp.qq.com
  username: 你的邮箱账号@qq.com
  password: 你的SMTP授权码
  from: 你的邮箱账号@qq.com
  # 可选：不配时默认 ssl=true 且端口 465
  port: 465
  ssl: true
  starttls: false
```

- `password` 不是邮箱登录密码，通常是邮箱服务商提供的 SMTP 授权码/应用专用密码。
- 出于安全考虑，不要把真实账号和授权码提交到仓库；建议仅在本地 `application-dev.yml` 配置，或用环境变量/启动参数覆盖（例如 `--email.password=xxx`）。

## 内置账号

- 管理员：admin / admin123456
- 求职者：user1 / user123456
- HR：hr1 / hr123456
