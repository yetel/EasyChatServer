package com.easychat.service.impl;

import com.easychat.service.BaseService;
import com.easychat.service.FriendService;
import com.easychat.bean.User;
import com.easychat.bean.UserFrind;
import com.easychat.mapper.FriendMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: Zed
 * date: 2019/08/22.
 * description:
 */
@Service
@Slf4j
public class FriendServiceImpl extends BaseService implements FriendService {
    @Resource
    private FriendMapper friendMapper;
    @Override
    public void addFriend(String userId, String friendId) {
        log.debug("添加好友， userid ={}, friendId = {}", userId, friendId);
        friendMapper.addFriend(userId, friendId);
    }

    @Override
    public void removeFriend(String userId, String friendId) {
        log.debug("删除好友， userid ={}, friendId = {}", userId, friendId);
        friendMapper.removeFriend(userId, friendId);
    }

    @Override
    public void removeAllFriend(String userId) {
        friendMapper.removeAllFriend(userId);
    }

    @Override
    public boolean queryHasFriend(String userId, String friendId) {
        log.debug("查询是否好友关系， userid ={}, friendId = {}", userId, friendId);
        UserFrind userFrind = friendMapper.queryHasFriend(userId, friendId);
        return userFrind != null;
    }

    @Override
    public List<User> getFriends() {
        log.debug("获取好友列表, userId = {}", getUserId());
        return friendMapper.getFriends(getUserId());
    }

    @Override
    public User updateFriendRemark(String friendId, String remark) {
        String userId = getUserId();
        log.debug("修改好友备注, userId = {}, friendId={}, remark={}", userId, friendId, remark);
        friendMapper.updateRemark(userId, friendId, remark);
        return friendMapper.getFriend(userId, friendId);
    }

    @Override
    public void addInviteFriend(String userId, String friendId) {
        log.debug("添加好友邀请信息， userid ={}, friendId = {}", userId, friendId);
        friendMapper.addInviteFriend(userId, friendId);
    }

    @Override
    public void removeInviteFriend(String userId, String friendId) {
        log.debug("删除好友邀请信息， userid ={}, friendId = {}", userId, friendId);
        friendMapper.removeInviteFriend(userId, friendId);
    }

    @Override
    public void removeAllInviteFriend(String userId) {
        friendMapper.removeAllInviteFriend(userId);
    }

    @Override
    public boolean queryHasInviteFriend(String userId, String friendId) {
        UserFrind userFrind = friendMapper.queryHasInviteFriend(userId, friendId);
        return userFrind != null;
    }
}
