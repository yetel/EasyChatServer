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
public class AddUserReq extends Packet {
    /** 添加的好友 userId*/
    private String addUserId;
    @Override
    public byte getCommand() {
        return Command.ADD_USER_REQ;
    }
}
