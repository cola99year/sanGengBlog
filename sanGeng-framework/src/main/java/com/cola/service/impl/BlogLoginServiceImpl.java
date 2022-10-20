package com.cola.service.impl;

import com.cola.domain.ResponseResult;
import com.cola.domain.entity.LoginUser;
import com.cola.domain.entity.User;
import com.cola.domain.vo.BlogUserLoginVo;
import com.cola.domain.vo.UserInfoVo;
import com.cola.service.BlogLoginService;
import com.cola.utils.BeanCopyUtils;
import com.cola.utils.JwtUtil;
import com.cola.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @Author: cola99year
 * @Date: 2022/10/19 10:03
 */
@Service
public class BlogLoginServiceImpl implements BlogLoginService {
    //调用authenticate方法来进行用户认证
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public ResponseResult login(User user) {
        //authticate方法需要Authentication类型的参数
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        //调用authenticate方法，返回Authentication给UserDetails实现类去数据库查询用户信息，再返回到这里来
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //如果查询不到该用户，抛出异常
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或密码错误");
        }
        //查询到用户，将authentication对象转为LoginUser对象
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        //取出用户id，转成redis所需的String类型
        String userId = loginUser.getUser().getId().toString();
        //使用id生成jwt
        String jwt = JwtUtil.createJWT(userId);
        //把id和loginUser存入redis
        redisUtil.set("bloglogin:"+userId,loginUser);
        //把User封装为UserInfo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        //把Jwt和userInfoVo封装为BlogUserLoginVo
        BlogUserLoginVo vo = new BlogUserLoginVo(jwt, userInfoVo);
        return ResponseResult.okResult(vo);

    }

    @Override
    public ResponseResult logout() {
        //获取token（已经在过滤器中获取过token），并把loginUser存进了SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        //SecurityContextHolder内部使用了ThreadLocal进行存储，同一个线程只会拿到自己的数据，不怕并发问题
        //获取userID
        String id = loginUser.getUser().getId().toString();
        //删除redis中的用户信息
        redisUtil.del("bloglogin:"+id);
        return ResponseResult.okResult();

    }
}
