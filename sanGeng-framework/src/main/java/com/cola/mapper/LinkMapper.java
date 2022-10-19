package com.cola.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cola.domain.entity.Link;
import org.springframework.stereotype.Repository;


/**
 * 友链(Link)表数据库访问层
 *
 * @author makejava
 * @since 2022-10-17 11:24:17
 */
@Repository
public interface LinkMapper extends BaseMapper<Link> {

}

