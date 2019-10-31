package com.easychat.controller;

import com.easychat.bean.Result;
import com.easychat.bean.UpImageReq;
import com.easychat.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("image")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @PostMapping("upload")
    public Result<String> uploadImage(@RequestBody UpImageReq imageReqx) {
        return Result.success(imageService.uploadImage(imageReqx.getPicBase64(), imageReqx.getSuffix()));
    }

    @GetMapping("download/{imageId}")
    public void download(@PathVariable("imageId") String imageId, HttpServletResponse response) {
        imageService.download(imageId, response);
    }
}
