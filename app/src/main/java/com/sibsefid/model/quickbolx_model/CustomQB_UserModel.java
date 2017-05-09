package com.sibsefid.model.quickbolx_model;

import java.io.Serializable;

/**
 * Created by ubuntu on 29/9/16.
 */
public class CustomQB_UserModel implements Serializable {

    int id;
    String email;
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
