package com.easychat.service;

import com.easychat.support.Packet;
import com.easychat.bean.User;
import io.netty.channel.Channel;

import java.util.List;

/**
 * @author: Zed
 * date: 2019/08/22.
 * description:
 */
public interface UserService {
    void addUser(String userName, String password, String nickName) throws Exception;
    User queryById(String userId);
    User queryByName(String userName);
    List<User> fuzzyQueryByName(String userName, int curPage, int pageSize);
    void updateBySelective(User user);
    void deleteById(String userId);
    void deleteByName(String userName);
    User updatePassword(String oldPassword, String newPassword);
    
    boolean checkChannelAndCahceMessage(String userId, Channel channel, Packet packet);

    void sendCahceMessage(String userId, Channel channel);
    void sendCahceMessage();
}
