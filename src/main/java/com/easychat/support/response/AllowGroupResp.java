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
public class AllowGroupResp extends Packet {
    /** 群id*/
    private String groupId;
    /** 是否同意*/
    private boolean allow;
    @Override
    public byte getCommand() {
        return Command.ALLOW_GROUP_RESP;
    }
}
