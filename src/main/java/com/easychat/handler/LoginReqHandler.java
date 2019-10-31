package com.easychat.handler;

import com.easychat.bean.User;
import com.easychat.service.UserService;
import com.easychat.support.Session;
import com.easychat.support.request.LoginReq;
import com.easychat.support.response.LoginResp;
import com.easychat.util.RandomIdUtil;
import com.easychat.util.SessionUtil;
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
 * description: 登录处理
 */
@ChannelHandler.Sharable
@Component
@Slf4j
public class LoginReqHandler extends SimpleChannelInboundHandler<LoginReq> {
    @Autowired
    private UserService userService;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginReq msg) throws Exception {
        log.debug("服务器收到登录消息， req = {}", msg);
        
        LoginResp loginResp = new LoginResp();
        User user;
        if (StringUtils.isNotBlank(msg.getToken())) {
            String userId = SessionUtil.getUserIdByToken(msg.getToken());
            user = userService.queryById(userId);
            if (user == null) {
                loginResp.setSuccess(false);
                loginResp.setReason("token 错误 或者 token过期！");
                ctx.channel().writeAndFlush(loginResp);
                return;
            }
        } else {
            if (msg.getUserName() == null || msg.getPassword() == null) {
                loginResp.setSuccess(false);
                loginResp.setReason("用户名或者密码为空！");
                ctx.channel().writeAndFlush(loginResp);
                return;
            }

            user = userService.queryByName(msg.getUserName());
            if (user == null || !user.getPassword().equals(msg.getPassword())) {
                loginResp.setSuccess(false);
                loginResp.setReason("用户名或者密码错误！");
                ctx.channel().writeAndFlush(loginResp);
                return;
            }
        }
        
        loginResp.setUserName(user.getUserName());
        loginResp.setDateTime(msg.getDateTime());
        
        loginResp.setSuccess(true);
        loginResp.setUserId(user.getUserId());
        String uUid = getToken();
        loginResp.setToken(uUid);
        SessionUtil.saveToken(user.getUserId(), uUid);
        SessionUtil.bindSession(new Session(user.getUserId(), msg.getUserName(), user.getAvatar()), ctx.channel());

        ctx.channel().writeAndFlush(loginResp);
        
        if (msg.isSendCacheMessage()) {
            userService.sendCahceMessage(user.getUserId(), ctx.channel());
        }
    }

    private String getToken() {
        String token = RandomIdUtil.generateUniqId();
        String userId = SessionUtil.getUserIdByToken(token);
        if (StringUtils.isBlank(userId)) {
            return token;
        }
        return getToken();
    }
}
