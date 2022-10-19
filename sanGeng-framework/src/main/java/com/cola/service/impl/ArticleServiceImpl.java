package com.cola.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cola.constants.SystemConstants;
import com.cola.domain.ResponseResult;
import com.cola.domain.entity.Article;
import com.cola.domain.vo.ArticleDetailVo;
import com.cola.domain.vo.ArticleListVo;
import com.cola.domain.vo.HotArticleVo;
import com.cola.domain.vo.PageVo;
import com.cola.mapper.ArticleMapper;
import com.cola.service.ArticleService;
import com.cola.service.CategoryService;
import com.cola.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @Author: cola99year
 * @Date: 2022/10/10 21:54
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Override
    public ResponseResult hotArticle() {
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        //文章不能是草稿    //浏览量来降序排序
        wrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL).orderByDesc(Article::getViewCount);
        //分页展示：第一页的前10条
        Page page = new Page(1,10);
        page(page,wrapper);
        List<Article> articleList = page.getRecords();

        //利用Java反射，我根本不需要自己创建List对象！
        // ArrayList<HotArticleVo> hotArticleList = new ArrayList<>();
        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(articleList, HotArticleVo.class);
        return ResponseResult.okResult(hotArticleVos);

    }

    //分页查询文章，还可根据分类来查询并分页！
    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        LambdaQueryWrapper<Article> pageWrapper = new LambdaQueryWrapper<>();
        // 《condition的使用》如果有categoryId 就要查询时要和传入的相同
        pageWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0 ,Article::getCategoryId,categoryId);
        //正式文章+按指定降序排序
        pageWrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL)
                .orderByDesc(Article::getIsTop);
        Page<Article> page = new Page<>(pageNum,pageSize);
        page(page,pageWrapper);
        List<Article> pageRecords = page.getRecords();
        //给查出来的文章赋予分类名称！
        List<Article> articleList = pageRecords.stream()
                .map(article -> {
                    article.setCategoryName(categoryService.getById(article.getCategoryId()).getName());
                    return article;
                }).collect(Collectors.toList());

        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articleList, ArticleListVo.class);
        //封装成pageVo对象
        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        Article article = getById(id);
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        articleDetailVo.setCategoryName(categoryService.getById(articleDetailVo.getCategoryId()).getName());
        return ResponseResult.okResult(articleDetailVo);
    }
}
