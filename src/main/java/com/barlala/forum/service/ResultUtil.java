package com.barlala.forum.service;

/**
 * @author Barlala
 * @version 1.0
 * @date 2020/7/4 下午4:24
 */
public class ResultUtil {
    public static Result<Object> success(Object data) {
        Result<Object> result = new Result<>();
        result.setCode(200);
        result.setMsg("success");
        result.setData(data);
        return result;
    }

    public static Result<?> success() {
        Result<?> result = new Result<>();
        result.setCode(200);
        result.setMsg("success");
        return result;
    }

    public static Result<?> error(String msg) {
        Result<?> result = new Result<>();
        result.setCode(400);
        result.setMsg(msg);
        return result;
    }

    public static Result<?> error() {
        Result<?> result = new Result<>();
        result.setCode(400);
        result.setMsg("error");
        return result;
    }
}
