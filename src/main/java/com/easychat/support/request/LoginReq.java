package com.easychat.support.request;

import com.easychat.support.Command;
import com.easychat.support.Packet;
import lombok.Data;

/**
 * @author Zed
 * date: 2019/08/19.
 * description:
 */
@Data
public class LoginReq extends Packet {
    /** 账号*/
    private String userName;
    /** 密码*/
    private String password;
    
    /** 是否登录后直接发送缓存消息*/
    private boolean sendCacheMessage = true;
    
    private String token;
    @Override
    public byte getCommand() {
        return Command.LOGIN_RRQ;
    }
}
