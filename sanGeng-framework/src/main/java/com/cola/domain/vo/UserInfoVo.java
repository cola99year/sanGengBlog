package com.cola.domain.vo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
//生成setter方法返回this（也就是返回的是对象），代替了默认的返回void。
@Accessors(chain = true)
public class UserInfoVo {
    /**
     * 主键
     */
    private Long id;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 头像
     */
    private String avatar;

    private String sex;

    private String email;


}
