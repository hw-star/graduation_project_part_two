package cn.simbrain.controller;

import cn.simbrain.service.OssService;
import cn.simbrain.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author huowei
 * @version 1.0.0
 * @description TODO
 * @date 2021/2/19
 */
@RestController
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping("/fileoss")
    public Result addOssFile(MultipartFile multipartFile){
        String url = ossService.addFileAvatar(multipartFile);
        return Result.success(url);
    }
}
