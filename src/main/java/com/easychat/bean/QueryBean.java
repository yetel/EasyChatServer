package com.easychat.bean;

import lombok.Data;

/**
 * @author Zed
 * date: 2019/10/22.
 * description: 万能查询结果集
 */
@Data
public class QueryBean {
    /** 用户id 或者群id*/
    private String id;
    /** 用户名称或者群名称*/
    private String name;
    /** 用户 昵称*/
    private String nickName;
    /** 用户图像或者群头像*/
    private String avatar;
    /** 类型 0 用户类型 1 群类型*/
    private int type;
}
