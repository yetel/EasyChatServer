package com.easychat.handler;

import com.easychat.bean.Group;
import com.easychat.service.GroupService;
import com.easychat.support.Attributes;
import com.easychat.support.Session;
import com.easychat.support.request.AllowGroupReq;
import com.easychat.support.response.AllowGroupResp;
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
 * description: 允许加入群聊处理
 */
@ChannelHandler.Sharable
@Component
@Slf4j
public class AllowGroupReqHandler extends SimpleChannelInboundHandler<AllowGroupReq> {
    @Autowired
    private GroupService groupService;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AllowGroupReq req) throws Exception {
        log.debug("服务器收到允许加入群聊消息， req = {}", req);
        AllowGroupResp resp = new AllowGroupResp();
        resp.setGroupId(req.getGroupId());
        resp.setDateTime(req.getDateTime());
        resp.setAllow(req.isAllow());
        Group group = groupService.getGroup(req.getGroupId());
        Session session = ctx.channel().attr(Attributes.SESSION).get();
        if (req.isAllow() && session.getUserId().equals(group.getMainUserId())) {
            groupService.addmember(group.getGroupId(), req.getUserId());
        }

        Channel channel = SessionUtil.getChannel(req.getUserId());
        channel.writeAndFlush(resp);


    }
}
