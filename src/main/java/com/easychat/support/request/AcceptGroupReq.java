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
public class AcceptGroupReq extends Packet {
    /** 群id*/
    private String groupId;
    /** 邀请人*/
    private String inviterId;
    /** 是否接受邀请*/
    private boolean accept;
    @Override
    public byte getCommand() {
        return Command.ACCEPT_GROUP_REQ;
    }
}
