package com.barlala.forum.controller;

import com.barlala.forum.service.ResultJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Barlala
 * @version 1.0
 * @date 2020/6/26 下午9:53
 */
@Controller
public class FileController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static byte[] fileToByte(File img) throws Exception {
        byte[] bytes = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            BufferedImage bi;
            bi = ImageIO.read(img);
            ImageIO.write(bi, "png", baos);
            bytes = baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            baos.close();
        }
        return bytes;
    }

    @PostMapping(value = "/uploadImage")
    @ResponseBody
    public ResultJson<?> uploadImage(@RequestParam(value = "file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResultJson<>(HttpStatus.BAD_REQUEST, "上传失败");
        }
        String fileName = file.getOriginalFilename();
        String filePath = "files/";
        File dest = new File(new File(filePath).getAbsolutePath() + "/" + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
            logger.info("上传成功" + dest.getName());
            return new ResultJson<>(200, "上传成功", "http://127.0.0.1:8118/image/" + dest.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResultJson<>(HttpStatus.BAD_REQUEST, "上传失败");
    }

    @RequestMapping(value = "/image/{imageName}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_PNG_VALUE})
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
