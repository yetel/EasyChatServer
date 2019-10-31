/*
 * @(#)ResultCodeEnum.java 2017年10月23日
 * Copyright (c), 2017 深圳业拓讯通信科技有限公司（Shenzhen Yetelcom Communication Tech. Co.,Ltd.）,  
 * 著作权人保留一切权利，任何使用需经授权。
 */

package com.easychat.bean;


public enum ResultCode {
    
    /** 成功 */
    SUCCESS("0", "Success"),
    /** 参数不合法 */
    PARAM_INVALID("10000", "参数 %s 异常"),
    /** 数据库异常 */
    DB_EXCEPTION("10001", "数据库异常"),
    /** 系统内部错误 */
    SYSTEM_ERROR("10002", "系统内部错误"),
    /**
     * 请求头中没有携带token
     */
    NO_SESSION("10003", "请求头中没有携带token"),
    /**
     * 请求token不合法
     */
    INVALID_SESSION("10004", "请求token不合法"),
    /**
     * 请求的token已过期
     */
    EXP_SESSIOS("10005", "请求的token已过期"),
    OLD_PASSWORD_ERROR("10006", "修改密码失败，旧密码错误"),
    SAME_PASSWORD("10007", "修改密码失败，新密码跟老密码没有改变"),
    NOT_BELONG_GROUP("10008", "您不是该群的成员，不能进行该操作"),
    ;

    ResultCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private String code;

    private String desc;
    

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public ServiceException build(String... format) {
        desc = String.format(desc, format);
        return ServiceException.builder().code(this.code).desc(desc).build();
    }

    public ServiceException build() {
        return ServiceException.builder().code(this.code).desc(desc).build();
    }

}
