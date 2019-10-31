package com.easychat.service;

import com.easychat.bean.Group;
import com.easychat.bean.User;

import java.util.List;

/**
 * @author: Zed
 * date: 2019/08/22.
 * description:
 */
public interface GroupService {
    String addGroup(String groupName, String mainUserId);

    List<String> getMembers(String groupId);
    
    List<User> getMemberInfo(String groupId);

    Group getGroup(String groupId);

    void addmember(String groupId, String userId);

    void removemember(String groupId, String userId);

    void removeAllmember(String groupId);

    boolean queryHasmember(String groupId, String userId);

    void addInviteMember(String groupId, String userId);

    void removeInviteMember(String groupId, String userId);

    void removeAllInviteMember(String groupId);

    boolean queryHasInviteMember(String groupId, String userId);
    
    List<Group> queryGroupsByUserId();
    
    List<Group> fuzzyQueryGroupsByName(String groupName, int curPage, int pageSize);

    Group update(String groupId, String groupName, String avatar);
}
