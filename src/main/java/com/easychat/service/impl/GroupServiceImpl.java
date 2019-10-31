package com.easychat.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.easychat.bean.Group;
import com.easychat.bean.ResultCode;
import com.easychat.bean.User;
import com.easychat.bean.UserGroup;
import com.easychat.mapper.GroupMapper;
import com.easychat.service.BaseService;
import com.easychat.service.GroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: Zed
 * date: 2019/08/22.
 * description:
 */
@Slf4j
@Service
public class GroupServiceImpl extends BaseService implements GroupService {
    @Resource
    private GroupMapper groupMapper;
    
    @Override
    public String addGroup(String groupName, String mainUserId) {
        log.debug("添加群组信息， groupName ={}, mainUserId = {}", groupName, mainUserId);
        Group group = new Group();
        group.setGroupName(groupName);
        group.setMainUserId(mainUserId);
        groupMapper.addGroup(group);
        return group.getGroupId();
    }

    @Override
    public List<String> getMembers(String groupId) {
        log.debug("获取群成员列表， groupId = {}", groupId);
        return groupMapper.getMembers(groupId);
    }

    @Override
    public List<User> getMemberInfo(String groupId) {
        log.debug("获取群成员列表信息， groupId = {}", groupId);
        String userId = getUserId();
        boolean b = queryHasmember(groupId, userId);
        if (!b) {
            throw ResultCode.NOT_BELONG_GROUP.build();
        }

        List<User> memberInfo = groupMapper.getMemberInfo(groupId);
        log.debug("获取群成员列表信息, memberInfo={}", memberInfo);
        return memberInfo;
    }

    @Override
    public Group getGroup(String groupId) {
        Group group = groupMapper.getGroup(groupId);
        int num = groupMapper.queryMemberNum(groupId);
        group.setMemberNum(num);
        return group;
        
    }

    @Override
    public void addmember(String groupId, String userId) {
        UserGroup userGroup = new UserGroup();
        userGroup.setGroupId(groupId);
        userGroup.setUserId(userId);
        groupMapper.addmember(userGroup);
    }

    @Override
    public void removemember(String groupId, String userId) {
        groupMapper.removemember(groupId, userId);
    }

    @Override
    public void removeAllmember(String groupId) {
        groupMapper.removeAllmember(groupId);
    }

    @Override
    public boolean queryHasmember(String groupId, String userId) {
        UserGroup userGroup = groupMapper.queryHasmember(groupId, userId);
        return userGroup != null;
    }

    @Override
    public void addInviteMember(String groupId, String userId) {
        groupMapper.addInviteMember(groupId, userId);
    }

    @Override
    public void removeInviteMember(String groupId, String userId) {
        groupMapper.removeInviteMember(groupId, userId);
    }

    @Override
    public void removeAllInviteMember(String groupId) {
        groupMapper.removeAllInviteMember(groupId);
    }

    @Override
    public boolean queryHasInviteMember(String groupId, String userId) {
        UserGroup userGroup = groupMapper.queryHasInviteMember(groupId, userId);
        return userGroup != null;
    }

    @Override
    public List<Group> queryGroupsByUserId() {
        return groupMapper.queryGroupsByUserId(getUserId());
    }
    
    @Override
    public List<Group> fuzzyQueryGroupsByName(String groupName, int curPage, int pageSize) {
        PageHelper.startPage(curPage ,pageSize);
        List<Group> groups = groupMapper.fuzzyQueryGroupsByName(groupName);
        PageInfo<Group> result = new PageInfo<>(groups);
        return groups;
    }

    @Override
    public Group update(String groupId, String groupName, String avatar) {
        String userId = getUserId();
        UserGroup userGroup = groupMapper.queryHasmember(groupId, userId);
        if (userGroup != null) {
            groupMapper.update(groupId, groupName, avatar);
        } else {
            throw ResultCode.NOT_BELONG_GROUP.build();
        }
        Group group = getGroup(groupId);
        return group;
    }
}
