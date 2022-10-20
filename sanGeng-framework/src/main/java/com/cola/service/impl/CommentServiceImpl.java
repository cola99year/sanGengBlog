package com.cola.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cola.constants.SystemConstants;
import com.cola.domain.ResponseResult;
import com.cola.domain.entity.Comment;
import com.cola.domain.vo.CommentVo;
import com.cola.domain.vo.PageVo;
import com.cola.mapper.CommentMapper;
import com.cola.service.CommentService;
import com.cola.service.UserService;
import com.cola.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2022-10-20 19:58:13
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private UserService userService;
    @Override
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        //查哪一篇文章的评论？
        queryWrapper.eq(Comment::getId,articleId);
        //先查询是根评论的
        queryWrapper.eq(Comment::getRootId, SystemConstants.COMMENT_ISROOT);
        //分页查询
        Page page = new Page(pageNum,pageSize);
        //Page<Comment> page = new Page(pageNum,pageSize);
        page(page,queryWrapper);
        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());
        PageVo pageVo = new PageVo(commentVoList, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    private List<CommentVo> toCommentVoList(List<Comment> commentList){
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(commentList, CommentVo.class);
        for (CommentVo commentVo:commentVos) {
            //获取并设置评论者网名
            commentVo.setUserName(userService.getById(commentVo.getCreateBy()).getNickName());
            //若toCommentUserId不为-1才进行查询，获取并设置用户所回复的评论者的网名,因为父评论没有所回复的人
            if(commentVo.getToCommentId()!=-1){
                commentVo.setToCommentUserName(userService.getById(commentVo.getToCommentUserId()).getNickName());
            }
        }
        return commentVos;
    }
}

