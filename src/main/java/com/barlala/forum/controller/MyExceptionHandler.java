package com.barlala.forum.controller;

import com.barlala.forum.service.ResultJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
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
    public ResultJson<Object> exception(Exception e) {
        logger.info(e.getMessage());
        return new ResultJson<>(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(value = MissingRequestCookieException.class)
    public ResultJson<Object> missingRequestCookieException(MissingRequestCookieException e) {
        return new ResultJson<>(HttpStatus.BAD_REQUEST, "缺失cookie:" + e.getCookieName());
    }

}
