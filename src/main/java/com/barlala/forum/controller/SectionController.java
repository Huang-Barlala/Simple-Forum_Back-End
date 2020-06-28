package com.barlala.forum.controller;

import com.barlala.forum.service.ResultJson;
import com.barlala.forum.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @GetMapping(value = "/getSections")
    public ResultJson<?> getSections() {
        return new ResultJson<>(HttpStatus.OK, sectionService.getSections());
    }
}
