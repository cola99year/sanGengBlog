package com.cola.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cola.domain.ResponseResult;
import com.cola.domain.entity.Article;
import com.cola.mapper.ArticleMapper;
import com.cola.service.ArticleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: cola99year
 * @Date: 2022/10/10 21:54
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public ResponseResult hotArticle() {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        //文章不能是草稿    //浏览量来降序排序
        wrapper.eq(Article::getStatus,0).orderByDesc(Article::getViewCount);
        //分页展示：第一页的前10条
        Page page = new Page(1,10);
        page(page,wrapper);
        List<Article> articleList = page.getRecords();
        return ResponseResult.okResult(articleList);

    }
}
