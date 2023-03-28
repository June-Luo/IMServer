package com.mengxiang.im.push.bean.db;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "TB_Group")
@SuppressWarnings("all")
public class GroupMember {

    public static final int PERMISSION_TYPE_NONE = 0;//默认普通成员
    public static final int PERMISSION_TYPE_ADMIN = 1;//管理员
    public static final int PERMISSION_TYPE_ADMIN_SU = 100;//群主

    public static final int NOTIFY_LEVEL_INVALID = -1;//默认不接受消息
    public static final int NOTIFY_LEVEL_NONE = 0;//默认接收级别
    public static final int NOTIFY_LEVEL_CLOSE = 1;//接收消息不提醒

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

    // 别名
    @Column
    private String alias;

    //消息通知级别
    @Column(nullable = false)
    private int notifyLevel = NOTIFY_LEVEL_NONE;

    //成员的权限类型
    @Column(nullable = false)
    private int PermissionType = PERMISSION_TYPE_NONE;

    //定义创建时间戳，在创建的时候就写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createAt = LocalDateTime.now();

    //定义更新时间戳，在创建的时候就写入
    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime updateAt = LocalDateTime.now();


    // 成员信息对应的用户信息
    @JoinColumn(name = "userId")
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private User user;
    @Column(nullable = false, updatable = false, insertable = false)
    private String userId;


    //成员信息对应的群信息
    @JoinColumn(name = "userId")
    @ManyToOne(optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Group group;
    @Column(nullable = false, updatable = false, insertable = false)
    private String groupId;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public int getNotifyLevel() {
        return notifyLevel;
    }

    public void setNotifyLevel(int notifyLevel) {
        this.notifyLevel = notifyLevel;
    }

    public int getPermissionType() {
        return PermissionType;
    }

    public void setPermissionType(int permissionType) {
        PermissionType = permissionType;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
