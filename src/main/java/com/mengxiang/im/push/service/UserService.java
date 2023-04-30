package com.mengxiang.im.push.service;

import com.mengxiang.im.push.bean.api.base.ResponseModel;
import com.mengxiang.im.push.bean.api.user.UpdateInfoModel;
import com.mengxiang.im.push.bean.card.UserCard;
import com.mengxiang.im.push.bean.db.User;
import com.mengxiang.im.push.factory.UserFactory;
import com.oracle.deploy.update.UpdateInfo;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
}
