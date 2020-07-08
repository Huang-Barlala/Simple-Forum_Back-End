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
        example.or().andTopicIdEqualTo(topicId);
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
        example.or().andTopicIdEqualTo(topicId);
        PageHelper.startPage(page, 20);
        return replyMapper.selectByExampleWithBLOBs(example);
    }

    public Reply getReply(int replyId) {
        return replyMapper.selectByPrimaryKey(replyId);
    }


    public long replyNum(int topicId) {
        ReplyExample example = new ReplyExample();
        example.or().andTopicIdEqualTo(topicId);
        return replyMapper.countByExample(example);
    }

    public long replyPages(int topicId) {
        long replyNum = replyNum(topicId);
        return replyNum / 20 + (replyNum % 20 == 0 ? 0 : 1);
    }

    public void updateAvatar(int userId, String authorAvatar) {
        ReplyExample example = new ReplyExample();
        example.or().andUserIdEqualTo(userId);
        Reply reply = new Reply();
        reply.setAuthorAvatar(authorAvatar);
        replyMapper.updateByExampleSelective(reply, example);
    }

    public void updateAuthor(int userId, String author) {
        ReplyExample example = new ReplyExample();
        example.or().andUserIdEqualTo(userId);
        Reply reply = new Reply();
        reply.setAuthor(author);
        replyMapper.updateByExampleSelective(reply, example);
    }

    public void deleteReplyByTopic(int topicId) {
        ReplyExample example = new ReplyExample();
        example.or().andTopicIdEqualTo(topicId);
        replyMapper.deleteByExample(example);
    }

    public boolean updateReply(Reply reply) {
        int result = replyMapper.updateByPrimaryKeySelective(reply);
        return result == 1;
    }

    public long getAllReplyPageNum(String search) {
        ReplyExample example = new ReplyExample();
        if (search != null && !search.equals("")) {
            example.or().andAuthorLike("%" + search + "%");
            try {
                int id = Integer.parseInt(search);
                example.or().andIdEqualTo(id);
                example.or().andTopicIdEqualTo(id);
                example.or().andUserIdEqualTo(id);
            } catch (NumberFormatException ignore) {
            }
        }
        long num = replyMapper.countByExample(example);
        return num / 20 + (num % 20 == 0 ? 0 : 1);
    }

    public List<Reply> getAllReplyList(int page, String order, boolean desc, String search) {
        ReplyExample example = orderExample(order, desc);
        if (search != null && !search.equals("")) {
            example.or().andAuthorLike("%" + search + "%");
            try {
                int id = Integer.parseInt(search);
                example.or().andIdEqualTo(id);
                example.or().andTopicIdEqualTo(id);
                example.or().andUserIdEqualTo(id);
            } catch (NumberFormatException ignore) {
            }
        }
        PageHelper.startPage(page, 20);
        return replyMapper.selectByExample(example);
    }

    private ReplyExample orderExample(String orderBy, boolean desc) {
        ReplyExample example = new ReplyExample();
        StringBuilder clause = new StringBuilder(orderBy);
        if (desc) {
            clause.append(" DESC");
        } else {
            clause.append(" ASC");
        }
        example.setOrderByClause(clause.toString());
        return example;
    }

    public boolean deleteReply(int id) {
        int result = replyMapper.deleteByPrimaryKey(id);
        return result == 1;
    }
}
