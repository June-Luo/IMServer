package com.mengxiang.im.push.bean.db;

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

    private User sender;

    private User receiver;

    //定义创建时间戳，在创建的时候就写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    //定义更新时间戳，在创建的时候就写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime udateAt = LocalDateTime.now();
}
