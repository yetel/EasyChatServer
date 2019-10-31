package com.easychat.controller;

import com.easychat.bean.AppVersion;
import com.easychat.bean.Result;
import com.easychat.service.VersionService;
import com.easychat.util.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * @author: Zed
 * date: 2019/08/30.
 * description:
 */
@RestController
@RequestMapping("system")
@Slf4j
public class SystemController {
    
    @PostMapping("onlineNum")
    public Result<String> getSystemOnlineNum() throws InterruptedException {
        Thread.sleep(12000);
        return Result.success("系统在线人数为：" + SessionUtil.userChannelMap.size()); 
    }

    @Autowired
    private VersionService versionService;

    /**
     * 检验版本
     * @param versionCode 当前版本码
     * @param appType App类型： 可选，默认为0 
     *           0.	智慧停车个人端1.	智慧停车巡检端
     * @return
     */
    @GetMapping("checkVersion")
    public Result<AppVersion> checkVersion(@RequestParam(value = "versionCode") @NotNull Integer versionCode, Integer appType) {
        log.debug("start requesting checkVersion, versionCode = {}, appType = {}", versionCode, appType);

        appType = appType == null ? 0: appType;
        AppVersion appVersion = versionService.queryNewVersion(versionCode, appType);

        return Result.success(appVersion);
    }
}
