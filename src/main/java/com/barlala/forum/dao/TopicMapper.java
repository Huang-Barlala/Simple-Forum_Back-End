package com.barlala.forum.dao;

import com.barlala.forum.entity.Topic;
import com.barlala.forum.entity.TopicExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TopicMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Topic
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    long countByExample(TopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Topic
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    int deleteByExample(TopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Topic
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Topic
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    int insert(Topic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Topic
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    int insertSelective(Topic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Topic
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    List<Topic> selectByExampleWithBLOBs(TopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Topic
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    List<Topic> selectByExample(TopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Topic
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    Topic selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Topic
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    int updateByExampleSelective(@Param("record") Topic record, @Param("example") TopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Topic
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    int updateByExampleWithBLOBs(@Param("record") Topic record, @Param("example") TopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Topic
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    int updateByExample(@Param("record") Topic record, @Param("example") TopicExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Topic
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    int updateByPrimaryKeySelective(Topic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Topic
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    int updateByPrimaryKeyWithBLOBs(Topic record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Topic
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    int updateByPrimaryKey(Topic record);
}