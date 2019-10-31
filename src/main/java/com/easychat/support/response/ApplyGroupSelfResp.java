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
public class ApplyGroupSelfResp extends Packet {
    private String groupId;
    /** 不能申请的原因*/
    private String reason;
    /** 发出申请是否成功*/
    private boolean success;
    @Override
    public byte getCommand() {
        return Command.APPLY_GROUP_SELF_RESP;
    }
}
