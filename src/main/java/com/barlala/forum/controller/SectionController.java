package com.barlala.forum.controller;

import com.barlala.forum.service.Result;
import com.barlala.forum.service.ResultUtil;
import com.barlala.forum.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Barlala
 * @version 1.0
 * @date 2020/6/25 下午3:50
 */
@RestController
public class SectionController {
    private final SectionService sectionService;

    @Autowired
    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping(value = "/api/sections")
    public Result<?> getSections() {
        return ResultUtil.success(sectionService.getSections());
    }
}
