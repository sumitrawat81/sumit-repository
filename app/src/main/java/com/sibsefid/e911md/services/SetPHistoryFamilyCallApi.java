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
 * Created by root on 22/9/16.
 */
public class SetPHistoryFamilyCallApi implements Callback<RegisterResponseModel> {
    private static final String TAG = SetPHistoryFamilyCallApi.class.getSimpleName();
    private OnFamilyHiSaveCallBackListener mOnAllergiesSaveCallBackListener;

    private int adpterPosition = -1;
    private int rowPosition = -1;
    private PMyHistoryParamsModel pMyHistoryParamsModel = new PMyHistoryParamsModel();

    public SetPHistoryFamilyCallApi(OnFamilyHiSaveCallBackListener onAllergiesSaveCallBackListener) {
        this.mOnAllergiesSaveCallBackListener = onAllergiesSaveCallBackListener;
    }

    public synchronized void connect(PMyHistoryParamsModel modelparms) {
        this.adpterPosition = modelparms.getAdpterPosition();
        this.rowPosition = modelparms.getRowPosition();

        if (modelparms.getType() == ApiConstant.FAMILY_HISTORY_TYPE) {
            RestAdapter.getAdapter()
                    .addPatientHistoryOnTab(modelparms.getPatientid(), modelparms.getIDInt(), modelparms.isCheck(), modelparms.getName(),Utils.landSend)
                    .enqueue(this);
        } else if (modelparms.getType() == ApiConstant.LIFE_STYLE_TYPE) {
            RestAdapter.getAdapter()
                    .setLifeStyleOnTab(modelparms.getPatientid(), modelparms.getIDInt(), modelparms.getDeleted_item_id_type(),Utils.landSend)
                    .enqueue(this);
        }


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

    public interface OnFamilyHiSaveCallBackListener {
        void onAllergiesSaveCallBackSuccess(RegisterResponseModel data, int adpterPosition, int rowPosition);

        void onAllergiesSaveCallBackFailure(RegisterResponseModel data, int adpterPosition, int rowPosition);

        void onAllergiesSaveCallBackException(String message, int adpterPosition, int rowPosition);
    }
}