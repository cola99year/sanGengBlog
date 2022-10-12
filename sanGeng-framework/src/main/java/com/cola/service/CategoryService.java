package com.cola.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cola.domain.ResponseResult;
import com.cola.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2022-10-11 14:40:15
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}

