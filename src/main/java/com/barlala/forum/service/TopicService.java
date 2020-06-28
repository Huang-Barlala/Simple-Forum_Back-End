package com.barlala.forum.service;

import com.barlala.forum.dao.TopicMapper;
import com.barlala.forum.entity.Topic;
import com.barlala.forum.entity.TopicExample;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    private final UserService userService;

    @Autowired
    public TopicService(TopicMapper topicMapper, UserService userService) {
        this.topicMapper = topicMapper;
        this.userService = userService;
    }

    public ResultJson<?> addTopic(Topic topic) {
        int success = topicMapper.insertSelective(topic);
        if (success == 1) {
            userService.updateTopicNum(topic.getUserid());
        }
        return new ResultJson<>(HttpStatus.OK, success == 1 ? "发布成功" : "发布失败");
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
}
