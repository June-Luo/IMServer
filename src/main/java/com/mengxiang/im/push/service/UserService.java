package com.mengxiang.im.push.service;

import com.mengxiang.im.push.bean.api.base.ResponseModel;
import com.mengxiang.im.push.bean.api.user.UpdateInfoModel;
import com.mengxiang.im.push.bean.card.UserCard;
import com.mengxiang.im.push.bean.db.User;
import com.mengxiang.im.push.factory.UserFactory;
import com.oracle.deploy.update.UpdateInfo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 用户信息处理的Service
 */
@Path("/user")
public class UserService extends BaseService{
    //用户信息修改接口，返回自己的个人信息

    @PUT
    //@Path("") //127.0.0.1/api/user 不需要写，就是当前目录
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<UserCard> update(UpdateInfoModel model) {
        //进行检查
        if (!UpdateInfoModel.check(model)) {
            return ResponseModel.buildParameterError();
        }


        User self = getSelf();
        //更新信息
        self = model.updateToUser(self);
        self = UserFactory.update(self);

        UserCard userCard = new UserCard(self,true);

        return ResponseModel.buildOk(userCard);
    }

    //获取联系人列表

    @GET
    @Path("/contact")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<List<UserCard>> contacts() {
        User self = getSelf();
        //获取我的联系人
        List<User> contacts = UserFactory.getContacts(self);
        //user-->userCard
        List<UserCard> userCards = contacts.stream().map(user -> new UserCard(user, true)).collect(Collectors.toList());
        return ResponseModel.buildOk(userCards);
    }

    @PUT
    @Path("/follow/{followId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<UserCard> follow(@PathParam("followId") String followId) {
        User self = getSelf();

        if (followId.equalsIgnoreCase(self.getId())) {
            //自己不能关注自己
            return ResponseModel.buildParameterError();
        }

        //我关注的人
        User followUser = UserFactory.findById(followId);
        if (followUser == null) {
            return ResponseModel.buildNotFoundUserError(null);
        }

        followUser = UserFactory.follow(self, followUser, null);

        if (followUser == null) {
            //服务器异常
            return ResponseModel.buildServiceError();
        }

        //TODO:通知我关注的人我关注了它


        //返回关注人信息
        return ResponseModel.buildOk(new UserCard(followUser, true));
    }
}
