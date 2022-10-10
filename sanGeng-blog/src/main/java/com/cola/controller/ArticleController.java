package com.cola.controller;

import com.cola.domain.ResponseResult;
import com.cola.domain.entity.Article;
import com.cola.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/hotArticle")
    public ResponseResult hotArticle(){
        ResponseResult result = articleService.hotArticle();
        return result;
    }

}
