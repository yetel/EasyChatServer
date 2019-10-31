package com.easychat.handler;

import com.easychat.support.Attributes;
import com.easychat.support.Session;
import com.easychat.support.request.AcceptGroupReq;
import com.easychat.support.response.AcceptGroupResp;
import com.easychat.service.GroupService;
import com.easychat.service.UserService;
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
 * description: 接受群组邀请处理
 */
@ChannelHandler.Sharable
@Component
@Slf4j
public class AcceptGroupReqHandler extends SimpleChannelInboundHandler<AcceptGroupReq> {
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AcceptGroupReq req) throws Exception {
        log.debug("服务器收到接收群组邀请消息， req = {}", req);
        AcceptGroupResp resp = new AcceptGroupResp();
        Session session = ctx.channel().attr(Attributes.SESSION).get();
        resp.setAcceptUser(session.getUserId());
        resp.setSuccess(false);
        resp.setDateTime(req.getDateTime());
        
        //如果同意的话，则添加好友
        if (req.isAccept()) {
            groupService.addmember(req.getGroupId(), session.getUserId());
            resp.setSuccess(true);
        }
        
        //删除邀请记录
        groupService.removeInviteMember(req.getGroupId(), session.getUserId());
        resp.setGroupId(req.getGroupId());
        
        //如果成功则发送通知通知所有的群聊成员
        //失败则通知邀请人
        if (resp.isSuccess()) {
            List<String> members = groupService.getMembers(req.getGroupId());
            for (String member : members) {
                if (member.equals(session.getUserId())) {
                    continue;
                }
                Channel channel = SessionUtil.getChannel(member);
                boolean save = userService.checkChannelAndCahceMessage(member, channel, resp);
                if (!save) {
                    channel.writeAndFlush(resp);
                }
            }
        } else {
            Channel channel = SessionUtil.getChannel(req.getInviterId());
            boolean save = userService.checkChannelAndCahceMessage(req.getInviterId(), channel, resp);
            if (!save) {
                channel.writeAndFlush(resp);
            }
        }
    }
}
