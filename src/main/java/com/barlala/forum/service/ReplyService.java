package com.barlala.forum.service;

import com.barlala.forum.dao.ReplyMapper;
import com.barlala.forum.entity.Reply;
import com.barlala.forum.entity.ReplyExample;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Barlala
 * @version 1.0
 * @date 2020/6/23 下午8:32
 */
@Service
public class ReplyService {
    private final ReplyMapper replyMapper;
    private final TopicService topicService;

    @Autowired
    public ReplyService(ReplyMapper replyMapper, TopicService topicService) {
        this.replyMapper = replyMapper;
        this.topicService = topicService;
    }

    public int getNextSerial(int topicId) {
        ReplyExample example = new ReplyExample();
        example.setOrderByClause("Serial DESC");
        example.or().andTopicidEqualTo(topicId);
        PageHelper.offsetPage(0, 1);
        List<Reply> replies = replyMapper.selectByExample(example);
        if (!replies.isEmpty()) {
            return replies.get(0).getSerial() + 1;
        }
        return 1;
    }


    public ResultJson<?> addReply(Reply reply) {
        int success = replyMapper.insertSelective(reply);
        if (success == 1) {
            topicService.changeReplyTime(reply.getTopicid(), reply.getCreatetime());
        }
        return new ResultJson<>(HttpStatus.OK, success == 1 ? "回复成功" : "回复失败");
    }

    public List<Reply> getReply(int topicId, int page) {
        ReplyExample example = new ReplyExample();
        example.setOrderByClause("Serial ASC");
        example.or().andTopicidEqualTo(topicId);
        PageHelper.startPage(page, 20);
        return replyMapper.selectByExampleWithBLOBs(example);
    }

    public List<Reply> getReplyByOffset(int topicId, int offset) {
        ReplyExample example = new ReplyExample();
        example.setOrderByClause("Serial ASC");
        example.or().andTopicidEqualTo(topicId);
        PageHelper.offsetPage(offset, offset + 20);
        return replyMapper.selectByExampleWithBLOBs(example);
    }

    public long replyNum(int topicId) {
        ReplyExample example = new ReplyExample();
        example.or().andTopicidEqualTo(topicId);
        return replyMapper.countByExample(example);
    }

    public long replyPages(int topicId) {
        long replyNum = replyNum(topicId);
        return replyNum / 20 + (replyNum % 20 == 0 ? 0 : 1);
    }
}
