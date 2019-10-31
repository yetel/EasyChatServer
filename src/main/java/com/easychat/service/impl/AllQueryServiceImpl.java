package com.easychat.service.impl;

import com.easychat.service.AllQueryService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.easychat.bean.Group;
import com.easychat.bean.QueryBean;
import com.easychat.bean.User;
import com.easychat.mapper.GroupMapper;
import com.easychat.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Zed
 * date: 2019/10/22.
 * description:
 */
@Service
@Slf4j
public class AllQueryServiceImpl implements AllQueryService {
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private GroupMapper groupMapper;
    
    @Override
    public List<QueryBean> queryAll(String key, int curPage, int pageSize) {
        log.debug("收到万能搜索的的请求， key={}, curPage={}, pageSize={}", key, curPage, pageSize);
        List<QueryBean> beans = new ArrayList<>();
        PageHelper.startPage(curPage ,pageSize);
        List<LinkedHashMap<String, Integer>> linkedHashMaps = userMapper.fuzzyQueryAll(key);
        PageInfo<LinkedHashMap<String, Integer>> result = new PageInfo<>(linkedHashMaps);
        log.debug("万能搜索直接查询结果集={}",  linkedHashMaps);
        
        for (LinkedHashMap<String, Integer> linkedHashMap : linkedHashMaps) {
            if ("0".equals(String.valueOf(linkedHashMap.get("type")))) {
                //用户
                User user = userMapper.queryById(String.valueOf(linkedHashMap.get("id")));
                QueryBean bean = new QueryBean();
                bean.setId(user.getUserId());
                bean.setAvatar(user.getAvatar());
                bean.setName(user.getUserName());
                bean.setNickName(user.getNickName());
                bean.setType(0);
                beans.add(bean);
            } else {
                //群
                Group group = groupMapper.getGroup(String.valueOf(linkedHashMap.get("id")));
                QueryBean bean = new QueryBean();
                bean.setId(group.getGroupId());
                bean.setAvatar(group.getAvatar());
                bean.setName(group.getGroupName());
                bean.setType(1);
                beans.add(bean);
            }
        }
        log.debug("得到查询结果， results={}", beans);
        return beans;
    }
}
