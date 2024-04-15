package com.zclc.controller;

import com.zclc.annotation.SystemLog;
import com.zclc.domain.ResponseResult;
import com.zclc.domain.dto.LoginUserDto;
import com.zclc.domain.entity.User;
import com.zclc.enums.AppHttpCodeEnum;
import com.zclc.exception.SystemException;
import com.zclc.service.LoginService;
import com.zclc.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "登录注销相关接口")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    @SystemLog(businessName = "登录接口")
    @ApiOperation(value = "登录接口")
    public ResponseResult login(@RequestBody LoginUserDto loginUserDto){
        if (!StringUtils.hasText(loginUserDto.getUserName())){
            //提示 必须要传用户名
//            throw new RuntimeException();
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        User user = BeanCopyUtils.copyBean(loginUserDto, User.class);
        return loginService.login(user);
    }


    @PostMapping("/logout")
    @SystemLog(businessName = "退出登录接口")
    @ApiOperation(value = "退出登录接口")
    public ResponseResult logout(){
        return loginService.logout();
    }
}
