package com.sky.controller.admin;


import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.sky.config.OssConfiguration;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用接口")
@Slf4j
public class CommonController {


    @Autowired
    private AliOssUtil aliOssUtil;

    /**
     * 文件上传
      */
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
//        String url = fileService.upload(file);

        log.info("文件上传：{}", file.getOriginalFilename());
        try{
            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String objectName = UUID.randomUUID().toString() + suffix;
            String filePath = aliOssUtil.upload(file.getBytes(), objectName);
            return Result.success(filePath);
        }
        catch (Exception e){
            log.error("文件上传失败", e);

        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
