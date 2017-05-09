package com.sibsefid.e911md.services;

import com.sibsefid.model.doctor.RegisterResponseModel;
import com.sibsefid.model.patient.PMyHistoryParamsModel;
import com.sibsefid.util.ApiConstant;
import com.sibsefid.util.Utils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 4/10/16.
 */
public class SaveRequestedData implements Callback<RegisterResponseModel> {
    private static final String TAG = SetPHistoryFamilyCallApi.class.getSimpleName();
    private OnSaveRequestedDataCallBackListener callBackListener;

    private int adpterPosition = -1;
    private int rowPosition = -1;
    private PMyHistoryParamsModel pMyHistoryParamsModel = new PMyHistoryParamsModel();

    public SaveRequestedData(OnSaveRequestedDataCallBackListener callBackListener) {
        this.callBackListener = callBackListener;
    }

    public synchronized void connect(PMyHistoryParamsModel modelparms) {
        this.adpterPosition = modelparms.getAdpterPosition();
        this.rowPosition = modelparms.getRowPosition();

        if (modelparms.getType() == ApiConstant.DOCTOR_ACCEPTS_APPOINTMENT_TYPE) {

            RestAdapter.getAdapter()
                    .acceptsAppointment(modelparms.getAppointmentid(), modelparms.getAction(), modelparms.getPatientemail(), modelparms.getDoctorname(),Utils.landSend)
                    .enqueue(this);
        }


    }

    @Override
    public void onResponse(Call<RegisterResponseModel> call, Response<RegisterResponseModel> response) {
        if (response.isSuccessful()) {
            RegisterResponseModel data = response.body();
            if (!data.isSuccess()) {
                callBackListener.onnSaveRequestedDataFailure(data, adpterPosition, rowPosition);
            } else {
                callBackListener.onnSaveRequestedDataSuccess(data, adpterPosition, rowPosition);
            }
        } else {
            try {
                callBackListener.onnSaveRequestedDatakException(response.errorBody().string(), adpterPosition, rowPosition);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call<RegisterResponseModel> call, Throwable t) {
        t.printStackTrace();
        callBackListener.onnSaveRequestedDatakException(t.getMessage(), adpterPosition, rowPosition);
    }

    public interface OnSaveRequestedDataCallBackListener {
        void onnSaveRequestedDataSuccess(RegisterResponseModel data, int adpterPosition, int rowPosition);

        void onnSaveRequestedDataFailure(RegisterResponseModel data, int adpterPosition, int rowPosition);

        void onnSaveRequestedDatakException(String message, int adpterPosition, int rowPosition);
    }
}