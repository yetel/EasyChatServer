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
public class CreateGroupResp extends Packet {
    /** 创建的群名*/
    private String groupName;
    /** 新增的群id*/
    private String groupId;
    /** 是否创建群成功*/
    private boolean success;
    /** 创建失败的原因*/
    private String reason;
    @Override
    public byte getCommand() {
        return Command.CREATE_GROUP_RESP;
    }
}
