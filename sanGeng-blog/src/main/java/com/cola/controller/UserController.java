package com.cola.controller;

import com.cola.domain.ResponseResult;
import com.cola.domain.entity.User;
import com.cola.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: cola99year
 * @Date: 2022/10/22 13:21
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 个人中心——查询用户信息
     * @return
     */
    @GetMapping("/userInfo")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }

    /**
     * 更新 个人信息
     * @param user  接收的前端信息
     * @return
     */
    @PutMapping("/userInfo")
    public ResponseResult updateUserInfo(@RequestBody User user){
        return userService.updateUserInfo(user);
    }

    /**
     * 用户注册
     * @param user 收到的请求体信息
     * @return
     */
    @PostMapping("/register")
    public ResponseResult register(@RequestBody User user){
        return userService.register(user);
    }

}
