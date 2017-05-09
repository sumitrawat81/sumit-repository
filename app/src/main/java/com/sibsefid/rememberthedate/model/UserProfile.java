package com.sibsefid.rememberthedate.model;

import android.graphics.Bitmap;

/**
 * Data model for a user profile
 */
public class UserProfile {

    private String mName;

    private String mEmail;
    private String mPhone;
    private Bitmap mAvatar;

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        this.mEmail = email;
    }

    public Bitmap getAvatar() {
        return mAvatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.mAvatar = avatar;
    }
}
