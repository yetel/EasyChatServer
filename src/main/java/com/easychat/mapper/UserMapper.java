package com.easychat.mapper;

import com.easychat.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author: Zed
 * date: 2019/08/22.
 * description:
 */
@Mapper
public interface UserMapper {
    int addUser(User user);
    User queryById(@Param("userId")String userId);
    User queryByName(String userName);
    List<User> fuzzyQueryByName(String userName);
    int updateBySelective(@Param("userId")String userId, @Param("nickName")String nickName, @Param("avatar")String avatar, @Param("signature")String signature ,@Param("password")String password);
    void deleteById(@Param("userId")String userId);
    void deleteByName(@Param("userName")String userName);
    List<LinkedHashMap<String, Integer>> fuzzyQueryAll(@Param("key")String key);
}
