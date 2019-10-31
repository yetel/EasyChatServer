package com.easychat.util;

import com.easychat.support.Attributes;
import com.easychat.support.Session;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Zed
 * date: 2019/08/20.
 * description:
 */
@Component
public class SessionUtil {
    
    private static RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        SessionUtil.redisTemplate = redisTemplate;
    }
    
    public static Map<String, Channel> userChannelMap = new ConcurrentHashMap<>();
    private static final String TOKEN_USER_MAP = "token:user:map"; 
    private static final String USER_TOKEN_MAP = "user:token:map"; 
    
    public static boolean hasLogin(Channel channel) {
       return channel.attr(Attributes.SESSION).get() != null;
    }
    
    public static Channel getChannel(String userId) {
        return userChannelMap.get(userId);
    }
    
    public static void bindSession(Session session, Channel channel) {
        userChannelMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unbindSession(Channel channel) {
        if (hasLogin(channel)) {
            Session session = channel.attr(Attributes.SESSION).get();
            userChannelMap.remove(session.getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }
    
    public static void saveToken(String userId, String token) {
        redisTemplate.opsForHash().put(TOKEN_USER_MAP, token, userId);
        String oldToken = (String) redisTemplate.opsForHash().get(USER_TOKEN_MAP, userId);
        if (oldToken != null) {
            redisTemplate.opsForHash().delete(TOKEN_USER_MAP, oldToken);
        }
        redisTemplate.opsForHash().put(USER_TOKEN_MAP, userId, token);
    }

    public static String getUserIdByToken(String token) {
        return (String) redisTemplate.opsForHash().get(TOKEN_USER_MAP, token);
    }
}
