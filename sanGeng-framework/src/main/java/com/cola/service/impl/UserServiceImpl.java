package com.cola.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cola.domain.ResponseResult;
import com.cola.domain.entity.User;
import com.cola.domain.vo.UserInfoVo;
import com.cola.enums.AppHttpCodeEnum;
import com.cola.exception.SystemException;
import com.cola.mapper.UserMapper;
import com.cola.service.UserService;
import com.cola.utils.BeanCopyUtils;
import com.cola.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2022-10-20 20:29:29
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    /**
     * 个人中心信息查询
     * @return
     */
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

    /**
     * 个人信息更新
     * @param user
     * @return
     */
    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * 用户注册
     * @param user
     * @return
     */
    @Override
    public ResponseResult register(User user) {
        //数据非空判断
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        //数据是否进行存在，即该账号已被注册的情况
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //密码加密
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        //存入
        save(user);
        return ResponseResult.okResult();

    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getNickName,nickName);
        //查询到记录大于0则账户的昵称已存在
        return count(wrapper)>0;
    }
    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName,userName);
        //查询到记录大于0则账户的账号已存在
        return count(wrapper)>0;
    }
}

