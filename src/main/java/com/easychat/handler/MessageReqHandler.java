package com.easychat.handler;

import com.easychat.service.FriendService;
import com.easychat.service.UserService;
import com.easychat.support.Attributes;
import com.easychat.support.Session;
import com.easychat.support.request.MessageReq;
import com.easychat.support.response.MessageResp;
import com.easychat.support.response.MessageSelfResp;
import com.easychat.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Zed
 * date: 2019/08/20.
 * description: 普通消息处理
 */
@ChannelHandler.Sharable
@Component
@Slf4j
public class MessageReqHandler extends SimpleChannelInboundHandler<MessageReq> {
    @Autowired
    private FriendService friendService;
    @Autowired
    private UserService userService;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageReq msg) throws Exception {
        log.debug("服务器收到普通消息， req = {}", msg);
        
        String receiver = msg.getReceiver();
        Session session = ctx.channel().attr(Attributes.SESSION).get();
        if (session == null) {
            return;
        }
        boolean hasFriend = friendService.queryHasFriend(session.getUserId(), receiver);
        if (!hasFriend) {
            MessageSelfResp resp = new MessageSelfResp();
            resp.setSuccess(false);
            resp.setReceiverId(receiver);
            resp.setDateTime(msg.getDateTime());
            resp.setReason("对方不是你的好友，不能发送消息，请先添加好友。");
            ctx.channel().writeAndFlush(resp);
            return;
        }
        MessageResp resp = new MessageResp();
        resp.setSender(session.getUserId());
        resp.setSenderName(session.getUserName());
        resp.setMessage(msg.getMessage());
        resp.setDateTime(msg.getDateTime());
        resp.setMessageType(msg.getMessageType());
        Channel channel = SessionUtil.getChannel(receiver);
        boolean save = userService.checkChannelAndCahceMessage(receiver, channel, resp);
        if (!save) {
            channel.writeAndFlush(resp);
        }
    }
}
