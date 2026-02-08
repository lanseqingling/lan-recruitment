CREATE DATABASE IF NOT EXISTS lan_recruitment DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE lan_recruitment;

CREATE TABLE IF NOT EXISTS sys_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码（加密存储）',
    email VARCHAR(100) NOT NULL COMMENT '邮箱',
    role VARCHAR(20) NOT NULL COMMENT '角色：USER / HR / ADMIN',
    avatar VARCHAR(255) COMMENT '头像地址',
    status TINYINT DEFAULT 1 COMMENT '账号状态：1正常 0禁用',
    audit_status TINYINT DEFAULT 1 COMMENT '审核状态：0待审核 1通过 2拒绝（HR/岗位审核用）',
    real_name VARCHAR(20) COMMENT '真实姓名',
    phone VARCHAR(20) COMMENT '手机号',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_username (username),
    UNIQUE KEY uk_email (email),
    KEY idx_role (role),
    KEY idx_audit_status (audit_status)
) COMMENT='系统用户表';

CREATE TABLE IF NOT EXISTS tag (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '标签ID',
    tag_name VARCHAR(50) NOT NULL COMMENT '标签名称',
    tag_type VARCHAR(30) NOT NULL COMMENT '标签类型：SKILL / INDUSTRY / EXPERIENCE',
    base_weight INT DEFAULT 1 COMMENT '标签基础权重',
    status TINYINT DEFAULT 1 COMMENT '状态：1启用 0停用',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    UNIQUE KEY uk_tag_name_type (tag_name, tag_type)
) COMMENT='系统统一标签库';

CREATE TABLE IF NOT EXISTS resume (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '简历ID',
    user_id BIGINT NOT NULL COMMENT '关联求职者ID',
    resume_name VARCHAR(100) NOT NULL COMMENT '简历名称（如“我的技术简历”）',
    real_name VARCHAR(20) NOT NULL COMMENT '姓名',
    gender VARCHAR(10) COMMENT '性别：MALE/FEMALE',
    city VARCHAR(50) COMMENT '所在城市',
    education VARCHAR(20) COMMENT '学历（本科/硕士等）',
    work_years VARCHAR(20) COMMENT '工作年限（如3年）',
    expect_job_type VARCHAR(50) COMMENT '期望岗位类型',
    expect_salary VARCHAR(50) COMMENT '期望薪资',
    work_desc TEXT COMMENT '工作描述',
    file_name VARCHAR(255) COMMENT '附件简历文件名',
    file_url VARCHAR(255) COMMENT '附件简历访问URL',
    file_type VARCHAR(20) COMMENT '附件简历类型（pdf/jpg/png）',
    file_size BIGINT COMMENT '附件简历大小（字节）',
    is_default TINYINT DEFAULT 0 COMMENT '是否默认简历：1是 0否',
    status TINYINT DEFAULT 1 COMMENT '状态：0无效/1有效',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    KEY idx_user_id (user_id),
    KEY idx_user_default (user_id, is_default)
) COMMENT='简历表';

CREATE TABLE IF NOT EXISTS resume_tag (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
    resume_id BIGINT NOT NULL COMMENT '简历ID',
    tag_id BIGINT NOT NULL COMMENT '标签ID',
    proficiency TINYINT COMMENT '熟练度：3精通 2熟悉 1了解（非技能标签可为空）',
    weight INT COMMENT '最终计算权重',
    KEY idx_resume_id (resume_id),
    KEY idx_tag_id (tag_id),
    UNIQUE KEY uk_resume_tag (resume_id, tag_id)
) COMMENT='简历标签关联表';

CREATE TABLE IF NOT EXISTS job (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '岗位ID',
    hr_id BIGINT NOT NULL COMMENT '发布HR用户ID',
    job_name VARCHAR(100) NOT NULL COMMENT '岗位名称',
    city VARCHAR(50) COMMENT '工作城市',
    salary_range VARCHAR(50) COMMENT '薪资范围',
    job_type VARCHAR(50) COMMENT '岗位类型',
    description TEXT COMMENT '岗位描述',
    status TINYINT DEFAULT 1 COMMENT '岗位状态：1招聘中 0已下架',
    audit_status TINYINT DEFAULT 0 COMMENT '审核状态：0待审核 1通过 2拒绝',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间',
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    KEY idx_hr_id (hr_id),
    KEY idx_audit_status (audit_status),
    KEY idx_city_type (city, job_type)
) COMMENT='招聘岗位表';

CREATE TABLE IF NOT EXISTS job_tag (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
    job_id BIGINT NOT NULL COMMENT '岗位ID',
    tag_id BIGINT NOT NULL COMMENT '标签ID',
    proficiency TINYINT COMMENT '技能要求熟练度',
    weight INT COMMENT '最终计算权重',
    KEY idx_job_id (job_id),
    KEY idx_tag_id (tag_id),
    UNIQUE KEY uk_job_tag (job_id, tag_id)
) COMMENT='岗位标签关联表';

CREATE TABLE IF NOT EXISTS job_apply (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '投递ID',
    job_id BIGINT NOT NULL COMMENT '岗位ID',
    resume_id BIGINT NOT NULL COMMENT '简历ID',
    user_id BIGINT NOT NULL COMMENT '求职者用户ID',
    status TINYINT DEFAULT 0 COMMENT '投递状态：0已投递 1已查看 2通过 3拒绝',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '投递时间',
    KEY idx_job_id (job_id),
    KEY idx_user_job (user_id, job_id),
    UNIQUE KEY uk_resume_job (resume_id, job_id)
) COMMENT='岗位投递记录表';

CREATE TABLE IF NOT EXISTS match_score (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT 'ID',
    resume_id BIGINT NOT NULL COMMENT '简历ID',
    job_id BIGINT NOT NULL COMMENT '岗位ID',
    score DECIMAL(5,4) NOT NULL COMMENT '余弦相似度得分',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '计算时间',
    KEY idx_resume_score (resume_id, score),
    KEY idx_job_score (job_id, score),
    UNIQUE KEY uk_resume_job (resume_id, job_id)
) COMMENT='简历与岗位匹配度结果表';

CREATE TABLE IF NOT EXISTS notice (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '公告ID',
    title VARCHAR(100) NOT NULL COMMENT '公告标题',
    content TEXT NOT NULL COMMENT '公告内容',
    status TINYINT DEFAULT 1 COMMENT '状态：1发布 0下线',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '发布时间'
) COMMENT='系统公告表';

CREATE TABLE IF NOT EXISTS login_log (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '日志ID',
    user_id BIGINT COMMENT '用户ID',
    ip VARCHAR(50) COMMENT '登录IP',
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
    KEY idx_user_id (user_id)
) COMMENT='用户登录日志表';