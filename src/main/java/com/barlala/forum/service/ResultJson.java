package com.barlala.forum.service;

import org.springframework.http.HttpStatus;

/**
 * @author Barlala
 * @version 1.0
 * @date 2020/6/22 下午9:50
 */
public class ResultJson<T> {
    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public ResultJson() {
    }

    public ResultJson(HttpStatus status) {
        this.code = status.value();
        this.msg = status.getReasonPhrase();
    }

    public ResultJson(HttpStatus status, String msg) {
        this.code = status.value();
        this.msg = msg;
    }

    public ResultJson(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
