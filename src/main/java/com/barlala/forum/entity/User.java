package com.barlala.forum.entity;

import java.util.Date;

public class User {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column User.Id
     *
     * @mbg.generated Fri Jun 26 12:49:18 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column User.UserName
     *
     * @mbg.generated Fri Jun 26 12:49:18 CST 2020
     */
    private String username;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column User.Password
     *
     * @mbg.generated Fri Jun 26 12:49:18 CST 2020
     */
    private String password;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column User.Email
     *
     * @mbg.generated Fri Jun 26 12:49:18 CST 2020
     */
    private String email;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column User.CreateTime
     *
     * @mbg.generated Fri Jun 26 12:49:18 CST 2020
     */
    private Date createtime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column User.AvatarUrl
     *
     * @mbg.generated Fri Jun 26 12:49:18 CST 2020
     */
    private String avatarurl;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column User.TopicNum
     *
     * @mbg.generated Fri Jun 26 12:49:18 CST 2020
     */
    private Integer topicnum;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table User
     *
     * @mbg.generated Fri Jun 26 12:49:18 CST 2020
     */
    public User(Integer id, String username, String password, String email, Date createtime, String avatarurl, Integer topicnum) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.createtime = createtime;
        this.avatarurl = avatarurl;
        this.topicnum = topicnum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table User
     *
     * @mbg.generated Fri Jun 26 12:49:18 CST 2020
     */
    public User() {
        super();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column User.Id
     *
     * @return the value of User.Id
     *
     * @mbg.generated Fri Jun 26 12:49:18 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column User.Id
     *
     * @param id the value for User.Id
     *
     * @mbg.generated Fri Jun 26 12:49:18 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column User.UserName
     *
     * @return the value of User.UserName
     *
     * @mbg.generated Fri Jun 26 12:49:18 CST 2020
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column User.UserName
     *
     * @param username the value for User.UserName
     *
     * @mbg.generated Fri Jun 26 12:49:18 CST 2020
     */
    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column User.Password
     *
     * @return the value of User.Password
     *
     * @mbg.generated Fri Jun 26 12:49:18 CST 2020
     */
    public String getPassword() {
        return password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column User.Password
     *
     * @param password the value for User.Password
     *
     * @mbg.generated Fri Jun 26 12:49:18 CST 2020
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column User.Email
     *
     * @return the value of User.Email
     *
     * @mbg.generated Fri Jun 26 12:49:18 CST 2020
     */
    public String getEmail() {
        return email;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column User.Email
     *
     * @param email the value for User.Email
     *
     * @mbg.generated Fri Jun 26 12:49:18 CST 2020
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column User.CreateTime
     *
     * @return the value of User.CreateTime
     *
     * @mbg.generated Fri Jun 26 12:49:18 CST 2020
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column User.CreateTime
     *
     * @param createtime the value for User.CreateTime
     *
     * @mbg.generated Fri Jun 26 12:49:18 CST 2020
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column User.AvatarUrl
     *
     * @return the value of User.AvatarUrl
     *
     * @mbg.generated Fri Jun 26 12:49:18 CST 2020
     */
    public String getAvatarurl() {
        return avatarurl;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column User.AvatarUrl
     *
     * @param avatarurl the value for User.AvatarUrl
     *
     * @mbg.generated Fri Jun 26 12:49:18 CST 2020
     */
    public void setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl == null ? null : avatarurl.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column User.TopicNum
     *
     * @return the value of User.TopicNum
     *
     * @mbg.generated Fri Jun 26 12:49:18 CST 2020
     */
    public Integer getTopicnum() {
        return topicnum;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column User.TopicNum
     *
     * @param topicnum the value for User.TopicNum
     *
     * @mbg.generated Fri Jun 26 12:49:18 CST 2020
     */
    public void setTopicnum(Integer topicnum) {
        this.topicnum = topicnum;
    }
}