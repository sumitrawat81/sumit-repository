package com.sibsefid.model.doctor;

import java.util.List;

/**
 * Created by ubuntu on 5/1/17.
 */
public class SendMobileVerifyCodeModel {


    /**
     * Success : true
     * Data : {"Table1":[{"VerifiedCode":"CYTR","IsMobileVerified":"False","phone":"9997866961","phonecode":"91"}]}
     */

    private boolean Success;
    private DataBean Data;

    public boolean isSuccess() {
        return Success;
    }

    public void setSuccess(boolean Success) {
        this.Success = Success;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * VerifiedCode : CYTR
         * IsMobileVerified : False
         * phone : 9997866961
         * phonecode : 91
         */

        private List<Table1Bean> Table1;

        public List<Table1Bean> getTable1() {
            return Table1;
        }

        public void setTable1(List<Table1Bean> Table1) {
            this.Table1 = Table1;
        }

        public static class Table1Bean {
            private String VerifiedCode;
            private String IsMobileVerified;
            private String phone;
            private String phonecode;

            public String getVerifiedCode() {
                return VerifiedCode;
            }

            public void setVerifiedCode(String VerifiedCode) {
                this.VerifiedCode = VerifiedCode;
            }

            public String getIsMobileVerified() {
                return IsMobileVerified;
            }

            public void setIsMobileVerified(String IsMobileVerified) {
                this.IsMobileVerified = IsMobileVerified;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getPhonecode() {
                return phonecode;
            }

            public void setPhonecode(String phonecode) {
                this.phonecode = phonecode;
            }
        }
    }
}
