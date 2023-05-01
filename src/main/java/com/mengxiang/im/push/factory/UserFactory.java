package com.mengxiang.im.push.factory;

import com.mengxiang.im.push.bean.db.User;
import com.mengxiang.im.push.bean.db.UserFollow;
import com.mengxiang.im.push.utils.Hib;
import com.mengxiang.im.push.utils.TextUtil;
import org.hibernate.Session;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.UUID.*;

/**
 * 该类提供一些查询接口
 */
public class UserFactory {
    //根据token查找用户信息
    public static User findByToken(String token) {
        return Hib.query(session -> (User) session.createQuery("from User where token=:token")
                .setParameter("token", token)
                .uniqueResult());
    }

    // 通过Phone找到User
    public static User findByPhone(String phone) {
        return Hib.query(session -> (User) session
                .createQuery("from User where phone=:inPhone")
                .setParameter("inPhone", phone)
                .uniqueResult());
    }

    // 通过Name找到User
    public static User findByName(String name) {
        return Hib.query(session -> (User) session
                .createQuery("from User where name=:name")
                .setParameter("name", name)
                .uniqueResult());
    }

    // 通过Id找到User
    public static User findById(String id) {
        return Hib.query(session -> (User) session
                .createQuery("from User where id=:id")
                .setParameter("id", id)
                .uniqueResult());
    }

    //更新用户信息
    public static User update(User user) {
        return Hib.query(session -> {
            session.saveOrUpdate(user);
            return user;
        });
    }

    //当前用户绑定pushId
    public static User bindPushId(User user, String pushId) {
        if (TextUtil.isEmpty(pushId)) {
            return null;
        }
        //查询是否有其他账号绑定这个设备
        //如果有，取消绑定
        Hib.queryOnly(session -> {
            List<User> userList = session
                    .createQuery("from User where lower(pushId)=:pushId and id!=:userId")
                    .setParameter("pushId", pushId.toLowerCase())
                    .setParameter("userId", user.getId())
                    .list();
            for (User u : userList) {
                //更新为Null
                u.setPushId(null);
                session.saveOrUpdate(u);
            }
        });

        if (pushId.equalsIgnoreCase(user.getPushId())) {
            //如果之前已经绑定过了，那么不需要再绑定
            return user;
        } else {
            // 如果当前账户之前的设备Id，和需要绑定的不同
            // 那么需要单点登录，让之前的设备退出账户，
            // 给之前的设备推送一条退出消息
            if (TextUtil.isEmpty(user.getPushId())) {
                //TODO 推送退出消息
            }

            //更新设备Id
            user.setPushId(pushId);
            return update(user);
        }
    }

    //用户使用账户密码登陆
    public static User login(String account, String password) {
        final String accountStr = account.trim();

        final String passwordStr = encodePassword(password);
        User user = Hib.query(new Hib.Query<User>() {
            @Override
            public User query(Session session) {
                return (User) session.createQuery("from User where phone=:phone and password=:password")
                        .setParameter("phone", accountStr)
                        .setParameter("password", passwordStr)
                        .uniqueResult();
            }
        });

        if (user != null) {
            //用户部位空就进行登陆，并且更新token
            user = login(user);
        }
        return user;
    }

    //用户注册
    public static User registerUser(String account, String password, String name) {
        account = account.trim();
        password = encodePassword(password);
        User user = createUser(account, password, name);
        if (user != null) {
            user = login(user);
        }
        return user;
    }

    //用户注册逻辑
    private static User createUser(String account, String password, String name) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        user.setPhone(account);
        return Hib.query(session -> {
            session.save(user);
           return user;
        });
    }

    //用户登陆
    private static User login(User user) {
        //生成Token
        String token = randomUUID().toString();

        //Base64
        token = TextUtil.encodeBase64(token);
        user.setToken(token);
        return update(user);
    }

    //密码加密
    private static String encodePassword(String password) {
        password = password.trim();
        //加盐再Base64
        password = TextUtil.getMD5(password);
        return TextUtil.encodeBase64(password);
    }

    //获取当前用户的联系人
     public static List<User> getContacts(User self) {
        return Hib.query(session -> {
            //重新加载一次用户信息
            session.load(self, self.getId());
            //获取我关注的人
            Set<UserFollow> following = self.getFollowing();
            return following.stream()
                    .map(UserFollow::getTarget).collect(Collectors.toList());
        });
     }

    /**
     * 关注人操作
     * @param origin 发起者
     * @param target 被关注人
     * @param alias 备注名
     * @return 被关注人信息
     */
    public static User follow(User origin, User target, String alias) {
        //先去查看二者有没有相互关注，即在UserFollow表中有没有数据
        UserFollow follow = getUserFollow(origin, target);

        if(follow != null) {
            //二者已经相互关注了
            return follow.getTarget();
        }

        return Hib.query(session ->  {
            //懒加载，重新加载一次数据
            session.load(origin, origin.getId());
            session.load(target, target.getId());

            UserFollow originUserFollow = new UserFollow();

            originUserFollow.setOrigin(origin);
            originUserFollow.setTarget(target);
            originUserFollow.setAlias(alias);

            UserFollow targetFollow = new UserFollow();
            targetFollow.setOrigin(origin);
            targetFollow.setTarget(target);

            //保存至数据库
            session.save(originUserFollow);
            session.save(targetFollow);

            return target;


        });
    }


    /**
     * 查询两个人是否已经关注
     * @param origin
     * @param target
     * @return
     */
    public static UserFollow getUserFollow(User origin, User target) {
        return Hib.query(session ->
            (UserFollow) session.createQuery("from UserFollow where originId=:originId and targetId=:targetId")
                    .setParameter("originId", origin.getId())
                    .setParameter("targetId", target.getId())
                    .uniqueResult());
    }
}
