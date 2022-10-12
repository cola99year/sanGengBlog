package com.cola.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cola.domain.entity.Category;
import org.springframework.stereotype.Repository;


/**
 * 分类表(Category)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-11 14:40:14
 */
@Repository
public interface CategoryMapper extends BaseMapper<Category> {

}

