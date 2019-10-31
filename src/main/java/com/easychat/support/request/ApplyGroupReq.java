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
public class ApplyGroupReq extends Packet {
    /** 群Id*/
    private String groupId;
    @Override
    public byte getCommand() {
        return Command.APPLY_GROUP_REQ;
    }
}
