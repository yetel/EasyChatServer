package com.easychat.support;

/**
 * @author Zed
 * date: 2019/08/19.
 * description:
 */
public interface Command {
    /** 登录*/
    byte LOGIN_RRQ = 1;
    byte LOGIN_RESP = 2;
    /** 登出*/
    byte LOGOUT_REQ = 3;
    byte LOGOUT_RESP = 4;
    /** 发送信息*/
    byte SEND_MESSAGE_REQ = 5;
    byte SEND_MESSAGE_RESP = 6;

    byte ADD_USER_REQ = 7;
    byte ADD_USER_RESP = 8;
    byte ADD_USER_SELF_RESP = 9;
    byte CREATE_GROUP_REQ = 10;
    byte CREATE_GROUP_RESP = 11;
    byte INVITE_GROUP_REQ = 12;
    byte INVITE_GROUP_RESP = 13;
    byte INVITE_GROUP_SELF_RESP = 14;
    byte GROUP_MESSAGE_REQ = 15;
    byte GROUP_MESSAGE_RESP = 16;
    byte ACCEPT_GROUP_REQ = 17;
    byte ACCEPT_GROUP_RESP = 18;
    byte ACCEPT_REQ = 19;
    byte ACCEPT_RESP = 20;
    byte REGISTER_REQ = 21;
    byte REGISTER_RESP = 22;
    byte UPDATE_PASSWD_REQ = 23;
    byte UPDATE_PASSWD_RESP = 24;
    byte MESSAGE_SELF_RESP = 25;
    byte HEAT_BEAT_REQ = 26;
    byte HEAT_BEAT_RESP = 27;
    byte SYNC_MESSAGE_REQ = 28;
    byte APPLY_GROUP_REQ = 29;
    byte APPLY_GROUP_RESP = 30;
    byte APPLY_GROUP_SELF_RESP = 31;
    byte ALLOW_GROUP_REQ = 32;
    byte ALLOW_GROUP_RESP = 33;
}
