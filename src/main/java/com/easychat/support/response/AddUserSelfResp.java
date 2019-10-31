package com.easychat.support.response;

import com.easychat.support.Command;
import com.easychat.support.Packet;
import lombok.Data;

/**
 * @author Zed
 * date: 2019/08/19.
 * description:
 */
@Data
public class AddUserSelfResp extends Packet {
    /** 被邀请方id*/
    private String invitedId;
    /** 不能添加的原因*/
    private String reason;
    /** 发出邀请是否成功*/
    private boolean success;
    @Override
    public byte getCommand() {
        return Command.ADD_USER_SELF_RESP;
    }
}
