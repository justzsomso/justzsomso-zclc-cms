package com.zclc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zclc.domain.ResponseResult;
import com.zclc.domain.entity.User;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-01-12 17:29:22
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);
}

