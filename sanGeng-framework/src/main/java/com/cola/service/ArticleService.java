package com.cola.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cola.domain.ResponseResult;
import com.cola.domain.entity.Article;

/**
 * @Author: cola99year
 * @Date: 2022/10/10 21:53
 */
public interface ArticleService extends IService<Article> {
     ResponseResult hotArticle();

     ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

     ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);
}
