package com.easychat.mapper;

import com.easychat.bean.User;
import com.easychat.bean.UserFrind;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: Zed
 * date: 2019/08/22.
 * description:
 */
@Mapper
public interface FriendMapper {
    void addFriend(@Param("userId")String userId, @Param("friendId")String friendId);

    void removeFriend(@Param("userId")String userId, @Param("friendId")String friendId);

    void removeAllFriend(@Param("userId")String userId);

    UserFrind queryHasFriend(@Param("userId")String userId, @Param("friendId")String friendId);

    List<User> getFriends(@Param("userId")String userId);
    
    User getFriend(@Param("userId")String userId, @Param("friendId")String friendId);
    
    void updateRemark(@Param("userId")String userId, @Param("friendId")String friendId, @Param("remark")String remark);

    void addInviteFriend(@Param("userId")String userId, @Param("friendId")String friendId);

    void removeInviteFriend(@Param("userId")String userId, @Param("friendId")String friendId);

    void removeAllInviteFriend(@Param("userId")String userId);

    UserFrind queryHasInviteFriend(@Param("userId")String userId, @Param("friendId")String friendId);
}
