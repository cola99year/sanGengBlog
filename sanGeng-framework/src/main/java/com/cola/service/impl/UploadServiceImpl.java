package com.cola.service.impl;

import com.cola.domain.ResponseResult;
import com.cola.enums.AppHttpCodeEnum;
import com.cola.exception.SystemException;
import com.cola.service.UploadService;
import com.cola.utils.PathUtils;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @Author: cola99year
 * @Date: 2022/10/22 15:08
 */
@Service
@Data
@ConfigurationProperties(prefix = "oss")
public class UploadServiceImpl implements UploadService {
    //公钥AK
    private String accessKey;
    //密钥SK
    private String secretKey;
    //对象存储空间的名字
    private String bucket;
    //域名
    private String domainName;

    @Override
    public ResponseResult upload(MultipartFile img) {
        //获取原始文件名
        String originalFilename = img.getOriginalFilename();
        //文件类型不符合，抛出异常
        if(!(originalFilename.endsWith(".png") || originalFilename.endsWith(".jpg"))){
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
        //文件类型符合，调用上传方法上传
        String url = uploadOss(img);
        return ResponseResult.okResult(url);
    }

    /**
     * 文件上传到七牛云
     * @param imgFile 要上传的文件
     * @return 返回url文件链接
     */
    private String uploadOss(MultipartFile imgFile){
        //构造一个带指定 Region 对象的配置类:即对象存储在华东地区还是华南地区
        Configuration cfg = new Configuration(Region.autoRegion());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);

        //key即为在七牛云的文件名，默认不指定key的情况下，会以文件内容的hash值作为文件名，
        String key = PathUtils.generateFilePath(imgFile.getOriginalFilename());;
        try {
            //文件转换成输入流
            InputStream inputStream = imgFile.getInputStream();
            //使用密钥和空间了
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                //对流上传了
                Response response = uploadManager.put(inputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                //输出key和hash值
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                return domainName+key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
            //增大IO范围为Exception，捕捉IO流异常
        } catch (Exception ex) {
            //ignore
        }
        return "www....";
    }
}
