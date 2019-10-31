package com.easychat.handler;

import com.easychat.service.GroupService;
import com.easychat.service.UserService;
import com.easychat.support.Attributes;
import com.easychat.support.Session;
import com.easychat.support.request.CreateGroupReq;
import com.easychat.support.response.CreateGroupResp;
import com.easychat.support.response.InviteGroupResp;
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
 * description: 创建群聊处理
 */
@ChannelHandler.Sharable
@Component
@Slf4j
public class CreateGroupReqHandler extends SimpleChannelInboundHandler<CreateGroupReq> {
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupReq req) throws Exception {
        log.debug("服务器收到创建群聊消息， req = {}", req);
        CreateGroupResp resp = new CreateGroupResp();
        Session session = ctx.channel().attr(Attributes.SESSION).get();
        String userId = session.getUserId();
        String groupId = groupService.addGroup(req.getGroupName(), userId);
        String groupName = req.getGroupName();
        resp.setGroupId(groupId);
        resp.setGroupName(groupName);
        resp.setSuccess(true);
        resp.setDateTime(req.getDateTime());
        groupService.addmember(groupId, userId);
        ctx.channel().writeAndFlush(resp);

        
        if (req.getUsers() == null || req.getUsers().size() == 0) {
            return;
        }
        
        for (String inviter : req.getUsers()) {
            InviteGroupResp resp1 = new InviteGroupResp();
            resp1.setGroupId(groupId);
            resp1.setGroupName(groupName);
            resp1.setInviteId(userId);
            resp1.setDateTime(req.getDateTime());
            boolean hasInviteMember = groupService.queryHasInviteMember(groupId, inviter);
            if (!hasInviteMember) {
                groupService.addInviteMember(groupId, inviter);
            }
            Channel channel = SessionUtil.getChannel(inviter);
            boolean save = userService.checkChannelAndCahceMessage(inviter, channel, resp1);
            if (!save) {
                channel.writeAndFlush(resp1);
            }
            
        }
    }
}
