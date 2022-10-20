package com.cola.domain.vo;

import java.util.Date;
import java.util.List;

/**
 * @Author: cola99year
 * @Date: 2022/10/20 20:48
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentVo {
    private Long id;
    //文章id
    private Long articleId;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;
    //所回复的目标评论的userid
    private Long toCommentUserId;
    //所回复的用户名
    private String toCommentUserName;
    //评论者名
    private String userName;
    //回复目标评论id
    private Long toCommentId;

    private Long createBy;

    private Date createTime;
    //子评论
    private List<CommentVo> children;
}
