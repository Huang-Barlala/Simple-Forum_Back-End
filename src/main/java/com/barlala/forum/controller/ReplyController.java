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
    private final String ADMIN = "admin";


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
        reply.setTopicId(topicId);
        reply.setUserId(userId);
        content = content.replaceAll("src=\"api/image/", "src=\"/api/image/");
        reply.setContent(content);
        Date now = new Date();
        reply.setCreateTime(now);
        reply.setModifyTime(now);
        int serial = replyService.getNextSerial(topicId);
        reply.setSerial(serial);
        reply.setAuthor(authenticationService.getUsername(jwt));
        reply.setAuthorAvatar(authenticationService.getAvatar(jwt));
        if (replyService.addReply(reply)) {
            topicService.changeReplyTime(reply.getTopicId(), reply.getCreateTime());
            return ResultUtil.success();
        }
        return ResultUtil.error();
    }

    @GetMapping(value = "/api/reply")
    public Result<?> getReply(@RequestParam(value = "id") Integer id) {
        Reply reply = replyService.getReply(id);
        if (reply != null) {
            return ResultUtil.success(reply);
        }
        return ResultUtil.error();
    }

    @GetMapping(value = "/api/replyList")
    public Result<?> getReplyList(@RequestParam(value = "topicId") int topicId,
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


    @PutMapping("/api/reply")
    public Result<?> modifyTopic(@CookieValue String jwt,
                                 @RequestParam(value = "id") int id,
                                 @NotBlank(message = "内容不能为空") @RequestParam(value = "content") String content) {
        int userId = authenticationService.verifyToken(jwt);
        if (userId != replyService.getReply(id).getUserId() && !ADMIN.equals(authenticationService.getPermission(jwt))) {
            return ResultUtil.error("权限校验失败");
        }
        Reply reply = new Reply();
        reply.setId(id);
        reply.setContent(content);
        reply.setModifyTime(new Date());
        if (replyService.updateReply(reply)) {
            return ResultUtil.success();
        }
        return ResultUtil.error("删除失败");

    }

    @DeleteMapping("/api/reply")
    public Result<?> deleteReply(@CookieValue String jwt,
                                 @RequestParam(value = "id") int id) {
        int userId = authenticationService.verifyToken(jwt);
        if (userId != replyService.getReply(id).getUserId() && !ADMIN.equals(authenticationService.getPermission(jwt))) {
            return ResultUtil.error("权限校验失败");
        }
        if (replyService.deleteReply(id)) {
            return ResultUtil.success();
        }
        return ResultUtil.error("删除失败");
    }

    @GetMapping(value = "/api/replyPageNum")
    public Result<?> replyPageNum(@RequestParam(value = "topicId") int topicId) {
        return ResultUtil.success(replyService.replyPages(topicId));
    }

    @GetMapping("/api/allReply")
    public Result<?> getAllReply(@CookieValue String jwt,
                                 @RequestParam(value = "page", required = false) Integer page,
                                 @NotBlank @RequestParam(value = "order", required = false) String order,
                                 @RequestParam(value = "desc", required = false) Boolean desc,
                                 @RequestParam(value = "search", required = false) String search) {
        int userId = authenticationService.verifyToken(jwt);
        if (userId == -1 || !ADMIN.equals(authenticationService.getPermission(jwt))) {
            return ResultUtil.error("权限校验失败");
        }
        if (page == null) {
            page = 1;
        }
        if (page > replyService.getAllReplyPageNum(search)) {
            return ResultUtil.error("页数超出范围");
        }
        if (order == null) {
            order = "id";
        }
        if (desc == null) {
            desc = false;
        }
        return ResultUtil.success(replyService.getAllReplyList(page, order, desc, search));
    }

    @GetMapping("/api/allReplyPageNum")
    public Result<?> getAllReplyPageNum(@CookieValue String jwt,
                                        @RequestParam(value = "search", required = false) String search) {
        int userId = authenticationService.verifyToken(jwt);
        if (userId == -1 || !ADMIN.equals(authenticationService.getPermission(jwt))) {
            return ResultUtil.error("权限校验失败");
        }
        return ResultUtil.success(replyService.getAllReplyPageNum(search));
    }
}
