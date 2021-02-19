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
 * @description TODO
 * @date 2021/2/19
 */
@Service
public class OssServiceImpl implements OssService {

    @Override
    public String addFileAvatar(MultipartFile multipartFile) {
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        String accessKeyId = "LTAI4G35aBYNbtoykwQt51ef";
        String accessKeySecret = "ML1FkRzq0Den6FiEqC8OgQnyeLaJx3";
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        InputStream inputStream = null;
        String url = "";
        try {
            inputStream = multipartFile.getInputStream();
            String fileName = multipartFile.getOriginalFilename();
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            fileName = uuid + fileName;
            String datePath = new DateTime().toString("yyyy/MM/dd");
            fileName = datePath + "/" + fileName;
            ossClient.putObject("youngvolunteer", fileName, inputStream);
            ossClient.shutdown();
            url ="https://youngvolunteer.oss-cn-beijing.aliyuncs.com/"+fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }
}
