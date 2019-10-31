package com.easychat.util;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author Zed
 * date: 2019/08/20.
 * description:
 */
public class RandomIdUtil {
    /**
     * 根据时间戳到秒加两位随机数生成唯一id
     * @return
     */
    public static String generateUniqId() {
        long curTime = System.currentTimeMillis()/1000;
        String timeString = Long.toString(curTime, 16);
        /*if (timeString.length() == 8) {
            timeString = "0" + timeString;
        }*/
        String randon = RandomStringUtils.randomAlphanumeric(2);
        return timeString + randon;
    }
}
