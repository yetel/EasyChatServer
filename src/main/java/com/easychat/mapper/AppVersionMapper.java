package com.easychat.mapper;

import com.easychat.bean.AppVersion;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Description:
 * Created by Zed on 2019/01/03.
 */
@Mapper
public interface AppVersionMapper {
    
    AppVersion queryNewVersion(@Param("versionNum") int versionNum, @Param("appType") int appType);

    Integer isForcedUpgrade(@Param("versionNum") int versionNum, @Param("appType") int appType);
}
