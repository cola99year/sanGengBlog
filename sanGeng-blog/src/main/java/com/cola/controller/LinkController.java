package com.cola.controller;

import com.cola.domain.ResponseResult;
import com.cola.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: cola99year
 * @Date: 2022/10/17 11:26
 */
@RestController
@RequestMapping("/link")
public class LinkController {
    @Autowired
    private LinkService linkService;
    @GetMapping("/getAllLink")
    public ResponseResult getALlLink(){
        return linkService.getALlLink();
    }
}
