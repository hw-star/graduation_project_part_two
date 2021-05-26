package cn.simbrain.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author huowei
 * @version 1.0.0
 * @description OSS存储服务层
 * @date 2021/2/19
 */
public interface OssService{

    String addFileAvatar(MultipartFile multipartFile);
}
