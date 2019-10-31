package com.easychat.handler;

import com.easychat.code.PacketDecoder;
import com.easychat.code.PacketEncoder;
import com.easychat.code.Spliter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: Zed
 * date: 2019/08/22.
 * description: netty消息链
 */
@Component
public class WorkServerInitializer extends ChannelInitializer<SocketChannel>  {
    @Autowired
    private AcceptGroupReqHandler acceptGroupReqHandler;
    @Autowired
    private AcceptReqHandler acceptReqHandler;
    @Autowired
    private AddUserReqHandler addUserReqHandler;
    @Autowired
    private CreateGroupReqHandler createGroupReqHandler;
    @Autowired
    private GroupMessageReqHandler groupMessageReqHandler;
    @Autowired
    private InviteGroupReqHandler inviteGroupReqHandler;
    @Autowired
    private LoginReqHandler loginReqHandler;
    @Autowired
    private MessageReqHandler messageReqHandler;
    @Autowired
    private RegisterReqHandler registerReqHandler;
    @Autowired
    private UpdatePasswdReqHandler updatePasswdReqHandler;
    @Autowired
    private HeartBeatRequestHandler heartBeatRequestHandler;
    
    @Autowired
    private SynctReqHandler synctReqHandler;
    
    @Autowired
    private ApplyGroupReqHandler applyGroupReqHandler;
    
    @Autowired
    private AllowGroupReqHandler allowGroupReqHandler;
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new Spliter());
        ch.pipeline().addLast(new PacketDecoder());
        ch.pipeline().addLast(new IMIdleStateHandler());
        ch.pipeline().addLast(acceptGroupReqHandler);
        ch.pipeline().addLast(acceptReqHandler);
        ch.pipeline().addLast(addUserReqHandler);
        ch.pipeline().addLast(createGroupReqHandler);
        ch.pipeline().addLast(groupMessageReqHandler);
        ch.pipeline().addLast(inviteGroupReqHandler);
        ch.pipeline().addLast(loginReqHandler);
        ch.pipeline().addLast(messageReqHandler);
        ch.pipeline().addLast(registerReqHandler);
        ch.pipeline().addLast(updatePasswdReqHandler);
        ch.pipeline().addLast(heartBeatRequestHandler);
        ch.pipeline().addLast(synctReqHandler);
        ch.pipeline().addLast(applyGroupReqHandler);
        ch.pipeline().addLast(allowGroupReqHandler);

        ch.pipeline().addLast(new PacketEncoder());
    }
}
