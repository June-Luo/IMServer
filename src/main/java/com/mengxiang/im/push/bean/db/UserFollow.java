package com.mengxiang.im.push.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户关系的model
 * 用于用户直接进行好友关系的实现
 *
 * 用户和用户之间通过一个中间表进行联系
 */
@Entity
@Table(name = "TB_USER_FOLLOW")
@SuppressWarnings("all")
public class UserFollow {

    //主键
    @Id
    @PrimaryKeyJoinColumn
    //主键存储为uuid
    @GeneratedValue(generator = "uuid")
    //把uuid的生成器定义为uuid2，uuid2是常规的UUID toString
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    //不允许更改，不允许为null
    @Column(updatable =  false, nullable = false)
    private String id;

    //关注的发起人(一个人关注多个人)
    //一个User -> UserFollow
    //optional不可选，必须存储，一条关注记录一定要有一个"你"
    @ManyToOne(optional = false)
    //定义关联表字段名为originId，对应User.id，定义的是数据库中存储的字段
    @JoinColumn(name = "originId")
    private User origin;
    //这个列提取到Model中，不允许有、为空、更新、插入等
    @Column(nullable = false, updatable = false, insertable = false)
    private String originId;

    //关注的目标(多对一)
    //可以被很多人关注，每一次关注都是一条记录
    //多个UserFollow对应一个User的情况
    @ManyToOne(optional = false)
    //定义关联表字段名为targetId，对应User.id
    @JoinColumn(name = "targetId")
    private User target;

    @Column(nullable = false, updatable = false, insertable = false)
    private String targetId;

    //对target的备注，可以为空
    @Column
    private String alias;

    //定义创建时间戳，在创建的时候就写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    //定义更新时间戳，在创建的时候就写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime udateAt = LocalDateTime.now();


    //关注的人列表
    //对应数据库表字段为UserFollow.originId
    @JoinColumn(name = "originId")
    //定义为懒加载，默认加载User信息的时候，并不查询这个集合
    @LazyCollection(LazyCollectionOption.EXTRA)
    //一对多，一个用户可以有很多关主任
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserFollow> myFollowUser = new HashSet<>();


    //关注我的人
    @JoinColumn(name = "targetId")
    //定义为懒加载，默认加载User信息的时候，并不查询这个集合
    @LazyCollection(LazyCollectionOption.EXTRA)
    //一对多，一个用户可以被很多人关注
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<UserFollow> followers = new HashSet<>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getOrigin() {
        return origin;
    }

    public void setOrigin(User origin) {
        this.origin = origin;
    }

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUdateAt() {
        return udateAt;
    }

    public void setUdateAt(LocalDateTime udateAt) {
        this.udateAt = udateAt;
    }

    public Set<UserFollow> getMyFollowUser() {
        return myFollowUser;
    }

    public void setMyFollowUser(Set<UserFollow> myFollowUser) {
        this.myFollowUser = myFollowUser;
    }

    public Set<UserFollow> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<UserFollow> followers) {
        this.followers = followers;
    }
}
