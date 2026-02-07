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
