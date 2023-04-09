package com.mengxiang.im.push.service;

import com.mengxiang.im.push.bean.api.account.RegisterModel;
import com.mengxiang.im.push.bean.db.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author lbj
 */
@Path("/account")
public class AccountService {

//    /**
//     *Get 请求实际地址(127.0.0.1/api/account/login)
//     */
//    @GET
//    @Path("/login")
//    public String get(){
//        return "get login";
//    }
//
//    //POST 127.0.0.1/api/account/login
//    @POST
//    @Path("/login")
//    // 返回的相应体为JSON
//    @Produces(MediaType.APPLICATION_JSON)
//    public User post() {
//        User user = new User();
//        user.setName("jack");
//        user.setSex(2);
//        return user;
//    }

    @POST
    @Path("/register")
    // 返回的相应体为JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public RegisterModel register(RegisterModel registerModel) {
        User user = new User();
        user.setName(registerModel.getName());
        user.setSex(2);
        return registerModel;
    }
}
