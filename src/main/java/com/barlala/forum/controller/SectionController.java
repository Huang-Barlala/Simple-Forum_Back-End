package com.barlala.forum.controller;

import com.barlala.forum.entity.Section;
import com.barlala.forum.service.AuthenticationService;
import com.barlala.forum.service.Result;
import com.barlala.forum.service.ResultUtil;
import com.barlala.forum.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

/**
 * @author Barlala
 * @version 1.0
 * @date 2020/6/25 下午3:50
 */
@RestController
public class SectionController {
    private final SectionService sectionService;
    private final AuthenticationService authenticationService;
    private final String ADMIN = "admin";

    @Autowired
    public SectionController(SectionService sectionService, AuthenticationService authenticationService) {
        this.sectionService = sectionService;
        this.authenticationService = authenticationService;
    }

    @GetMapping(value = "/api/sections")
    public Result<?> getSections() {
        return ResultUtil.success(sectionService.getSections());
    }

    @PutMapping("/api/section")
    public Result<?> updateSection(@CookieValue String jwt,
                                   @RequestParam(value = "id") int id,
                                   @NotBlank(message = "板块名不能为空") @RequestParam(value = "name") String name) {
        int userId = authenticationService.verifyToken(jwt);
        if (userId == -1 || !ADMIN.equals(authenticationService.getPermission(jwt))) {
            return ResultUtil.error("权限校验失败");
        }
        Section section = new Section();
        section.setId(id);
        section.setName(name);
        if (sectionService.updateSection(section)) {
            return ResultUtil.success();
        }
        return ResultUtil.error("修改失败");
    }

    @PostMapping("/api/section")
    public Result<?> insertSection(@CookieValue String jwt,
                                   @NotBlank(message = "板块名不能为空") @RequestParam(value = "name") String name) {
        int userId = authenticationService.verifyToken(jwt);
        if (userId == -1 || !ADMIN.equals(authenticationService.getPermission(jwt))) {
            return ResultUtil.error("权限校验失败");
        }
        Section section = new Section();
        section.setName(name);
        if (sectionService.insertSection(section)) {
            return ResultUtil.success();
        }
        return ResultUtil.error("添加失败");
    }

    @DeleteMapping("/api/section")
    public Result<?> deleteSection(@CookieValue String jwt,
                                   @RequestParam(value = "id") int id) {
        int userId = authenticationService.verifyToken(jwt);
        if (userId == -1 || !ADMIN.equals(authenticationService.getPermission(jwt))) {
            return ResultUtil.error("权限校验失败");
        }
        if (sectionService.deleteSection(id)) {
            return ResultUtil.success();
        }
        return ResultUtil.error("删除");
    }
}
