package com.easychat.service;

import com.easychat.bean.User;

import java.util.List;

/**
 * @author: Zed
 * date: 2019/08/22.
 * description:
 */
public interface FriendService {
    /**
     * 添加好友
     * @param userId
     * @param friendId
     */
    void addFriend(String userId, String friendId);

    /**
     * 删除好友
     * @param userId
     * @param friendId
     */
    void removeFriend(String userId, String friendId);

    /**
     * 删除所有好友
     * @param userId
     */
    void removeAllFriend(String userId);

    /**
     * 查询是否为好友
     * @param userId
     * @param friendId
     * @return
     */
    boolean queryHasFriend(String userId, String friendId);

    /**
     * 获取好友列表
     * @return
     */
    List<User> getFriends();

    /**
     *更新好友备注
     * @param friendId
     * @param remark
     * @return
     */
    User updateFriendRemark(String friendId, String remark);

    void addInviteFriend(String userId, String friendId);

    void removeInviteFriend(String userId, String friendId);

    void removeAllInviteFriend(String userId);

    boolean queryHasInviteFriend(String userId, String friendId);
}
