package com.cola.service;

import com.cola.domain.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: cola99year
 * @Date: 2022/10/22 15:07
 */
public interface UploadService {
    ResponseResult upload(MultipartFile img);
}
