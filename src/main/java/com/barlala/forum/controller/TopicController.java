package com.barlala.forum.controller;

import com.barlala.forum.entity.Topic;
import com.barlala.forum.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    public TopicController(AuthenticationService authenticationService,
                           SectionService sectionService,
                           TopicService topicService,
                           UserService userService) {
        this.authenticationService = authenticationService;
        this.sectionService = sectionService;
        this.topicService = topicService;
    }


    @PostMapping(value = "/addTopic")
    public ResultJson<?> addTopic(@CookieValue String jwt,
                                  @RequestParam(value = "sectionId") int sectionId,
                                  @NotBlank(message = "标题不能为空") @RequestParam(value = "title") String title,
                                  @NotBlank(message = "内容不能为空") @RequestParam(value = "content") String content) {
        int userId = authenticationService.verifyToken(jwt);
        if (userId == -1) {
            return new ResultJson<>(HttpStatus.BAD_REQUEST, "未登录");
        }
        if (!sectionService.isSectionInSectionList(sectionId)) {
            return new ResultJson<>(HttpStatus.BAD_REQUEST, "板块错误");
        }
        Topic topic = new Topic();
        topic.setSectionid(sectionId);
        topic.setUserid(userId);
        topic.setTitle(title);
        topic.setContent(content);
        Date now = new Date();
        topic.setCreatetime(now);
        topic.setModifytime(now);
        topic.setReplytime(now);
        topic.setAuthor(authenticationService.getUsername(jwt));
        return topicService.addTopic(topic);
    }

    @GetMapping(value = "/getTopicList")
    public ResultJson<?> getTopicList(@RequestParam(value = "page", required = false) Integer page,
                                      @RequestParam(value = "sectionId") int sectionId,
                                      @RequestParam(value = "order", required = false) Integer order) {
        if (!sectionService.isSectionInSectionList(sectionId)) {
            return new ResultJson<>(HttpStatus.BAD_REQUEST, "分区不存在");
        }
        if (page == null) {
            page = 1;
        }
        if (topicService.isPageOutOfRange(sectionId, page)) {
            return new ResultJson<>(HttpStatus.BAD_REQUEST, "页数超出范围");
        }
        List<Topic> topicList = topicService.getTopicsList(page, order, sectionId);
        return new ResultJson<>(HttpStatus.OK, topicList);
    }

    @GetMapping(value = "/topicPageNum")
    public ResultJson<?> topicPageNum(@RequestParam(value = "sectionId") int sectionId) {
        return new ResultJson<>(HttpStatus.OK, topicService.topicPages(sectionId));
    }

    @GetMapping(value = "/getTopic")
    public ResultJson<?> getTopic(@RequestParam(value = "topicId") int topicId){
        Topic topic = topicService.getTopic(topicId);
        if (topic == null) {
            return new ResultJson<>(HttpStatus.BAD_REQUEST, "Topic不存在");
        }
        return new ResultJson<>(HttpStatus.OK, topic);
    }
}
