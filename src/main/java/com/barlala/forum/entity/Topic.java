package com.barlala.forum.entity;

import java.util.Date;

public class Topic {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column Topic.id
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column Topic.sectionId
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    private Integer sectionId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column Topic.userId
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column Topic.title
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    private String title;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column Topic.createTime
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column Topic.modifyTime
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    private Date modifyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column Topic.replyTime
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    private Date replyTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column Topic.author
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    private String author;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column Topic.authorAvatar
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    private String authorAvatar;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column Topic.content
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    private String content;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Topic
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    public Topic(Integer id, Integer sectionId, Integer userId, String title, Date createTime, Date modifyTime, Date replyTime, String author, String authorAvatar) {
        this.id = id;
        this.sectionId = sectionId;
        this.userId = userId;
        this.title = title;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
        this.replyTime = replyTime;
        this.author = author;
        this.authorAvatar = authorAvatar;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Topic
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    public Topic(Integer id, Integer sectionId, Integer userId, String title, Date createTime, Date modifyTime, Date replyTime, String author, String authorAvatar, String content) {
        this.id = id;
        this.sectionId = sectionId;
        this.userId = userId;
        this.title = title;
        this.createTime = createTime;
        this.modifyTime = modifyTime;
        this.replyTime = replyTime;
        this.author = author;
        this.authorAvatar = authorAvatar;
        this.content = content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table Topic
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    public Topic() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column Topic.id
     *
     * @return the value of Topic.id
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column Topic.id
     *
     * @param id the value for Topic.id
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column Topic.sectionId
     *
     * @return the value of Topic.sectionId
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    public Integer getSectionId() {
        return sectionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column Topic.sectionId
     *
     * @param sectionId the value for Topic.sectionId
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    public void setSectionId(Integer sectionId) {
        this.sectionId = sectionId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column Topic.userId
     *
     * @return the value of Topic.userId
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column Topic.userId
     *
     * @param userId the value for Topic.userId
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column Topic.title
     *
     * @return the value of Topic.title
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column Topic.title
     *
     * @param title the value for Topic.title
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column Topic.createTime
     *
     * @return the value of Topic.createTime
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column Topic.createTime
     *
     * @param createTime the value for Topic.createTime
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column Topic.modifyTime
     *
     * @return the value of Topic.modifyTime
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column Topic.modifyTime
     *
     * @param modifyTime the value for Topic.modifyTime
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column Topic.replyTime
     *
     * @return the value of Topic.replyTime
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    public Date getReplyTime() {
        return replyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column Topic.replyTime
     *
     * @param replyTime the value for Topic.replyTime
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    public void setReplyTime(Date replyTime) {
        this.replyTime = replyTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column Topic.author
     *
     * @return the value of Topic.author
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    public String getAuthor() {
        return author;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column Topic.author
     *
     * @param author the value for Topic.author
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column Topic.authorAvatar
     *
     * @return the value of Topic.authorAvatar
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    public String getAuthorAvatar() {
        return authorAvatar;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column Topic.authorAvatar
     *
     * @param authorAvatar the value for Topic.authorAvatar
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    public void setAuthorAvatar(String authorAvatar) {
        this.authorAvatar = authorAvatar == null ? null : authorAvatar.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column Topic.content
     *
     * @return the value of Topic.content
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    public String getContent() {
        return content;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column Topic.content
     *
     * @param content the value for Topic.content
     *
     * @mbg.generated Tue Jul 07 21:10:04 CST 2020
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}