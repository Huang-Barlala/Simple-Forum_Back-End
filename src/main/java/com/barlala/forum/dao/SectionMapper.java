package com.barlala.forum.dao;

import com.barlala.forum.entity.Section;
import com.barlala.forum.entity.SectionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SectionMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Section
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    long countByExample(SectionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Section
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    int deleteByExample(SectionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Section
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Section
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    int insert(Section record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Section
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    int insertSelective(Section record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Section
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    List<Section> selectByExample(SectionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Section
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    Section selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Section
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    int updateByExampleSelective(@Param("record") Section record, @Param("example") SectionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Section
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    int updateByExample(@Param("record") Section record, @Param("example") SectionExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Section
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    int updateByPrimaryKeySelective(Section record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Section
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    int updateByPrimaryKey(Section record);
}