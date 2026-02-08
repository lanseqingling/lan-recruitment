package com.lanrecruitment.config;

import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.context.SaHolder;
import com.lanrecruitment.common.enums.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SaTokenConfig {

    @Bean
    public SaServletFilter saServletFilter() {
        return new SaServletFilter()
                .addInclude("/api/**")
                .addExclude("/api/health", "/api/public/**")
                .setAuth(obj -> {
                    if ("OPTIONS".equalsIgnoreCase(SaHolder.getRequest().getMethod())) {
                        return;
                    }
                    SaRouter.match("/api/admin/**", r -> {
                        StpUtil.checkLogin();
                        StpUtil.checkRole(UserRole.ADMIN.name());
                    });
                    SaRouter.match("/api/hr/**", r -> {
                        StpUtil.checkLogin();
                        StpUtil.checkRole(UserRole.HR.name());
                    });
                    SaRouter.match("/api/user/**", r -> {
                        StpUtil.checkLogin();
                        StpUtil.checkRole(UserRole.USER.name());
                    });
                    SaRouter.match("/api/common/**", StpUtil::checkLogin);
                })
                .setError(e -> {
                    if (e instanceof NotLoginException) {
                        return json(401, "未登录");
                    }
                    if (e instanceof NotRoleException) {
                        return json(403, "无权限");
                    }
                    return json(500, "系统异常");
                });
    }

    private String json(int code, String message) {
        return "{\"code\":" + code + ",\"message\":\"" + message + "\",\"data\":null}";
    }
}
