package com.tzs.antique.common.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * 处理图片 加 人脸识别框
 *
 * @author YHS
 */
@RestController
@RequestMapping("/image")
@Slf4j
public class ImgController extends BaseController {

    @Value("${file.localPath}")
    private String localPath;

    @GetMapping("/{dateStr}/{imgPath}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable String imgPath, @PathVariable String dateStr) {
        try {
            // 构建文件路径
            Path imagePath = Paths.get(localPath, imgPath).normalize();
            File imageFile = new File(imagePath.toString());
            if (!imageFile.exists()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            // 获取文件的 MIME 类型
            String contentType = Files.probeContentType(imageFile.toPath());
            if (contentType == null) {
                contentType = MediaType.IMAGE_JPEG_VALUE; // 默认为 JPEG
            }
            // 创建 InputStreamResource 并设置响应头
            InputStreamResource resource = new InputStreamResource(new FileInputStream(imageFile));
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + imageFile.getName());
            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
