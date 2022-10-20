package com.cola.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cola.domain.ResponseResult;
import com.cola.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2022-10-20 19:58:13
 */
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize);
}

