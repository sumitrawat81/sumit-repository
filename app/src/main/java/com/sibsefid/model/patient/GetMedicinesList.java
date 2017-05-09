package com.sibsefid.model.patient;

import java.util.List;

/**
 * Created by ubuntu on 24/11/16.
 */
public class GetMedicinesList {


    /**
     * Success : true
     * Message : Medicine list.
     * Data : {"MedicineList":[{"srno":"78","MedicinName":"Combiflom"}]}
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
         * srno : 78
         * MedicinName : Combiflom
         */

        private List<MedicineListBean> MedicineList;

        public List<MedicineListBean> getMedicineList() {
            return MedicineList;
        }

        public void setMedicineList(List<MedicineListBean> MedicineList) {
            this.MedicineList = MedicineList;
        }

        public static class MedicineListBean {
            private String srno;
            private String MedicinName;

            public String getSrno() {
                return srno;
            }

            public void setSrno(String srno) {
                this.srno = srno;
            }

            public String getMedicinName() {
                return MedicinName;
            }

            public void setMedicinName(String MedicinName) {
                this.MedicinName = MedicinName;
            }
        }
    }
}
