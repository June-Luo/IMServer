package com.mengxiang.im.push.factory;

import com.mengxiang.im.push.bean.api.messsage.MessageCreateModel;
import com.mengxiang.im.push.bean.db.Group;
import com.mengxiang.im.push.bean.db.Message;
import com.mengxiang.im.push.bean.db.User;
import com.mengxiang.im.push.utils.Hib;

/**
 * 消息数据处理的类
 */
public class MessageFactory {
    public  static Message findById(String id){
        // 查询某个消息
        return Hib.query(session -> session.get(Message.class, id));
    }

    // 添加一条普通消息
    public static Message add(User sender, User receiver, MessageCreateModel model) {
        Message message = new Message(sender, receiver, model);
        return save(message);
    }

    // 添加一条群消息
    public static Message add(User sender, Group group, MessageCreateModel model) {
        Message message = new Message(sender, group, model);
        return save(message);
    }

    private static Message save(Message message) {
        return  Hib.query(session -> {
            session.save(message);
            //写入数据库
            session.flush();
            //查出
            session.refresh(message);
            return message;
        });
    }

}
