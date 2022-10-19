package com.cola.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailVo {
    private Long id;
    /**
     * 标题
     */
    private String title;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 所属分类id
     */
    private Long categoryId;
    /*
     * 分类名称，注解表明他在文章表中不存在该字段！
     */
    @TableField(exist = false)
    private String categoryName;
    /**
     * 访问量
     */
    private Long viewCount;
    /**
     * 是否允许评论 1是，0否
     */
    private String isComment;

    private Date createTime;

}
