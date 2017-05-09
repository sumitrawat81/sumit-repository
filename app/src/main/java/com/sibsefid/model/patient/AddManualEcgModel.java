package com.sibsefid.model.patient;

/**
 * Created by ubuntu on 25/10/16.
 */
public class AddManualEcgModel {


    /**
     * Success : false
     * Message : ECG File is required.
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
