package com.easychat.handler;

import com.easychat.service.GroupService;
import com.easychat.service.UserService;
import com.easychat.support.Attributes;
import com.easychat.support.Session;
import com.easychat.support.request.GroupMessageReq;
import com.easychat.support.response.GroupMessageResp;
import com.easychat.support.response.MessageSelfResp;
import com.easychat.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Zed
 * date: 2019/08/20.
 * description: 群消息处理
 */
@ChannelHandler.Sharable
@Component
@Slf4j
public class GroupMessageReqHandler extends SimpleChannelInboundHandler<GroupMessageReq> {
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMessageReq msg) throws Exception {
        log.debug("服务器收到群消息， req = {}", msg);
        
        String groupId = msg.getGroupId();
        Session session = ctx.channel().attr(Attributes.SESSION).get();
        boolean hasmember = groupService.queryHasmember(groupId, session.getUserId());
        if (!hasmember) {
            MessageSelfResp resp = new MessageSelfResp();
            resp.setGroupId(groupId);
            resp.setDateTime(msg.getDateTime());
            resp.setSuccess(false);
            resp.setReason("您不是该群的成员，不能发送消息到该群！");
            ctx.channel().writeAndFlush(resp);
            return;
        }

        List<String> members = groupService.getMembers(groupId);
        String message = msg.getMessage();
        String userId = session.getUserId();
        for (String member : members) {
            if (member.equals(userId)) {
                continue;
            }
            GroupMessageResp resp = new GroupMessageResp();
            resp.setGroupId(groupId);
            resp.setMessage(message);
            resp.setSenderId(userId);
            resp.setDateTime(msg.getDateTime());
            resp.setSenderName(session.getUserName());
            resp.setMessageType(msg.getMessageType());
            resp.setAvatar(session.getAvatar());
            Channel channel = SessionUtil.getChannel(member);
            boolean save = userService.checkChannelAndCahceMessage(member, channel, resp);
            if (!save) {
                channel.writeAndFlush(resp);
            }
        }
        
        


    }
}
