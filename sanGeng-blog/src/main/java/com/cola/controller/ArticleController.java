package com.cola.controller;

import com.cola.domain.ResponseResult;
import com.cola.domain.entity.Article;
import com.cola.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: cola99year
 * @Date: 2022/10/10 21:58
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/list")
    public List<Article> articleList(){
        return articleService.list();
    }

    @GetMapping("/hotArticleList")
    public ResponseResult hotArticle(){
        ResponseResult result = articleService.hotArticle();
        return result;
    }

    /**
     * 分页查询
     * @param pageNum 第几页
     * @param pageSize 页大小
     * @param categoryId 是否按分类查询
     * @return
     */
    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    /**
     * 文章详情
     * @param id 哪一篇文章
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }

}
