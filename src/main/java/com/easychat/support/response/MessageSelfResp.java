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
public class MessageSelfResp extends Packet {
    private String receiverId;
    private String groupId;
    private boolean success;
    private String reason;
    @Override
    public byte getCommand() {
        return Command.MESSAGE_SELF_RESP;
    }
}
