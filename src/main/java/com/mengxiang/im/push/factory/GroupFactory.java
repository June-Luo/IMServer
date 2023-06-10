package com.mengxiang.im.push.factory;

import com.mengxiang.im.push.bean.db.Group;
import com.mengxiang.im.push.bean.db.GroupMember;
import com.mengxiang.im.push.bean.db.User;
import com.mengxiang.im.push.utils.Hib;

import java.util.Set;

public class GroupFactory {
    public static Group findById(String groupId) {
        // TODO 查询一个群
        return null;
    }

    public static Group findById(User user, String groupId) {
        // TODO 查询一个群, 同时该User必须为群的成员，否则返回null
        return null;
    }

    public static Set<GroupMember> getMembers(Group group) {
        // TODO 查询一个群的成员
        return null;
    }
}
