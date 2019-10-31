package com.easychat.handler;

import com.easychat.service.FriendService;
import com.easychat.service.UserService;
import com.easychat.support.Attributes;
import com.easychat.support.Session;
import com.easychat.support.request.AcceptReq;
import com.easychat.support.response.AcceptResp;
import com.easychat.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.beans.Transient;

/**
 * @author Zed
 * date: 2019/08/20.
 * description:接受好友邀请处理
 */
@ChannelHandler.Sharable
@Component
@Slf4j
public class AcceptReqHandler extends SimpleChannelInboundHandler<AcceptReq> {
    @Autowired
    private FriendService friendService;
    @Autowired
    private UserService userService;
    @Override
    @Transient
    protected void channelRead0(ChannelHandlerContext ctx, AcceptReq msg) throws Exception {
        log.debug("服务器收到接受好友邀请消息， req = {}", msg);
        AcceptResp resp = new AcceptResp();
        Session session = ctx.channel().attr(Attributes.SESSION).get();
        resp.setInvitedId(session.getUserId());
        resp.setInvitedName(session.getUserName());
        resp.setInviterId(msg.getReceiver());
        resp.setSuccess(msg.isAccept());
        resp.setDateTime(msg.getDateTime());
        
        //删除邀请的信息
        friendService.removeInviteFriend(resp.getInviterId(), resp.getInvitedId());
        if (resp.isSuccess()) {
            //新增双向好友的
            friendService.addFriend(resp.getInviterId(), resp.getInvitedId());
            friendService.addFriend(resp.getInvitedId(), resp.getInviterId());
        }

        Channel channel = SessionUtil.getChannel(resp.getInviterId());
        boolean save = userService.checkChannelAndCahceMessage(resp.getInviterId(), channel, resp);
        if (!save) {
            channel.writeAndFlush(resp);
        }
    }
}
