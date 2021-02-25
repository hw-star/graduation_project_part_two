package cn.simbrain.controller;

import cn.simbrain.service.OssService;
import cn.simbrain.util.Result;
import cn.simbrain.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author huowei
 * @version 1.0.0
 * @description TODO
 * @date 2021/2/19
 */
@RestController
@RequestMapping("/oss/fileoss")
public class OssController {

    @Autowired
    private OssService ossService;

    @PostMapping
    public Result addOssFile(MultipartFile file){
        if (file == null)
            return Result.failure(ResultCode.SYSTEM_INNER_ERROR);
        String url = ossService.addFileAvatar(file);
        return Result.success(url);
    }
}
