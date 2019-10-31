package com.easychat.controller;

import com.easychat.bean.Group;
import com.easychat.bean.Result;
import com.easychat.bean.User;
import com.easychat.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Zed
 * date: 2019/10/22.
 * description:
 */
@RestController
public class GroupController {
    
    @Autowired
    private GroupService groupService;

    /**
     * 修改群组消息 只有群成员可以修改群信息 之后会给所有的群成员发送一条群变动消息
     * @param group 修改的群信息
     * @return 返回修改后的群信息
     */
    @PostMapping("group/update")
    public Result<Group> updateGroup(@RequestBody Group group){
        return Result.success(groupService.update(group.getGroupId(), group.getGroupName(), group.getAvatar()));
    }

    /**
     * 获取群组列表
     * @return
     */
    @GetMapping("groups")
    public Result<List<Group>> getGroups() {
        List<Group> groups = groupService.queryGroupsByUserId();
        return Result.success(groups);
    }

    /**
     * 根据群组id获取群组信息
     * @param groupId
     * @return
     */
    @GetMapping("group/{groupId}")
    public Result<Group> getGroupById(@PathVariable("groupId") String groupId) {
        return Result.success(groupService.getGroup(groupId));
    }


    /**
     * 模糊查询群组信息
     * @param groupName
     * @param curPage
     * @param pageSize
     * @return
     */
    @GetMapping("fuzzy/query/groups")
    public Result<List<Group>> fuzzyQueryGroup(@RequestParam("groupName") String groupName,
                                               @RequestParam(value = "curPage", required = false, defaultValue = "1") Integer curPage,
                                               @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize) {
        return Result.success(groupService.fuzzyQueryGroupsByName(groupName, curPage, pageSize));
    }

//    /**
//     * 删除群聊 只有群主可以删除群
//     *删除之后会给所有的群成员发送一条群变动消息
//     * @param groupId 群id
//     * @return
//     */
//    @DeleteMapping("group/delete/{groupId}")
//    public Result<Void> deleteGroup(@PathVariable("groupId") String groupId) {
//        
//    }
//
//    /**
//     * 退出群聊 之后会给所有的群成员发送一条群变动消息
//     * 群主不能退出群聊
//     * @param groupId 群id
//     * @return
//     */
//    @DeleteMapping("group/quit/{groupId}")
//    public Result<Void> deleteGroup(@PathVariable("groupId") String groupId) {
//
//    }
//
//    /**
//     * 踢出群聊 只能群主踢人 不能踢出自己  
//     * @param groupId 群id
//     * @param userId 群成员
//     * @return
//     */
//    @DeleteMapping("group/kick/{groupId}")
//    public Result<Void> deleteGroup(@PathVariable("groupId") String groupId, @RequestParam("userId") String userId) {
//
//    }
//
    @GetMapping("query/member/{groupId}")
    public Result<List<User>> queryMember(@PathVariable("groupId") String groupId) {
        return Result.success(groupService.getMemberInfo(groupId));
    }
}
