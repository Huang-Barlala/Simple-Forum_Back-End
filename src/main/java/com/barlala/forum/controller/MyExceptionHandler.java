package com.barlala.forum.controller;

import com.barlala.forum.service.Result;
import com.barlala.forum.service.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.MissingRequestCookieException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Barlala
 * @version 1.0
 * @date 2020/6/23 下午4:49
 */
@RestControllerAdvice
public class MyExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = Exception.class)
    public Result<?> exception(Exception e) {
        logger.info(e.getMessage());
        return ResultUtil.error(e.getMessage());
    }

    @ExceptionHandler(value = MissingRequestCookieException.class)
    public Result<?> missingRequestCookieException(MissingRequestCookieException e) {
        return ResultUtil.error("缺失cookie:" + e.getCookieName());
    }

}
