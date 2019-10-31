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
public class AddUserResp extends Packet {
    /** 邀请方id*/
    private String inviterId;
    private String inviterName;
    @Override
    public byte getCommand() {
        return Command.ADD_USER_RESP;
    }
}
