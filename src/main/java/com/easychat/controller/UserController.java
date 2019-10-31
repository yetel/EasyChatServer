package com.easychat.controller;

import com.easychat.bean.QueryBean;
import com.easychat.bean.Result;
import com.easychat.bean.User;
import com.easychat.service.AllQueryService;
import com.easychat.service.FriendService;
import com.easychat.service.GroupService;
import com.easychat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author: Zed
 * date: 2019/08/26.
 * description:
 */
@RestController
public class UserController {
    @Autowired
    private FriendService friendService;
    
    @Autowired
    private GroupService groupService;
    @Autowired
    private UserService userService;
    
    @Autowired
    private AllQueryService allQueryService;

    /**
     * 获取用户好友列表
     * @return
     */
    @GetMapping("users")
    public Result<List<User>> getFriends() {
        List<User> friends = friendService.getFriends();
        return Result.success(friends);
    }

    /**
     * 根据用户id获取用户信息
     * @param userId
     * @return
     */
    @GetMapping("user/{userId}")
    public Result<User> getUserById(@PathVariable("userId") String userId) {
        return Result.success(userService.queryById(userId));
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @PutMapping("user/update")
    public Result<User> updateUserInfo(@RequestBody User user) {
        userService.updateBySelective(user);
        return Result.success(userService.queryById(null));
    }

    /**
     * 更新用户密码
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @PutMapping("update/password")
    public Result<User> updateUserInfo(@RequestParam("oldPassword")String oldPassword, @RequestParam("newPassword")String newPassword) {
        return Result.success(userService.updatePassword(oldPassword, newPassword));
    }

    /**
     * 更新好友备注
     * @param friendId
     * @param remark
     * @return
     */
    @PutMapping("update/friend/remark")
    public Result<User> updateUserRemark(@RequestParam("friendId")String friendId, @RequestParam("remark")String remark) {
        return Result.success(friendService.updateFriendRemark(friendId, remark));
    }

    /**
     * 模糊查询用户信息
     * @param userName
     * @param curPage
     * @param pageSize
     * @return
     */
    @GetMapping("fuzzy/query/users")
    public Result<List<User>> fuzzyQueryUser(@RequestParam("userName") String userName,
                                             @RequestParam(value = "curPage", required = false, defaultValue = "1") Integer curPage,
                                             @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize) {
        return Result.success(userService.fuzzyQueryByName(userName, curPage, pageSize));
    }

    /**
     * 万能查询 根据key查询用户 群组信息  可以匹配用户id  用户名称 用户昵称  群组id 群组名称  全他妈查出来 就是数据多的话，速度有点慢 
     * @param key
     * @param curPage
     * @param pageSize
     * @return
     */
    @GetMapping("fuzzy/allQuery")
    public Result<List<QueryBean>> fuzzyQuery(@RequestParam("key") String key,
                                        @RequestParam(value = "curPage", required = false, defaultValue = "1") Integer curPage,
                                        @RequestParam(value = "pageSize", required = false, defaultValue = "20") Integer pageSize){
        return Result.success(allQueryService.queryAll(key, curPage, pageSize));
    }

    /**
     * 获取缓存信息  启用，增加了netty消息获取
     * @return
     */
    @Deprecated
    @GetMapping("user/get/cacheMessage")
    public Result<Void> getCacheMessage() {
        userService.sendCahceMessage();
        return Result.success();
    }
}
