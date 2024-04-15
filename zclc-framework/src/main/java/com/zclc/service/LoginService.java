package com.zclc.service;

import com.zclc.domain.ResponseResult;
import com.zclc.domain.entity.User;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
