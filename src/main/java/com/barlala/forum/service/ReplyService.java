package com.barlala.forum.service;

import com.barlala.forum.dao.ReplyMapper;
import com.barlala.forum.entity.Reply;
import com.barlala.forum.entity.ReplyExample;
import com.barlala.forum.entity.TopicExample;
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

    @Autowired
    public ReplyService(ReplyMapper replyMapper) {
        this.replyMapper = replyMapper;
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
        return new ResultJson<>(HttpStatus.OK, success == 1 ? "回复成功" : "回复失败");
    }

    public List<Reply> getReply(int topicId, int page) {
        ReplyExample example = new ReplyExample();
        example.setOrderByClause("Serial ASC");
        example.or().andTopicidEqualTo(topicId);
        PageHelper.startPage(page, 20);
        return replyMapper.selectByExampleWithBLOBs(example);
    }

    public boolean isPageOutOfRange(int topicId, int page) {
        ReplyExample example = new ReplyExample();
        example.or().andTopicidEqualTo(topicId);
        long sum = replyMapper.countByExample(example);
        long pages = sum / 20 + (sum % 20 == 0 ? 0 : 1);
        return page > pages;
    }
}
