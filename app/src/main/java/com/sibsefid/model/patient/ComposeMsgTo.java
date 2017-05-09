package com.sibsefid.model.patient;

import java.util.List;

/**
 * Created by ubuntu on 21/9/16.
 */
public class ComposeMsgTo {


    /**
     * success : true
     * Data : [{"user_seq":"99","name":"RAjKumar Sharma"},{"user_seq":"420","name":"jean marc"},{"user_seq":"424","name":"maryline flore"},{"user_seq":"425","name":"tchuikwa r"},{"user_seq":"437","name":"com bear"},{"user_seq":"438","name":"com bear"},{"user_seq":"439","name":"Kate P"},{"user_seq":"441","name":"MARIAME CAMARA"},{"user_seq":"456","name":"gouri sharma"},{"user_seq":"457","name":"ayushi mittal"},{"user_seq":"459","name":"hermine djounda"},{"user_seq":"461","name":"ayushi mittal"},{"user_seq":"463","name":"parul mittal"},{"user_seq":"464","name":"Amara Belewa"},{"user_seq":"465","name":"pascale nandji"},{"user_seq":"466","name":"AUGUSTIN DOUYA"},{"user_seq":"468","name":"Mel moundele"},{"user_seq":"482","name":"Akash Sharma"},{"user_seq":"485","name":"john1 rawestja"},{"user_seq":"494","name":"Aarav Khurana"},{"user_seq":"502","name":"Evariste Ngankeu"}]
     */

    private boolean success;
    /**
     * user_seq : 99
     * name : RAjKumar Sharma
     */

    private List<DataBean> Data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        private String user_seq;
        private String name;

        public String getUser_seq() {
            return user_seq;
        }

        public void setUser_seq(String user_seq) {
            this.user_seq = user_seq;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
