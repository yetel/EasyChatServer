package com.easychat.handler;

import com.easychat.bean.User;
import com.easychat.service.UserService;
import com.easychat.support.Session;
import com.easychat.support.request.RegisterReq;
import com.easychat.support.response.RegisterResp;
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
 * description: 注册处理
 */
@ChannelHandler.Sharable
@Component
@Slf4j
public class RegisterReqHandler extends SimpleChannelInboundHandler<RegisterReq> {
    @Autowired
    private UserService userService;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RegisterReq msg) {
        log.debug("服务器收到注册消息， req = {}", msg);
        
        RegisterResp resp = new RegisterResp();
        resp.setSuccess(true);
        resp.setDateTime(msg.getDateTime());
        //查询数据库是否存在相同userName的用户，否则不让注册
        try {
            userService.addUser(msg.getUserName(), msg.getPassword(), msg.getNickName());
            if (msg.isLogin()) {
                resp.setUserName(msg.getUserName());
                User user = userService.queryByName(msg.getUserName());
                resp.setUserId(user.getUserId());
                String uUid = getToken();
                resp.setToken(uUid);
                SessionUtil.saveToken(user.getUserId(), uUid);
                SessionUtil.bindSession(new Session(user.getUserId(), msg.getUserName(), user.getAvatar()), ctx.channel());
            }
        } catch (Exception e) {
            resp.setSuccess(false);
            resp.setReason(e.getMessage());
        }
        
        ctx.channel().writeAndFlush(resp);
        
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
