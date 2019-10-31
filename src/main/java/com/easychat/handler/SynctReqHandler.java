package com.easychat.handler;

import com.easychat.service.UserService;
import com.easychat.support.Attributes;
import com.easychat.support.Session;
import com.easychat.support.request.SyncMessageReq;
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
 * description: 同步缓存消息处理
 * 当用户不在线的时候， 如果有其他用户给他发送消息，会先缓存在服务器的redis,之后如果用户登录上线了， 可以选择直接登录的时候发送给他缓存信息
 * 也可以发送该消息请求同步缓存消息
 */
@ChannelHandler.Sharable
@Component
@Slf4j
public class SynctReqHandler extends SimpleChannelInboundHandler<SyncMessageReq> {
    @Autowired
    private UserService userService;
    
    @Override
    @Transient
    protected void channelRead0(ChannelHandlerContext ctx, SyncMessageReq msg) throws Exception {
        log.debug("服务器收到同步缓存消息， req = {}", msg);
        Session session = ctx.channel().attr(Attributes.SESSION).get();
        String userId = session.getUserId();
        userService.sendCahceMessage(userId, ctx.channel());
    }
}
