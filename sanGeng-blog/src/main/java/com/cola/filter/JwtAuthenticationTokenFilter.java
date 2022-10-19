package com.cola.filter;

import com.alibaba.fastjson.JSON;
import com.cola.domain.ResponseResult;
import com.cola.domain.entity.LoginUser;
import com.cola.enums.AppHttpCodeEnum;
import com.cola.utils.JwtUtil;
import com.cola.utils.RedisUtil;
import com.cola.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisUtil redisUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //获取token
        String token = httpServletRequest.getHeader("token");
        //若token为空，放行
        if(!StringUtils.hasText(token)){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        //若不为空，解析token
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            //token超时、非法;因为统一异常处理是针对controller层的，这里过滤器还没到controller层，不能使用抛出异常的方法
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
            return; // 结束一整个方法，不用向下执行了
        }
        //获取到id
        String userId = claims.getSubject();
        //通过id在Redis中查询到loginUser
        LoginUser loginUser = (LoginUser) redisUtil.get("bloglogin:" + userId);
        //若查询不到，则说明在redis中过期
        if(Objects.isNull(loginUser)){
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(httpServletResponse, JSON.toJSONString(result));
            return;
        }
        //查到了把user封装authenticationToken
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        //存入框架的上下文容器
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行！
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
