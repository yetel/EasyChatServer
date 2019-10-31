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
public class AcceptResp extends Packet {
    /** 邀请方*/
    private String inviterId;
    /** 被邀请方*/
    private String invitedId;
    private String invitedName;
    /** 是否同意*/
    private boolean success;
    @Override
    public byte getCommand() {
        return Command.ACCEPT_RESP;
    }
}
