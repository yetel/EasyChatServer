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
public class LoginResp extends Packet {
    /** userId*/
    private String userId;
    private String userName;
    private boolean success;
    private String token;

    private String reason;
    @Override
    public byte getCommand() {
        return Command.LOGIN_RESP;
    }
}
