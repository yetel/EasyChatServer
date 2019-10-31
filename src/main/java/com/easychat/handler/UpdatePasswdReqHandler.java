package com.easychat.handler;

import com.easychat.service.UserService;
import com.easychat.support.Attributes;
import com.easychat.support.Session;
import com.easychat.support.request.UpdatePasswdReq;
import com.easychat.support.response.UpdatePasswdResp;
import com.easychat.bean.User;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Zed
 * date: 2019/08/20.
 * description: 建议用户http修改密码
 */
@ChannelHandler.Sharable
@Component
@Deprecated
public class UpdatePasswdReqHandler extends SimpleChannelInboundHandler<UpdatePasswdReq> {
    @Autowired
    private UserService userService;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, UpdatePasswdReq msg) throws Exception {
        Session session = ctx.channel().attr(Attributes.SESSION).get();
        User user = userService.queryById(session.getUserId());
        UpdatePasswdResp resp = new UpdatePasswdResp();
        resp.setPassword(msg.getNewPassword());
        resp.setDateTime(msg.getDateTime());
        if (user.getPassword().equals(msg.getOldPassword())) {
            user.setPassword(msg.getNewPassword());
            user.setUserName(null);
            userService.updateBySelective(user);
            resp.setSuccess(true);
        } else {
            resp.setSuccess(false);
            resp.setReason("修改的密码跟旧密码一致，请重新修改！");
        }
        ctx.channel().writeAndFlush(resp);
    }
}
