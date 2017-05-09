package com.sibsefid.model.doctor;

import java.util.ArrayList;

/**
 * Created by root on 5/10/16.
 */
public class AppointmentStatusModel {

    /**
     * Success : true
     * Message :
     * Data : {"Table":[{"StatusID":"1","Status":"Pending"},{"StatusID":"2","Status":"Completed"},{"StatusID":"3","Status":"Approved"},{"StatusID":"4","Status":"Cancelled By Doctor"},{"StatusID":"5","Status":"Cancelled By Patient"},{"StatusID":"6","Status":"Rejected"},{"StatusID":"7","Status":"Approved - Not Completed"}]}
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
         * StatusID : 1
         * Status : Pending
         */

        private ArrayList<TableBean> Table;

        public ArrayList<TableBean> getTable() {
            return Table;
        }

        public void setTable(ArrayList<TableBean> Table) {
            this.Table = Table;
        }

        public static class TableBean {
            private String StatusID;
            private String Status;

            public String getStatusID() {
                return StatusID;
            }

            public void setStatusID(String StatusID) {
                this.StatusID = StatusID;
            }

            public String getStatus() {
                return Status;
            }

            public void setStatus(String Status) {
                this.Status = Status;
            }
        }
    }
}
