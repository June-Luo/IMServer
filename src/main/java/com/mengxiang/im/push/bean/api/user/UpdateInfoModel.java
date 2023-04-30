package com.mengxiang.im.push.bean.api.user;

import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;
import com.mengxiang.im.push.bean.db.User;
import com.mengxiang.im.push.utils.TextUtil;

import javax.swing.plaf.TextUI;

/**
 * 用户更新信息，完善信息Model
 */
public class UpdateInfoModel {
    @Expose
    private String name;
    @Expose
    private String portrait;
    @Expose
    private String desc;
    @Expose
    private int sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    //填充一个UserModel
    public User updateToUser(User user) {
        if (!TextUtil.isEmpty(name)) {
            user.setName(name);
        }

        if (!TextUtil.isEmpty(portrait)) {
            user.setPortrait(portrait);
        }

        if (!TextUtil.isEmpty(desc)) {
            user.setDescription(desc);
        }

        if (sex != 0) {
            user.setSex(sex);
        }

        return user;
    }

    public static boolean check(UpdateInfoModel model) {
        // Model 不允许为null，
        // 并且只需要具有一个及其以上的参数即可
        return model != null
                &&
                (!TextUtil.isEmpty(model.name)
                || !TextUtil.isEmpty(model.portrait)
                || !TextUtil.isEmpty(model.desc)
                || model.sex != 0);
    }
}
