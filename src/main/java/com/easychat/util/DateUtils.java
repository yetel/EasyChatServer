package com.easychat.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author: Zed
 * date: 2019/08/27.
 * description:
 */
public class DateUtils {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm:ss");
    public static String now() {
        return LocalDateTime.now().format(formatter);
    }
}
