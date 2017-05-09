package com.sibsefid.model.doctor;

/**
 * Created by ubuntu on 5/1/17.
 */
public class VerifyMobileCodeModel {


    /**
     * Success : true
     * Message : Verified
     */

    private boolean Success;
    private String Message;

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
}
