package com.mengxiang.im.push.bean.db;

import com.mengxiang.im.push.bean.api.messsage.MessageCreateModel;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_MESSAGE")
@SuppressWarnings("all")
public class Message {

    //定义消息类型
    public static final int TYPE_STR = 1;//字符串
    public static final int TYPE_PIC = 2;//图片
    public static final int TYPE_FILE = 3;//文件
    public static final int TYPE_AUDIO = 4;//语音

    // 定义发送的类别，发送给人还是发送给群
    public static final int RECEIVER_TYPE_NONE = 1;//人
    public static final int RECEIVER_TYPE_GROUP = 2;//群

    //主键
    @Id
    @PrimaryKeyJoinColumn
//    //主键存储为uuid(messageId由客户端代码生成)，避免服务器和客户端的复杂映射
//    @GeneratedValue(generator = "uuid")
    //把uuid的生成器定义为uuid2，uuid2是常规的UUID toString
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    //不允许更改，不允许为null
    @Column(updatable =  false, nullable = false)
    private String id;

    //内容不允许为空，默认类型为TEXT
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    //附件
    @Column
    private String attach;

    //消息类型
    @Column(nullable = false)
    private int type;

    //发送者
    @ManyToOne(optional = false)
    @JoinColumn(name = "senderId")
    //多个消息对应一个发送者(一个人可以发送很多消息)
    private User sender;

    //该字段为了对应sender的数据库字段的senderId，不允许手动更新或者插入
    @Column(nullable = false, updatable = false, insertable = false)
    private String senderId;

    //接受者
    //多个消息对应一个接受者
    @ManyToOne
    @JoinColumn(name = "receiverId")
    private User receiver;

    @Column(nullable = false, updatable = false, insertable = false)
    private String receiverId;

    //定义创建时间戳，在创建的时候就写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    //定义更新时间戳，在创建的时候就写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();

    // 一个群可以接受多个消息
    @ManyToOne
    @JoinColumn(name = "groupId")
    private Group group;

    @Column(updatable = false, insertable = false)
    private String groupId;

    public Message() {

    }

    // 发送给朋友的构造函数
    public Message(User sender, User receiver, MessageCreateModel model) {
        this.id = model.getId();
        this.content = model.getContent();
        this.attach = model.getAttach();
        this.type = model.getType();

        this.sender = sender;
        this.receiver = receiver;
    }

    // 发送给群的构造函数
    public Message(User sender, Group group, MessageCreateModel model) {
        this.id = model.getId();
        this.content = model.getContent();
        this.attach = model.getAttach();
        this.type = model.getType();

        this.sender = sender;
        this.group = group;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
