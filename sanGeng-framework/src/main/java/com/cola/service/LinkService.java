package com.cola.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cola.domain.ResponseResult;
import com.cola.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2022-10-17 11:24:20
 */
public interface LinkService extends IService<Link> {

    ResponseResult getALlLink();
}

