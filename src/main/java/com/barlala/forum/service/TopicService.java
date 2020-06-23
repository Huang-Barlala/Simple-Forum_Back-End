package com.barlala.forum.service;

import com.barlala.forum.dao.TopicMapper;
import com.barlala.forum.entity.Topic;
import com.barlala.forum.entity.TopicExample;
import com.github.pagehelper.PageHelper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Barlala
 * @version 1.0
 * @date 2020/6/23 下午5:41
 */
@Service
public class TopicService {
    private String[] clause = new String[]{"CreateTime ASC", "CreateTime DESC", "ModifyTime ASC",
            "ModifyTime DESC", "ReplyTime ASC", "ReplyTime DESC"};
    private final TopicMapper topicMapper;

    public TopicService(TopicMapper topicMapper) {
        this.topicMapper = topicMapper;
    }

    public ResultJson<?> addTopic(Topic topic) {
        int success = topicMapper.insertSelective(topic);
        return new ResultJson<>(HttpStatus.OK, success == 1 ? "发布成功" : "发布失败");
    }

    public List<Topic> getTopicsList(int page, Integer order) {
        if (order == null) {
            order = 0;
        } else if (order < 0 || order >= clause.length) {
            order = 0;
        }
        TopicExample example = new TopicExample();
        example.setOrderByClause(clause[order]);
        PageHelper.startPage(page, 20);
        return topicMapper.selectByExampleWithBLOBs(example);
    }

    /**
     * 页数是否超出范围
     *
     * @param page 页数
     * @return true 超出范围
     */
    public boolean isPageOutOfRange(int page) {
        long sum = topicMapper.countByExample(new TopicExample());
        long pages = sum / 20 + (sum % 20 == 0 ? 0 : 1);
        return page > pages;
    }

    public boolean isTopicExist(int topicId) {
        Topic topic = topicMapper.selectByPrimaryKey(topicId);
        return topic != null;
    }
}
