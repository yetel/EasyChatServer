package com.easychat.support;

import lombok.Data;

@Data
public class Session {
    /** 用户唯一性标识*/
    private String userId;
    private String userName;
    private String avatar;

    public Session(String userId, String userName, String avatar) {
        this.userId = userId;
        this.userName = userName;
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return userId + ":" + userName;
    }
}