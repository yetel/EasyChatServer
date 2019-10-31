package com.easychat.handler;

import com.easychat.support.Attributes;
import com.easychat.support.Session;
import com.easychat.support.request.AddUserReq;
import com.easychat.support.response.AddUserResp;
import com.easychat.support.response.AddUserSelfResp;
import com.easychat.bean.User;
import com.easychat.service.FriendService;
import com.easychat.service.UserService;
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
 * description: 添加好友处理
 */
@ChannelHandler.Sharable
@Component
@Slf4j
public class AddUserReqHandler extends SimpleChannelInboundHandler<AddUserReq> {
    @Autowired
    private FriendService friendService;
    @Autowired
    private UserService userService;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AddUserReq msg) throws Exception {
        log.debug("服务器收到添加好友消息， req = {}", msg);
        AddUserSelfResp selfResp = new AddUserSelfResp();
        selfResp.setInvitedId(msg.getAddUserId());
        selfResp.setDateTime(msg.getDateTime());
        Session session = ctx.channel().attr(Attributes.SESSION).get();

        User user = userService.queryById(msg.getAddUserId());
        if (user == null) {
            selfResp.setSuccess(false);
            selfResp.setReason("系统不存在该用户，请您确认后再添加");
            ctx.channel().writeAndFlush(selfResp);
            return;
        }

        boolean hasFriend = friendService.queryHasFriend(session.getUserId(), msg.getAddUserId());
        if (hasFriend) {
            selfResp.setSuccess(false);
            selfResp.setReason("对方已经在你的好友列表中了，不需要再添加");
            ctx.channel().writeAndFlush(selfResp);
            return;
        }
        AddUserResp resp = new AddUserResp();
        boolean hasInvite = friendService.queryHasInviteFriend(session.getUserId(), msg.getAddUserId());
        if (!hasInvite) {
            friendService.addInviteFriend(session.getUserId(), msg.getAddUserId());
        }
        resp.setInviterId(session.getUserId());
        resp.setInviterName(session.getUserName());
        resp.setDateTime(msg.getDateTime());
        Channel channel = SessionUtil.getChannel(msg.getAddUserId());
        boolean save = userService.checkChannelAndCahceMessage(msg.getAddUserId(), channel, resp);
        if (!save) {
            channel.writeAndFlush(resp);
        }

        selfResp.setSuccess(true);
        ctx.channel().writeAndFlush(selfResp);
    }
}
