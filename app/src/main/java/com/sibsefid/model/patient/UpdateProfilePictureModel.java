package com.sibsefid.model.patient;

import java.util.List;

/**
 * Created by ubuntu on 17/10/16.
 */
public class UpdateProfilePictureModel {


    /**
     * Success : true
     * Message : Update Successfully!!!
     * Data : {"UserImage":[{"user_seq":"515","user_photo":"IMG_20160416_065243_10_17_20162_46_26PM.jpg"}]}
     */

    private boolean Success;
    private String Message;
    private DataBean Data;

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * user_seq : 515
         * user_photo : IMG_20160416_065243_10_17_20162_46_26PM.jpg
         */

        private List<UserImageBean> UserImage;

        public List<UserImageBean> getUserImage() {
            return UserImage;
        }

        public void setUserImage(List<UserImageBean> UserImage) {
            this.UserImage = UserImage;
        }

        public static class UserImageBean {
            private String user_seq;
            private String user_photo;

            public String getUser_seq() {
                return user_seq;
            }

            public void setUser_seq(String user_seq) {
                this.user_seq = user_seq;
            }

            public String getUser_photo() {
                return user_photo;
            }

            public void setUser_photo(String user_photo) {
                this.user_photo = user_photo;
            }
        }
    }
}
