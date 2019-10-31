package com.easychat.support.request;

import com.easychat.support.Command;
import com.easychat.support.Packet;
import lombok.Data;

import java.util.List;

/**
 * @author Zed
 * date: 2019/08/19.
 * description:
 */
@Data
public class InviteGroupReq extends Packet {
    /** 群Id*/
    private String groupId;
    /** 邀请的好友*/
    private List<String> users;
    @Override
    public byte getCommand() {
        return Command.INVITE_GROUP_REQ;
    }
}
