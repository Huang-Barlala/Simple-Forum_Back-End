package com.barlala.forum.controller;

import com.barlala.forum.entity.Reply;
import com.barlala.forum.service.*;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping(value = "/api/reply")
    public Result<?> addReply(@CookieValue(value = "jwt") String jwt,
                              @RequestParam(value = "topicId") int topicId,
                              @NotBlank(message = "回复内容不能为空") @RequestParam(value = "content") String content) {
        int userId = authenticationService.verifyToken(jwt);
        if (userId == -1) {
            return ResultUtil.error("权限校验失败");
        }
        if (!topicService.isTopicExist(topicId)) {
            return ResultUtil.error("主题不存在");
        }
        Reply reply = new Reply();
        reply.setTopicid(topicId);
        reply.setUserid(userId);
        content = content.replaceAll("src=\"api/image/", "src=\"/api/image/");
        reply.setContent(content);
        Date now = new Date();
        reply.setCreatetime(now);
        reply.setModifytime(now);
        int serial = replyService.getNextSerial(topicId);
        reply.setSerial(serial);
        reply.setAuthor(authenticationService.getUsername(jwt));
        reply.setAuthoravatar(authenticationService.getAvatar(jwt));
        if (replyService.addReply(reply)) {
            topicService.changeReplyTime(reply.getTopicid(), reply.getCreatetime());
            return ResultUtil.success();
        }
        return ResultUtil.error();
    }

    @GetMapping(value = "/api/reply")
    public Result<?> getReply(@RequestParam(value = "topicId") int topicId,
                              @RequestParam(value = "page") int page) {
        if (!topicService.isTopicExist(topicId)) {
            return ResultUtil.error("主题不存在");
        }
        long replyNum = replyService.replyNum(topicId);
        List<Reply> replies = replyService.getReplyList(topicId, page);
        Map<String, Object> map = new HashMap<>();
        map.put("replyNum", replyNum);
        map.put("replyData", replies);
        return ResultUtil.success(map);
    }

    @GetMapping(value = "/api/replyPageNum")
    public Result<?> replyPageNum(@RequestParam(value = "topicId") int topicId) {
        return ResultUtil.success(replyService.replyPages(topicId));
    }

    @PutMapping("/api/reply")
    public Result<?> modifyTopic(@CookieValue String jwt,
                                 @RequestParam(value = "replyId") int replyId,
                                 @NotBlank(message = "内容不能为空") @RequestParam(value = "content") String content) {
        int userId = authenticationService.verifyToken(jwt);
        if (userId == -1) {
            return ResultUtil.error("权限校验失败");
        }
        boolean isUpdate = userId == replyService.getReply(replyId).getUserid();
        if (isUpdate) {
            Reply reply = new Reply();
            reply.setId(replyId);
            reply.setContent(content);
            reply.setModifytime(new Date());
            if (replyService.updateReply(reply)) {
                return ResultUtil.success();
            }
            return ResultUtil.error("修改失败");
        }
        return ResultUtil.error("无修改权限");
    }
}
