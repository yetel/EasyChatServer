package com.easychat.mapper;

import com.easychat.bean.Group;
import com.easychat.bean.User;
import com.easychat.bean.UserGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: Zed
 * date: 2019/08/22.
 * description:
 */
@Mapper
public interface GroupMapper {
    int addGroup(Group group);

    List<String> getMembers(@Param("groupId")String groupId);
    List<User> getMemberInfo(@Param("groupId")String groupId);

    Group getGroup(@Param("groupId") String groupId);

    void addmember(UserGroup userGroup);
    
    int queryMemberNum(@Param("groupId") String groupId);

    void removemember(@Param("groupId")String groupId, @Param("userId")String userId);

    void removeAllmember(@Param("groupId")String groupId);

    UserGroup queryHasmember(@Param("groupId") String groupId, @Param("userId")String userId);

    void addInviteMember(@Param("groupId")String groupId, @Param("userId")String userId);

    void removeInviteMember(@Param("groupId")String groupId, @Param("userId")String userId);

    void removeAllInviteMember(@Param("groupId")String groupId);

    UserGroup queryHasInviteMember(@Param("groupId")String groupId, @Param("userId")String userId);
    List<Group> queryGroupsByUserId(@Param("userId") String userId);
    List<Group> fuzzyQueryGroupsByName(@Param("groupName") String groupName);
    
    void update(@Param("groupId") String groupId, @Param("groupName")String groupName, @Param("avatar")String avatar);
}
