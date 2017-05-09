package com.sibsefid.model.doctor;

/**
 * Created by ubuntu on 6/10/16.
 */
public class SetAvailabilityResponseModel {


    /**
     * success : true
     * message : Available Time Save successfully
     */

    private boolean success;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
