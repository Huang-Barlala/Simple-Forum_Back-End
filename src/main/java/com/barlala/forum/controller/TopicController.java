package com.barlala.forum.controller;

import com.barlala.forum.entity.Topic;
import com.barlala.forum.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * @author Barlala
 * @version 1.0
 * @date 2020/6/23 下午2:08
 */
@RestController
@Validated
public class TopicController {
    private final AuthenticationService authenticationService;
    private final SectionService sectionService;
    private final TopicService topicService;
    private final ReplyService replyService;
    private final UserService userService;

    @Autowired
    public TopicController(AuthenticationService authenticationService,
                           SectionService sectionService,
                           TopicService topicService,
                           ReplyService replyService,
                           UserService userService) {
        this.authenticationService = authenticationService;
        this.sectionService = sectionService;
        this.topicService = topicService;
        this.replyService = replyService;
        this.userService = userService;
    }


    @PostMapping(value = "/api/addTopic")
    public Result<?> addTopic(@CookieValue String jwt,
                              @RequestParam(value = "sectionId") int sectionId,
                              @NotBlank(message = "标题不能为空") @RequestParam(value = "title") String title,
                              @NotBlank(message = "内容不能为空") @RequestParam(value = "content") String content) {
        int userId = authenticationService.verifyToken(jwt);
        if (userId == -1) {
            return ResultUtil.error("权限校验失败");
        }
        if (!sectionService.isSectionInSectionList(sectionId)) {
            return ResultUtil.error("板块错误");
        }
        Topic topic = new Topic();
        topic.setSectionid(sectionId);
        topic.setUserid(userId);
        topic.setTitle(title);
        content = content.replaceAll("src=\"api/image/", "src=\"/api/image/");
        topic.setContent(content);
        Date now = new Date();
        topic.setCreatetime(now);
        topic.setModifytime(now);
        topic.setReplytime(now);
        topic.setAuthor(authenticationService.getUsername(jwt));
        topic.setAuthoravatar(authenticationService.getAvatar(jwt));
        if (topicService.addTopic(topic)) {
            userService.updateTopicNum(userId, true);
            return ResultUtil.success();
        }
        return ResultUtil.error();
    }

    @GetMapping(value = "/api/getTopicList")
    public Result<?> getTopicList(@RequestParam(value = "page", required = false) Integer page,
                                  @RequestParam(value = "sectionId") int sectionId,
                                  @RequestParam(value = "order", required = false) Integer order) {
        if (!sectionService.isSectionInSectionList(sectionId)) {
            return ResultUtil.error("分区不存在");
        }
        if (page == null) {
            page = 1;
        }
        if (topicService.isPageOutOfRange(sectionId, page)) {
            return ResultUtil.error("页数超出范围");
        }
        List<Topic> topicList = topicService.getTopicsList(page, order, sectionId);
        return ResultUtil.success(topicList);
    }

    @GetMapping(value = "/api/topicPageNum")
    public Result<?> topicPageNum(@RequestParam(value = "sectionId") int sectionId) {
        return ResultUtil.success(topicService.topicPages(sectionId));
    }

    @GetMapping(value = "/api/getTopic")
    public Result<?> getTopic(@RequestParam(value = "topicId") int topicId) {
        Topic topic = topicService.getTopic(topicId);
        if (topic == null) {
            return ResultUtil.error("Topic不存在");
        }
        return ResultUtil.success(topic);
    }

    @GetMapping("/api/getUserTopicInfo")
    public Result<?> getUserTopicInfo(@CookieValue String jwt) {
        int userId = authenticationService.verifyToken(jwt);
        if (userId == -1) {
            return ResultUtil.error("权限校验失败");
        }
        return ResultUtil.success(topicService.getUserTopic(userId));
    }

    @PostMapping("/api/deleteTopic")
    public Result<?> deleteTopic(@CookieValue String jwt,
                                 @RequestParam(value = "topicId") int topicId) {
        int userId = authenticationService.verifyToken(jwt);
        if (userId == -1) {
            return ResultUtil.error("权限校验失败");
        }
        boolean isDelete = "admin".equals(authenticationService.getPermission(jwt)) || userId == topicService.getTopic(topicId).getUserid();
        if (isDelete) {
            if (topicService.deleteTopic(topicId)) {
                replyService.deleteReplyByTopic(topicId);
                userService.updateTopicNum(userId, false);
                return ResultUtil.success();
            }
            return ResultUtil.error("删除失败");
        }
        return ResultUtil.error("无删除权限");
    }

    @PostMapping("/api/modifyTopic")
    public Result<?> modifyTopic(@CookieValue String jwt,
                                 @RequestParam(value = "topicId") int topicId,
                                 @NotBlank(message = "标题不能为空") @RequestParam(value = "title") String title,
                                 @NotBlank(message = "内容不能为空") @RequestParam(value = "content") String content) {
        int userId = authenticationService.verifyToken(jwt);
        if (userId == -1) {
            return ResultUtil.error("权限校验失败");
        }
        boolean isUpdate = userId == topicService.getTopic(topicId).getUserid();
        if (isUpdate) {
            Topic topic = new Topic();
            topic.setId(topicId);
            topic.setTitle(title);
            topic.setContent(content);
            topic.setModifytime(new Date());
            if (topicService.updateTopic(topic)) {
                return ResultUtil.success();
            }
            return ResultUtil.error("修改失败");
        }
        return ResultUtil.error("无修改权限");
    }
}
