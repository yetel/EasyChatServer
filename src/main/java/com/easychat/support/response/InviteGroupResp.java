package com.easychat.support.response;

import com.easychat.support.Command;
import com.easychat.support.Packet;
import lombok.Data;

/**
 * @author Zed
 * date: 2019/08/19.
 * description: 服务端发送给被邀请方的响应
 */
@Data
public class InviteGroupResp extends Packet {
    /** 群名*/
    private String groupName;
    /** 群Id*/
    private String groupId;
    /** 邀请方id*/
    private String inviteId;
    @Override
    public byte getCommand() {
        return Command.INVITE_GROUP_RESP;
    }
}
