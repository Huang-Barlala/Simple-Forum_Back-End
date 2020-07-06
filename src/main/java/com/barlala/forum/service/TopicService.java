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
    private final String[] clause = new String[]{};
    private final TopicMapper topicMapper;

    @Autowired
    public TopicService(TopicMapper topicMapper) {
        this.topicMapper = topicMapper;
    }

    public boolean addTopic(Topic topic) {
        int success = topicMapper.insertSelective(topic);
        return success == 1;
    }

    public List<Topic> getTopicsList(int page, String order, int sectionId) {
        TopicExample example = orderExample(order, false);
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

    public long getAllTopicPageNum(String search) {
        TopicExample example = new TopicExample();
        if (search != null && !search.equals("")) {
            example.or().andAuthorLike("%" + search + "%");
            example.or().andTitleLike("%" + search + "%");
            try {
                int id = Integer.parseInt(search);
                example.or().andIdEqualTo(id);
                example.or().andSectionidEqualTo(id);
                example.or().andUseridEqualTo(id);
            } catch (NumberFormatException ignore) {
            }
        }
        long num = topicMapper.countByExample(example);
        return num / 20 + (num % 20 == 0 ? 0 : 1);
    }

    public List<Topic> getAllTopicList(int page, String order, boolean desc, String search) {
        TopicExample example = orderExample(order, desc);
        if (search != null && !search.equals("")) {
            example.or().andAuthorLike("%" + search + "%");
            example.or().andTitleLike("%" + search + "%");
            try {
                int id = Integer.parseInt(search);
                example.or().andIdEqualTo(id);
                example.or().andSectionidEqualTo(id);
                example.or().andUseridEqualTo(id);
            } catch (NumberFormatException ignore) {
            }
        }
        PageHelper.startPage(page, 20);

        return topicMapper.selectByExample(example);
    }


    private TopicExample orderExample(String orderBy, boolean desc) {
        TopicExample example = new TopicExample();
        if (desc) {
            switch (orderBy) {
                case "id":
                    example.setOrderByClause(MyClause.Id_DESC.toString());
                    break;
                case "sectionid":
                    example.setOrderByClause(MyClause.SectionId_DESC.toString());
                    break;
                case "userid":
                    example.setOrderByClause(MyClause.UserId_DESC.toString());
                    break;
                case "author":
                    example.setOrderByClause(MyClause.Author_DESC.toString());
                    break;
                case "title":
                    example.setOrderByClause(MyClause.Title_DESC.toString());
                    break;
                case "createtime":
                    example.setOrderByClause(MyClause.CreateTime_DESC.toString());
                    break;
                case "modifytime":
                    example.setOrderByClause(MyClause.ModifyTime_DESC.toString());
                    break;
                case "replytime":
                    example.setOrderByClause(MyClause.ReplyTime_DESC.toString());
                    break;
                default:
            }
        } else {
            switch (orderBy) {
                case "id":
                    example.setOrderByClause(MyClause.Id_ASC.toString());
                    break;
                case "sectionid":
                    example.setOrderByClause(MyClause.SectionId_ASC.toString());
                    break;
                case "userid":
                    example.setOrderByClause(MyClause.UserId_ASC.toString());
                    break;
                case "author":
                    example.setOrderByClause(MyClause.Author_ASC.toString());
                    break;
                case "title":
                    example.setOrderByClause(MyClause.Title_ASC.toString());
                    break;
                case "createtime":
                    example.setOrderByClause(MyClause.CreateTime_ASC.toString());
                    break;
                case "modifytime":
                    example.setOrderByClause(MyClause.ModifyTime_ASC.toString());
                    break;
                case "replytime":
                    example.setOrderByClause(MyClause.ReplyTime_ASC.toString());
                    break;
                default:
            }
        }
        return example;
    }

    enum MyClause {
        //Topic排序用Clause
        Id_ASC("Id ASC"), Id_DESC("Id DESC"),
        SectionId_ASC("SectionId ASC"), SectionId_DESC("SectionId DESC"),
        UserId_ASC("UserId ASC"), UserId_DESC("UserId DESC"),
        Title_ASC("Title ASC"), Title_DESC("Title DESC"),
        CreateTime_ASC("CreateTime ASC"), CreateTime_DESC("CreateTime DESC"),
        ModifyTime_ASC("ModifyTime ASC"), ModifyTime_DESC("ModifyTime DESC"),
        ReplyTime_ASC("ReplyTime ASC"), ReplyTime_DESC("ReplyTime DESC"),
        Author_ASC("author ASC"), Author_DESC("author DESC");
        private final String info;

        MyClause(String s) {
            this.info = s;
        }

        @Override
        public String toString() {
            return info;
        }
    }
}
