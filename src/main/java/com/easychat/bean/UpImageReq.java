package com.easychat.bean;

import lombok.Data;

/**
 * @author Zed
 * date: 2019/10/16.
 * description:
 */
@Data
public class UpImageReq {
    private String picBase64;
    private String suffix;
}
