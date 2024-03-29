package cn.simbrain.service.impl;

import cn.simbrain.service.OssService;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * @author huowei
 * @version 1.0.0
 * @description OSS储存实现层
 * @date 2021/2/19
 */
@Service
public class OssServiceImpl implements OssService {

    /**
     * @description: 上传图片并返回图片地址
     * @Param multipartFile: 头像文件
     * @return: java.lang.String
     */
    @Override
    public String addFileAvatar(MultipartFile multipartFile) {
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        String accessKeyId = "LTAI4G35aBYNbtoykwQt51ef";
        String accessKeySecret = "ML1FkRzq0Den6FiEqC8OgQnyeLaJx3";
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        InputStream inputStream = null;
        String url = "";
        try {
            String fileName = multipartFile.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            fileName = uuid + fileName;
            String dataPath = new DateTime().toString("yyyy/MM/dd");
            fileName = dataPath + "/" + fileName;
            inputStream = multipartFile.getInputStream();
            ossClient.putObject("youngvolunteer", fileName, inputStream);
            ossClient.shutdown();
            url = "https://youngvolunteer.oss-cn-beijing.aliyuncs.com"+ "/" + fileName;
        } catch (Exception e) {

        }
        return url;
    }
}
