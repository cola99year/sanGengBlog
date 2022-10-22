package com.cola.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cola.domain.ResponseResult;
import com.cola.domain.entity.User;
import com.cola.domain.vo.UserInfoVo;
import com.cola.mapper.UserMapper;
import com.cola.service.UserService;
import com.cola.utils.BeanCopyUtils;
import com.cola.utils.SecurityUtils;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2022-10-20 20:29:29
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public ResponseResult userInfo() {
        //token来获取用户id
        Long userId = SecurityUtils.getUserId();
        //通过id获取用户对象
        User user = getById(userId);
        //把用户对象封装为UserInfoVo
        UserInfoVo vo = BeanCopyUtils.copyBean(user,UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }
}

