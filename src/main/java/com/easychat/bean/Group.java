package com.easychat.bean;

import lombok.Data;

/**
 * @author: Zed
 * date: 2019/08/22.
 * description: 群组信息
 */
@Data
public class Group {
    /** 群组id*/
    private String groupId;
    /** 群组名称*/
    private String groupName;
    /** 群主id*/
    private String mainUserId;
    /** 用户图像或者群头像*/
    private String avatar;
    /** 群成员数量*/
    private int memberNum;
}
