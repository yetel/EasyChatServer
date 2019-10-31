package com.easychat.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * @author: Zed
 * date: 2019/08/22.
 * description:
 */
@Data
public class User {
    private String userId;
    private String userName;
    @JsonIgnore
    private String password;
    private String nickName;
    private String avatar;
    private String signature;
    private String remark;
}
