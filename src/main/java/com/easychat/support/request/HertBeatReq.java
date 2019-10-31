package com.easychat.support.request;

import com.easychat.support.Command;
import com.easychat.support.Packet;

public class HertBeatReq extends Packet {
    @Override
    public byte getCommand() {
        return Command.HEAT_BEAT_REQ;
    }
}
