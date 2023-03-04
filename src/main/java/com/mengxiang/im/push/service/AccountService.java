package com.mengxiang.im.push.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * @author lbj
 */
@Path("/account")
public class AccountService {

    /**
     *请求实际地址(127.0.0.1/api/account/login)
     */
    @GET
    @Path("/login")
    public String get(){
        return "get login";
    }
}
