package com.barlala.forum.entity;

import java.util.Date;

public class Reply {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column Reply.Id
     *
     * @mbg.generated Sun Jun 21 16:58:02 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column Reply.TopicId
     *
     * @mbg.generated Sun Jun 21 16:58:02 CST 2020
     */
    private Integer topicid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column Reply.UserId
     *
     * @mbg.generated Sun Jun 21 16:58:02 CST 2020
     */
    private Integer userid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column Reply.CreateTime
     *
     * @mbg.generated Sun Jun 21 16:58:02 CST 2020
     */
    private Date createtime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column Reply.ModifyTime
     *
     * @mbg.generated Sun Jun 21 16:58:02 CST 2020
     */
    private Date modifytime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column Reply.Serial
     *
     * @mbg.generated Sun Jun 21 16:58:02 CST 2020
     */
    private Integer serial;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column Reply.Content
     *
     * @mbg.generated Sun Jun 21 16:58:02 CST 2020
     */
    private String content;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Reply
     *
     * @mbg.generated Sun Jun 21 16:58:02 CST 2020
     */
    public Reply(Integer id, Integer topicid, Integer userid, Date createtime, Date modifytime, Integer serial) {
        this.id = id;
        this.topicid = topicid;
        this.userid = userid;
        this.createtime = createtime;
        this.modifytime = modifytime;
        this.serial = serial;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Reply
     *
     * @mbg.generated Sun Jun 21 16:58:02 CST 2020
     */
    public Reply(Integer id, Integer topicid, Integer userid, Date createtime, Date modifytime, Integer serial, String content) {
        this.id = id;
        this.topicid = topicid;
        this.userid = userid;
        this.createtime = createtime;
        this.modifytime = modifytime;
        this.serial = serial;
        this.content = content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Reply
     *
     * @mbg.generated Sun Jun 21 16:58:02 CST 2020
     */
    public Reply() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column Reply.Id
     *
     * @return the value of Reply.Id
     *
     * @mbg.generated Sun Jun 21 16:58:02 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column Reply.Id
     *
     * @param id the value for Reply.Id
     *
     * @mbg.generated Sun Jun 21 16:58:02 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column Reply.TopicId
     *
     * @return the value of Reply.TopicId
     *
     * @mbg.generated Sun Jun 21 16:58:02 CST 2020
     */
    public Integer getTopicid() {
        return topicid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column Reply.TopicId
     *
     * @param topicid the value for Reply.TopicId
     *
     * @mbg.generated Sun Jun 21 16:58:02 CST 2020
     */
    public void setTopicid(Integer topicid) {
        this.topicid = topicid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column Reply.UserId
     *
     * @return the value of Reply.UserId
     *
     * @mbg.generated Sun Jun 21 16:58:02 CST 2020
     */
    public Integer getUserid() {
        return userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column Reply.UserId
     *
     * @param userid the value for Reply.UserId
     *
     * @mbg.generated Sun Jun 21 16:58:02 CST 2020
     */
    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column Reply.CreateTime
     *
     * @return the value of Reply.CreateTime
     *
     * @mbg.generated Sun Jun 21 16:58:02 CST 2020
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column Reply.CreateTime
     *
     * @param createtime the value for Reply.CreateTime
     *
     * @mbg.generated Sun Jun 21 16:58:02 CST 2020
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column Reply.ModifyTime
     *
     * @return the value of Reply.ModifyTime
     *
     * @mbg.generated Sun Jun 21 16:58:02 CST 2020
     */
    public Date getModifytime() {
        return modifytime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column Reply.ModifyTime
     *
     * @param modifytime the value for Reply.ModifyTime
     *
     * @mbg.generated Sun Jun 21 16:58:02 CST 2020
     */
    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column Reply.Serial
     *
     * @return the value of Reply.Serial
     *
     * @mbg.generated Sun Jun 21 16:58:02 CST 2020
     */
    public Integer getSerial() {
        return serial;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column Reply.Serial
     *
     * @param serial the value for Reply.Serial
     *
     * @mbg.generated Sun Jun 21 16:58:02 CST 2020
     */
    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column Reply.Content
     *
     * @return the value of Reply.Content
     *
     * @mbg.generated Sun Jun 21 16:58:02 CST 2020
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column Reply.Content
     *
     * @param content the value for Reply.Content
     *
     * @mbg.generated Sun Jun 21 16:58:02 CST 2020
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}