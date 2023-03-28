package com.mengxiang.im.push.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_PUSH_HISTORY")
@SuppressWarnings("all")
/**
 * 消息接收历史记录表
 */
public class PushHistory {

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

    //推送的具体实体存储都是JSON字符串
    //BLOB是大字段类型
    @Column(nullable = false, columnDefinition = "BLOB")
    @Lob
    private String entity;

    //推送的实体类型
    @Column(nullable = false)
    private int entityType;

    //一个接受者可以接收很多历史推送消息
    //加载一条推送消息的时候直接加载用户信息
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "receiverId")
    private User receiver;
    @Column(nullable = false, updatable = false, insertable = false)
    private String receiverId;

    //发送者可以为空
    //一个发送者可以接收很多历史推送消息
    //加载一条推送消息的时候直接加载用户信息
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "senderId")
    private User sender;
    @Column(updatable = false, insertable = false)
    private String senderId;

    //接收者当前状态下的推送设备ID
    //User.pushId可以为空
    @Column
    private String receiverPushId;

    //定义创建时间戳，在创建的时候就写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    //定义更新时间戳，在创建的时候就写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();

    //消息送达时间，可以为空
    @Column
    private LocalDateTime arrivalAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverPushId() {
        return receiverPushId;
    }

    public void setReceiverPushId(String receiverPushId) {
        this.receiverPushId = receiverPushId;
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

    public LocalDateTime getArrivalAt() {
        return arrivalAt;
    }

    public void setArrivalAt(LocalDateTime arrivalAt) {
        this.arrivalAt = arrivalAt;
    }
}
