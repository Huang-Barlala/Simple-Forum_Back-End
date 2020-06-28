package com.barlala.forum.controller;

import com.barlala.forum.entity.Reply;
import com.barlala.forum.service.AuthenticationService;
import com.barlala.forum.service.ReplyService;
import com.barlala.forum.service.ResultJson;
import com.barlala.forum.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Barlala
 * @version 1.0
 * @date 2020/6/23 下午8:32
 */
@RestController
@Validated
public class ReplyController {
    private final ReplyService replyService;
    private final AuthenticationService authenticationService;
    private final TopicService topicService;


    @Autowired
    public ReplyController(ReplyService replyService, AuthenticationService authenticationService, TopicService topicService) {
        this.replyService = replyService;
        this.authenticationService = authenticationService;
        this.topicService = topicService;
    }

    @PostMapping(value = "/addReply")
    public ResultJson<?> addReply(@CookieValue(value = "jwt") String jwt,
                                  @RequestParam(value = "topicId") int topicId,
                                  @NotBlank(message = "回复内容不能为空") @RequestParam(value = "content") String content) {
        int userId = authenticationService.verifyToken(jwt);
        if (userId == -1) {
            return new ResultJson<>(HttpStatus.BAD_REQUEST, "未登录");
        }
        if (!topicService.isTopicExist(topicId)) {
            return new ResultJson<>(HttpStatus.BAD_REQUEST, "主题不存在");
        }
        Reply reply = new Reply();
        reply.setTopicid(topicId);
        reply.setUserid(userId);
        reply.setContent(content);
        Date now = new Date();
        reply.setCreatetime(now);
        reply.setModifytime(now);
        int serial = replyService.getNextSerial(topicId);
        reply.setSerial(serial);
        reply.setAuthor(authenticationService.getUsername(jwt));
        return replyService.addReply(reply);
    }

    @GetMapping(value = "/getReply")
    public ResultJson<?> getReply(@RequestParam(value = "topicId") int topicId,
                                  @RequestParam(value = "page") int page) {
        if (!topicService.isTopicExist(topicId)) {
            return new ResultJson<>(HttpStatus.BAD_REQUEST, "主题不存在");
        }
        long replyNum = replyService.replyNum(topicId);
        List<Reply> replies = replyService.getReply(topicId, page);
        Map<String, Object> map = new HashMap<>();
        map.put("replyNum", replyNum);
        map.put("replyData", replies);
        return new ResultJson<>(HttpStatus.OK, map);
    }

    @GetMapping(value = "/replyPageNum")
    public ResultJson<?> replyPageNum(@RequestParam(value = "topicId") int topicId) {
        return new ResultJson<>(HttpStatus.OK, replyService.replyPages(topicId));
    }
}
