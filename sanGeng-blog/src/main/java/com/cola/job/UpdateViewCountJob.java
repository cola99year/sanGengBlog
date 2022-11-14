package com.cola.job;

import com.cola.domain.entity.Article;
import com.cola.service.ArticleService;
import com.cola.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//定时任务，把redis的浏览量更新到数据库！

@Component
public class UpdateViewCountJob {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private ArticleService articleService;

    //每5秒更新一次！:0/5 * * * * ?
    //每两分钟更新一次：0 0/2 * * * ?
    @Scheduled(cron = "0 0/2 * * * ?")
    public void updateViewCount(){
        //获取redis中的浏览量:id为Long，viewCount为Long
        Map<Object, Object> viewCountMap = redisUtil.hmget("article:viewCount");

        //stream流返回Article，因对应要有相应的构造器！
        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf((String)entry.getKey()), ((Integer) entry.getValue()).longValue()))
                .collect(Collectors.toList());
        //更新到数据库中
        articleService.updateBatchById(articles);

    }
}
