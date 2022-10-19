package com.cola.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements UserDetails {//实现UserDetails接口

    //导入自己创建的pojo
    private User user;


    //返回权限信息
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    //获取密码
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    //获取用户名
    @Override
    public String getUsername() {
        return user.getUserName();
    }

    //账户是否没过期？
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //账户是否还没锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //是否还没超时？
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //账户是否可用？
    @Override
    public boolean isEnabled() {
        return true;
    }
}
