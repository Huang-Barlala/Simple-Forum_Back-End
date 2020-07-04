package com.barlala.forum.service;

import com.barlala.forum.dao.ReplyMapper;
import com.barlala.forum.entity.Reply;
import com.barlala.forum.entity.ReplyExample;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
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


    public boolean addReply(Reply reply) {
        int result = replyMapper.insertSelective(reply);
        return result == 1;
    }

    public List<Reply> getReplyList(int topicId, int page) {
        ReplyExample example = new ReplyExample();
        example.setOrderByClause("Serial ASC");
        example.or().andTopicidEqualTo(topicId);
        PageHelper.startPage(page, 20);
        return replyMapper.selectByExampleWithBLOBs(example);
    }

    public Reply getReply(int replyId) {
        return replyMapper.selectByPrimaryKey(replyId);
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

    public void updateAvatar(int userId, String authorAvatar) {
        ReplyExample example = new ReplyExample();
        example.or().andUseridEqualTo(userId);
        Reply reply = new Reply();
        reply.setAuthoravatar(authorAvatar);
        replyMapper.updateByExampleSelective(reply, example);
    }

    public void updateAuthor(int userId, String author) {
        ReplyExample example = new ReplyExample();
        example.or().andUseridEqualTo(userId);
        Reply reply = new Reply();
        reply.setAuthor(author);
        replyMapper.updateByExampleSelective(reply, example);
    }

    public void deleteReplyByTopic(int topicId) {
        ReplyExample example = new ReplyExample();
        example.or().andTopicidEqualTo(topicId);
        replyMapper.deleteByExample(example);
    }

    public boolean updateReply(Reply reply) {
        int result = replyMapper.updateByPrimaryKeySelective(reply);
        return result == 1;
    }
}
