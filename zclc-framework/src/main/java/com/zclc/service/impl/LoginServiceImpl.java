package com.zclc.service.impl;

import com.zclc.domain.ResponseResult;
import com.zclc.domain.entity.LoginUser;
import com.zclc.domain.entity.User;
import com.zclc.domain.vo.UserInfoVo;
import com.zclc.domain.vo.UserLoginVo;
import com.zclc.service.LoginService;
import com.zclc.utils.BeanCopyUtils;
import com.zclc.utils.JwtUtil;
import com.zclc.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否通过认证
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }

        //获取userId 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        Long loginUserId = loginUser.getUser().getId();
        String jwt = JwtUtil.createJWT(String.valueOf(loginUserId));
        //将用户信息存入到redis
        redisCache.setCacheObject("bloglogin:" + loginUserId, loginUser);

        //将token和userinfo封装 返回
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        UserLoginVo vo = new UserLoginVo(jwt, userInfoVo);
        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult logout() {
        //解析token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        //获取userId
        Long userId = loginUser.getUser().getId();

        //删除redis中的信息
        redisCache.deleteObject("bloglogin:"+userId);

        return ResponseResult.okResult();
    }
}
