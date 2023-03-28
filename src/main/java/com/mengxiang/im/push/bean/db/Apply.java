package com.mengxiang.im.push.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 申请记录表
 */
@Entity
@Table(name = "TB_Apply")
@SuppressWarnings("all")
public class Apply {

    public static final int TYPE_ADD_USER = 1;//添加好友
    public static final int TYPE_ADD_GROUP = 1;//添加群

    //主键
    @Id
    @PrimaryKeyJoinColumn
    //主键存储为uuid
    @GeneratedValue(generator = "uuid")
    //把uuid的生成器定义为uuid2，uuid2是常规的UUID toString
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    //不允许更改，不允许为null
    @Column(updatable = false, nullable = false)
    private String id;

    //描述信息，申请信息中的描述
    @Column(nullable = false)
    private String description;

    //附件：可以福袋图片地址或者其他
    @Column(columnDefinition = "TEXT")
    private String attach;

    //当前申请的类型
    @Column(nullable = false)
    private int type;

    //目标ID，不建立主外键关系
    //type - > TYPE_ADD_USER: User.id
    //type -> TYPE_ADD_GROUP: Group.id
    @Column(nullable = false)
    private String targetId;

    //定义创建时间戳，在创建的时候就写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    //定义更新时间戳，在创建的时候就写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();

    //申请人，可以为空，为空时是系统信息
    // 一个人可以有多个申请
    @ManyToOne
    @JoinColumn(name = "applicantId")
    private User applicant;
    @Column(updatable = false, insertable = false)
    private String applicantId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public User getApplicant() {
        return applicant;
    }

    public void setApplicant(User applicant) {
        this.applicant = applicant;
    }

    public String getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(String applicantId) {
        this.applicantId = applicantId;
    }
}

