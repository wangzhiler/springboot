package com.wzl.exception;

/**
 * Created by thinkpad on 2018/9/30.
 */
public class UserNotExistException extends RuntimeException {

    public UserNotExistException() {
        super("用户不存在");
    }
}
