# lan-recruitment（毕业设计-智能招聘系统）

本仓库为 Monorepo 结构：

- backend：后端（Java 8 + Spring Boot + MyBatis-Plus + Sa-Token + MySQL）
- frontend：前端（Vue 3 + Vite + Element Plus + Axios）
- docs：需求与规范文档

## 后端启动

1. 执行数据库初始化 SQL ：`/backend/sql/init.sql`

2. 配置 `/backend/src/main/resources/application-dev.yml` ：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/lan_recruitment?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: 123456 # 修改为你的密码
    driver-class-name: com.mysql.cj.jdbc.Driver

email:
  host: smtp.qq.com # 邮箱host（以 QQ 邮箱为例）
  from: # 修改为你的邮箱账号
  password: # 邮箱服务商提供的 SMTP 授权码/应用专用密码。
  username: # 修改为你的邮箱账号
```

3. 执行启动命令：

```bash
cd backend

mvn -q -DskipTests spring-boot:run
```

4. 健康检查：GET http://localhost:8080/api/health

## 前端启动

1. 配置后端请求地址 `/frontend/.env` ：

```bash
VITE_API_BASE_URL=http://localhost:8080
```

2. 执行启动命令：

```bash
cd frontend

npm i

npm run dev
```

## 内置账号

- 管理员：admin / admin123456
- 求职者：user1 / user123456
- HR：hr1 / hr123456
