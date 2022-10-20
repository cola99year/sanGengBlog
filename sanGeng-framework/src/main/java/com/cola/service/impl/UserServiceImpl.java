package com.cola.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cola.domain.entity.User;
import com.cola.mapper.UserMapper;
import com.cola.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2022-10-20 20:29:29
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}

