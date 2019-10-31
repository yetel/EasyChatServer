package com.easychat.handler;

import com.easychat.bean.Group;
import com.easychat.service.GroupService;
import com.easychat.support.Attributes;
import com.easychat.support.Session;
import com.easychat.support.request.ApplyGroupReq;
import com.easychat.support.response.ApplyGroupResp;
import com.easychat.support.response.ApplyGroupSelfResp;
import com.easychat.util.SessionUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Zed
 * date: 2019/08/20.
 * description: 申请加入群聊处理
 */
@ChannelHandler.Sharable
@Component
@Slf4j
public class ApplyGroupReqHandler extends SimpleChannelInboundHandler<ApplyGroupReq> {
    @Autowired
    private GroupService groupService;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ApplyGroupReq req) throws Exception {
        log.debug("服务器收到申请加入群聊消息， req = {}", req);
        ApplyGroupResp resp = new ApplyGroupResp();
        Session session = ctx.channel().attr(Attributes.SESSION).get();
        Group group = groupService.getGroup(req.getGroupId());
        if (group == null || StringUtils.isBlank(group.getMainUserId())) {
            ApplyGroupSelfResp resp1 = new ApplyGroupSelfResp();
            resp1.setGroupId(req.getGroupId());
            resp1.setDateTime(req.getDateTime());
            resp1.setSuccess(false);
            resp1.setReason("要添加的群不存在，或者要添加的群异常！");
            ctx.channel().writeAndFlush(resp1);
            return;
        }
        String userId = session.getUserId();
        resp.setGroupId(req.getGroupId());
        resp.setGroupName(group.getGroupName());
        resp.setDateTime(req.getDateTime());
        resp.setApplyUserId(userId);
        resp.setApplyUserName(session.getUserName());
        String mainUserId = group.getMainUserId();
        Channel channel = SessionUtil.getChannel(mainUserId);
        channel.writeAndFlush(resp);
    }
}
