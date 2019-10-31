package com.easychat.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.easychat.bean.ResultCode;
import com.easychat.bean.User;
import com.easychat.mapper.UserMapper;
import com.easychat.service.BaseService;
import com.easychat.service.ImageService;
import com.easychat.service.UserService;
import com.easychat.support.Packet;
import com.easychat.util.SessionUtil;
import com.easychat.util.RandomIdUtil;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * @author: Zed
 * date: 2019/08/22.
 * description:
 */
@Service
public class UserServiceImpl extends BaseService implements UserService {
    
    private static final String REDIS_MESSAGE_CACHE = "redis_message_cache";
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Resource
    private UserMapper userMapper;

    @Autowired
    private ImageService imageService;
    @Override
    public void addUser(String userName, String password, String nickName) throws Exception {
        User user = userMapper.queryByName(userName);
        if (user != null) {
            throw new Exception("已存在该名称用户");
        }
        User newUser = new User();
        newUser.setUserName(userName);
        newUser.setPassword(password);
        newUser.setNickName(nickName);
        String uUid = generateId();
        newUser.setUserId(uUid);
        int success = userMapper.addUser(newUser);
        if (success == 0) {
            throw new Exception("已存在该名称用户");
        }
    }

    private String generateId() {
        String userId = RandomIdUtil.generateUniqId();
        User user = userMapper.queryById(userId);
        if (user == null) {
            return userId;
        }
        return getUserId();
    }

    @Override
    public User queryById(String userId) {
        if (userId == null) {
            userId = getUserId();
        }
        return userMapper.queryById(userId);
    }

    @Override
    public User queryByName(String userName) {
        return userMapper.queryByName(userName);
    }

    @Override
    public List<User> fuzzyQueryByName(String userName, int curPage, int pageSize) {
        PageHelper.startPage(curPage ,pageSize);
        List<User> users = userMapper.fuzzyQueryByName(userName);
        PageInfo<User> result = new PageInfo<>(users);
        return users;
    }

    @Override
    public void updateBySelective(User user) {
        if (user.getAvatar() != null  && !"".equals(user.getAvatar())) {
            String imageUrl = imageService.uploadImage(user.getAvatar(), ".jpg");
            user.setAvatar(imageUrl);
        }
        User updateUser = new User();
        updateUser.setNickName(user.getNickName());
        updateUser.setSignature(user.getSignature());
        updateUser.setUserId(getUserId());
        userMapper.updateBySelective(getUserId(), user.getNickName(), user.getAvatar(), user.getSignature(), null);
    }

    @Override
    public void deleteById(String userId) {
        userMapper.deleteById(userId);
    }

    @Override
    public void deleteByName(String userName) {
        userMapper.deleteByName(userName);
    }

    @Override
    public User updatePassword(String oldPassword, String newPassword) {
        if (oldPassword.equals(newPassword)) {
            throw ResultCode.SAME_PASSWORD.build();
        }
        String userId = getUserId();
        User user = userMapper.queryById(userId);
        if (!user.getPassword().equals(oldPassword)) {
            throw ResultCode.OLD_PASSWORD_ERROR.build();
        }
        userMapper.updateBySelective(userId, null, null, null, newPassword);
        return userMapper.queryById(userId);
    }

    @Override
    public boolean checkChannelAndCahceMessage(String userId, Channel channel, Packet packet) {
        if (channel == null || !channel.isActive() || !channel.isOpen() || !channel.isWritable()) {
            redisTemplate.opsForSet().add(REDIS_MESSAGE_CACHE + userId, packet);
            return true;
        }
        return false;
    }

    @Override
    @Async
    public void sendCahceMessage(String userId, Channel channel) {
        Set<Packet> members = (Set)redisTemplate.opsForSet().members(REDIS_MESSAGE_CACHE + userId);
        if (members == null) {
            return;
        }
        ArrayList<Packet> packets = new ArrayList<>(members);
        packets.sort(Comparator.comparing(Packet::getDateTime));
        for (Packet packet :  packets) {
            channel.writeAndFlush(packet);
            System.out.println(packet.getDateTime());
        }
        redisTemplate.delete(REDIS_MESSAGE_CACHE + userId);
    }

    @Override
    @Async
    public void sendCahceMessage() {
        String userId = getUserId();
        Channel channel = SessionUtil.getChannel(userId);
        if (channel != null) {
            sendCahceMessage(userId, channel);
        }
    }
}
