package com.easychat.service;


import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;


public interface ImageService {
    String uploadImage(String picBase64, String suffix);

    void download(String imageId, HttpServletResponse response);
}
