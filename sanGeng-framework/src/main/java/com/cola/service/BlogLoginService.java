package com.cola.service;

import com.cola.domain.ResponseResult;
import com.cola.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
