package com.sibsefid.e911md.services;

import com.sibsefid.model.doctor.RegisterResponseModel;
import com.sibsefid.util.Utils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 22/9/16.
 */
public class PMedicalHistoryApiCallBack implements Callback<RegisterResponseModel> {
    private static final String TAG = PMedicalHistoryApiCallBack.class.getSimpleName();
    private OnAllergiesSaveCallBackListener mOnAllergiesSaveCallBackListener;

    private int adpterPosition = -1;
    private int rowPosition = -1;

    public PMedicalHistoryApiCallBack(OnAllergiesSaveCallBackListener onAllergiesSaveCallBackListener) {
        this.mOnAllergiesSaveCallBackListener = onAllergiesSaveCallBackListener;
    }

    public void connect(String patientId,
                        String ID,
                        String what_happen,
                        String Name,
                        int adpterPosition, int rowPosition) {
        this.adpterPosition = adpterPosition;
        this.rowPosition = rowPosition;

        RestAdapter.getAdapter()
                .saveAllergies(patientId, ID, what_happen, Name,Utils.landSend)
                .enqueue(this);
    }

    @Override
    public void onResponse(Call<RegisterResponseModel> call, Response<RegisterResponseModel> response) {
        if (response.isSuccessful()) {
            RegisterResponseModel data = response.body();
            if (!data.isSuccess()) {
                mOnAllergiesSaveCallBackListener.onAllergiesSaveCallBackFailure(data, adpterPosition, rowPosition);
            } else {
                mOnAllergiesSaveCallBackListener.onAllergiesSaveCallBackSuccess(data, adpterPosition, rowPosition);
            }
        } else {
            try {
                mOnAllergiesSaveCallBackListener.onAllergiesSaveCallBackException(response.errorBody().string(), adpterPosition, rowPosition);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFailure(Call<RegisterResponseModel> call, Throwable t) {
        t.printStackTrace();
        mOnAllergiesSaveCallBackListener.onAllergiesSaveCallBackException(t.getMessage(), adpterPosition, rowPosition);
    }

    public interface OnAllergiesSaveCallBackListener {
        void onAllergiesSaveCallBackSuccess(RegisterResponseModel data, int adpterPosition, int rowPosition);

        void onAllergiesSaveCallBackFailure(RegisterResponseModel data, int adpterPosition, int rowPosition);

        void onAllergiesSaveCallBackException(String message, int adpterPosition, int rowPosition);
    }
}