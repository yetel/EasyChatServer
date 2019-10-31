package com.easychat.handler;

import com.easychat.bean.Group;
import com.easychat.service.GroupService;
import com.easychat.service.UserService;
import com.easychat.support.Attributes;
import com.easychat.support.Session;
import com.easychat.support.request.InviteGroupReq;
import com.easychat.support.response.InviteGroupResp;
import com.easychat.support.response.InviteGroupSelfResp;
import com.easychat.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Zed
 * date: 2019/08/20.
 * description: 邀请好友入群处理
 */
@ChannelHandler.Sharable
@Component
@Slf4j
public class InviteGroupReqHandler extends SimpleChannelInboundHandler<InviteGroupReq> {
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, InviteGroupReq req) throws Exception {
        log.debug("服务器收到邀请好友入群消息， req = {}", req);
        
        String groupId = req.getGroupId();
        Group group = groupService.getGroup(groupId);
        Session session = ctx.channel().attr(Attributes.SESSION).get();
        String userId = session.getUserId();
        InviteGroupSelfResp resp = new InviteGroupSelfResp();
        resp.setGroupId(groupId);
        resp.setGroupName(group.getGroupName());
        resp.setDateTime(req.getDateTime());
        List<String> successUsers = new ArrayList<>();
        List<String> failedUsers = new ArrayList<>();
        for (String inviter : req.getUsers()) {
            boolean isMember = groupService.queryHasmember(groupId, inviter);
            if (isMember) {
                failedUsers.add(inviter);
                continue;
            }
            successUsers.add(inviter);
            InviteGroupResp resp1 = new InviteGroupResp();
            resp1.setGroupId(groupId);
            resp1.setGroupName(group.getGroupName());
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
        resp.setFailedUser(failedUsers);
        resp.setSuccessUser(successUsers);
        ctx.channel().writeAndFlush(resp);
    }
}
