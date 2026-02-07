package com.lanrecruitment.config;

import cn.dev33.satoken.filter.SaServletFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SaTokenConfig {

    @Bean
    public SaServletFilter saServletFilter() {
        return new SaServletFilter()
                .addInclude("/api/**")
                .addExclude("/api/health")
                .setAuth(obj -> SaRouter.match("/api/**", r -> {
                    if (StpUtil.isLogin()) {
                        return;
                    }
                }))
                .setError(e -> "NOT_LOGIN");
    }
}
