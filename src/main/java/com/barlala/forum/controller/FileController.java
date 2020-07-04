package com.barlala.forum.controller;

import com.barlala.forum.service.Result;
import com.barlala.forum.service.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author Barlala
 * @version 1.0
 * @date 2020/6/26 下午9:53
 */
@Controller
public class FileController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @PostMapping(value = "/api/uploadImage")
    @ResponseBody
    public Result<?> uploadImage(@RequestParam(value = "file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResultUtil.error("上传失败");
        }
        String fileName = UUID.randomUUID().toString();
        String filePath = "files/";
        File dest = new File(new File(filePath).getAbsolutePath() + "/" + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            logger.info("上传成功" + dest.getName());
            return ResultUtil.success("/api/image/" + dest.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultUtil.error("上传失败");
    }

    @RequestMapping(value = "/api/image/{imageName}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    public byte[] getImage(@PathVariable("imageName") String imageName) {
        String path = "files/";
        File file = new File(new File(path).getAbsolutePath() + "/" + imageName);
        logger.info(file.getAbsolutePath());
        try {
            FileInputStream inputStream = new FileInputStream(file);
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, inputStream.available());
            return bytes;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
}
