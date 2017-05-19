package com.studio.pattimura.bukaamal.Model;

import java.io.Serializable;

/**
 * Created by desmoncode on 12/05/17.
 */

public class userAuth implements Serializable{
    private String user_id,user_name,token,email,omnikey;

    public userAuth(String user_id, String user_name, String token, String email, String omnikey) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.token = token;
        this.email = email;
        this.omnikey = omnikey;
    }

    public userAuth() {
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOmnikey() {
        return omnikey;
    }

    public void setOmnikey(String omnikey) {
        this.omnikey = omnikey;
    }
}
