package com.sibsefid.model.patient;

import java.util.ArrayList;

/**
 * Created by ubuntu on 19/9/16.
 */
public class PatientTitleModel {


    /**
     * success : true
     * Data : [{"detail_code":"3","detail_code_nm":"Mr."},{"detail_code":"4","detail_code_nm":"Mrs."},{"detail_code":"5","detail_code_nm":"Ms."},{"detail_code":"6","detail_code_nm":"Miss."},{"detail_code":"7","detail_code_nm":"Dr."},{"detail_code":"8","detail_code_nm":"Prof."},{"detail_code":"9","detail_code_nm":"Sir."},{"detail_code":"10","detail_code_nm":"Lady."},{"detail_code":"11","detail_code_nm":"Br"},{"detail_code":"12","detail_code_nm":"Fr"},{"detail_code":"13","detail_code_nm":"Sr"},{"detail_code":"14","detail_code_nm":"Other"}]
     */

    private boolean success;
    /**
     * detail_code : 3
     * detail_code_nm : Mr.
     */

    private ArrayList<DataBean> Data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ArrayList<DataBean> getData() {
        return Data;
    }

    public void setData(ArrayList<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        private String detail_code;
        private String detail_code_nm;

        public String getDetail_code() {
            return detail_code;
        }

        public void setDetail_code(String detail_code) {
            this.detail_code = detail_code;
        }

        public String getDetail_code_nm() {
            return detail_code_nm;
        }

        public void setDetail_code_nm(String detail_code_nm) {
            this.detail_code_nm = detail_code_nm;
        }
    }
}
