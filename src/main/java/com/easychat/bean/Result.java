package com.easychat.bean;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description: 结果
 * Created by Zed on 2019/01/02.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> {

    // 结果状态
    private String code;
    // 结果消息
    private String desc;
    // 结果
    @JsonInclude(value=JsonInclude.Include.NON_NULL)
    private T data;
    
    /**
     * 成功响应码
     * @return
     */
    public static <T> Result<T> success() {
        return new Result<>(ResultCode.SUCCESS.getCode(),
                ResultCode.SUCCESS.getDesc(), null);
    }

    /**
     * 成功响应码
     * @param data
     * @return
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getDesc(), data);
    }

    /**
     * 失败响应码
     * @param code
     * @param desc
     * @return
     */
    public static <T> Result<T> fail(String code, String desc) {
        return new Result<>(code, desc, null);
    }
    
    /**
     * 失败响应码
     * @param code
     * @param desc
     * @return
     */
    public static <T> Result<T> fail(String code, String desc, Object... args) {
        return fail(code, String.format(desc, args));
    }

    /**
     * 失败响应码
     * @param resultCode
     * @return
     */
    public static <T> Result<T> fail(ResultCode resultCode) {
        return new Result<>(resultCode.getCode(), resultCode.getDesc(), null);
    }
    
    /**
     * 失败响应码
     * @param resultCode
     * @return
     */
    public static <T> Result<T> fail(ResultCode resultCode, Object... args) {
        return fail(resultCode.getCode(), String.format(resultCode.getDesc(), args));
    }

    public static void main(String[] args) {
        User user = new User();
        String s = JSON.toJSONString(Result.success());
        System.out.println(s);
    }

}
