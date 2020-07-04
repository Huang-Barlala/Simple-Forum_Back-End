package com.barlala.forum.service;

import com.barlala.forum.dao.TopicMapper;
import com.barlala.forum.entity.Topic;
import com.barlala.forum.entity.TopicExample;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Barlala
 * @version 1.0
 * @date 2020/6/23 下午5:41
 */
@Service
public class TopicService {
    private final String[] clause = new String[]{"CreateTime ASC", "CreateTime DESC", "ModifyTime ASC",
            "ModifyTime DESC", "ReplyTime ASC", "ReplyTime DESC"};
    private final TopicMapper topicMapper;

    @Autowired
    public TopicService(TopicMapper topicMapper) {
        this.topicMapper = topicMapper;
    }

    public boolean addTopic(Topic topic) {
        int success = topicMapper.insertSelective(topic);
        return success == 1;
    }

    public List<Topic> getTopicsList(int page, Integer order, int sectionId) {
        if (order == null) {
            order = 0;
        } else if (order < 0 || order >= clause.length) {
            order = 0;
        }
        TopicExample example = new TopicExample();
        example.setOrderByClause(clause[order]);
        example.or().andSectionidEqualTo(sectionId);
        PageHelper.startPage(page, 20);
        return topicMapper.selectByExampleWithBLOBs(example);
    }

    /**
     * 页数是否超出范围
     *
     * @param page 页数
     * @return true 超出范围
     */
    public boolean isPageOutOfRange(int sectionId, int page) {
        return page > topicPages(sectionId);
    }

    public boolean isTopicExist(int topicId) {
        Topic topic = topicMapper.selectByPrimaryKey(topicId);
        return topic != null;
    }

    public long topicPages(int sectionId) {
        TopicExample example = new TopicExample();
        example.or().andSectionidEqualTo(sectionId);
        long sum = topicMapper.countByExample(example);
        return sum / 20 + (sum % 20 == 0 ? 0 : 1);
    }

    public Topic getTopic(int topicId) {
        return topicMapper.selectByPrimaryKey(topicId);
    }

    public void changeReplyTime(int topicId, Date time) {
        Topic topic = new Topic();
        topic.setId(topicId);
        topic.setReplytime(time);
        topicMapper.updateByPrimaryKeySelective(topic);
    }

    public void updateAvatar(int userId, String authorAvatar) {
        TopicExample example = new TopicExample();
        example.or().andUseridEqualTo(userId);
        Topic topic = new Topic();
        topic.setAuthoravatar(authorAvatar);
        topicMapper.updateByExampleSelective(topic, example);
    }

    public void updateAuthor(int userId, String author) {
        TopicExample example = new TopicExample();
        example.or().andUseridEqualTo(userId);
        Topic topic = new Topic();
        topic.setAuthor(author);
        topicMapper.updateByExampleSelective(topic, example);
    }

    public List<Topic> getUserTopic(int userId) {
        TopicExample example = new TopicExample();
        example.or().andUseridEqualTo(userId);
        return topicMapper.selectByExample(example);
    }

    public boolean deleteTopic(int topicId) {
        int result = topicMapper.deleteByPrimaryKey(topicId);
        return result == 1;
    }

    public boolean updateTopic(Topic topic) {
        int result = topicMapper.updateByPrimaryKeySelective(topic);
        return result == 1;
    }
}
