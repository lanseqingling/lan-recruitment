package com.lanrecruitment.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.lanrecruitment.service.UserService;
import com.lanrecruitment.vo.CurrentUserVO;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public CurrentUserVO getMe() {
        CurrentUserVO vo = new CurrentUserVO();
        if (StpUtil.isLogin()) {
            vo.setId(StpUtil.getLoginIdAsLong());
        }
        vo.setUsername("demo");
        vo.setRole("USER");
        return vo;
    }
}
