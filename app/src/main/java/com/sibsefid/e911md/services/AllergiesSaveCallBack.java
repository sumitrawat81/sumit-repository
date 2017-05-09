package com.sibsefid.e911md.services;

import com.sibsefid.model.doctor.RegisterResponseModel;
import com.sibsefid.model.patient.PMyHistoryParamsModel;
import com.sibsefid.util.ApiConstant;
import com.sibsefid.util.MyPrefs;
import com.sibsefid.util.Utils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by root on 22/9/16.
 */
public class AllergiesSaveCallBack implements Callback<RegisterResponseModel> {
    private static final String TAG = AllergiesSaveCallBack.class.getSimpleName();
    private OnAllergiesSaveCallBackListener mOnAllergiesSaveCallBackListener;

    private int adpterPosition = -1;
    private int rowPosition = -1;
    private PMyHistoryParamsModel pMyHistoryParamsModel = new PMyHistoryParamsModel();

    public AllergiesSaveCallBack(OnAllergiesSaveCallBackListener onAllergiesSaveCallBackListener) {
        this.mOnAllergiesSaveCallBackListener = onAllergiesSaveCallBackListener;
    }

    public synchronized void connect(PMyHistoryParamsModel modelparms) {
        this.adpterPosition = modelparms.getAdpterPosition();
        this.rowPosition = modelparms.getRowPosition();

        if (modelparms.getType() == ApiConstant.ALLERGIES_TYPE) {

            RestAdapter.getAdapter()
                    .saveAllergies(modelparms.getPatientid(), modelparms.getID(), modelparms.getWhat_happen(), modelparms.getName(),Utils.landSend)
                    .enqueue(this);
        } else if (modelparms.getType() == ApiConstant.ALLERGIES_UPDATE_TYPE) {
            RestAdapter.getAdapter()
                    .saveAllergies(modelparms.getPatientid(), modelparms.getID(), modelparms.getWhat_happen(), modelparms.getName(),Utils.landSend)
                    .enqueue(this);
        } else if (modelparms.getType() == ApiConstant.ALLERGIES_DELETE_TYPE) {
            RestAdapter.getAdapter()
                    .deleteMyHistoryRow((modelparms.getDeleted_item_id_type()), modelparms.getIDInt(),Utils.landSend)
                    .enqueue(this);
        } else if (modelparms.getType() == ApiConstant.MEDICAL_HISTORY_TYPE) {
            RestAdapter.getAdapter()
                    .saveMedicalHistory(modelparms.getPatientidInt(), modelparms.getIDInt(), modelparms.getName(), modelparms.getYearstarted(), modelparms.getWhat_happen(), modelparms.getHowmanyeachday(),Utils.landSend)
                    .enqueue(this);
        } else if (modelparms.getType() == ApiConstant.MEDICAL_HISTORY_UPDATE_TYPE) {
            RestAdapter.getAdapter()
                    .saveMedicalHistory(modelparms.getPatientidInt(), modelparms.getIDInt(), modelparms.getName(), modelparms.getYearstarted(), modelparms.getWhat_happen(), modelparms.getHowmanyeachday(),Utils.landSend)
                    .enqueue(this);
        } else if (modelparms.getType() == ApiConstant.MEDICAL_HISTORY_DELETE_TYPE) {
            RestAdapter.getAdapter()
                    .deleteMyHistoryRow((modelparms.getDeleted_item_id_type()), modelparms.getIDInt(),Utils.landSend)
                    .enqueue(this);
        } else if (modelparms.getType() == ApiConstant.PAST_MEDICAL_HISTORY_TYPE) {
            RestAdapter.getAdapter()
                    .savePastMedicalHistory(modelparms.getPatientidInt(), modelparms.getIDInt(), modelparms.isCheck(), modelparms.getYearstarted(), modelparms.getName(),Utils.landSend)
                    .enqueue(this);
        } else if (modelparms.getType() == ApiConstant.SMOKING_TYPE) {
            RestAdapter.getAdapter()
                    .saveSmoking(modelparms.getPatientidInt(), modelparms.getIDInt(), modelparms.getYearstarted(), modelparms.getHowmanyeachday(),Utils.landSend)
                    .enqueue(this);
        } else if (modelparms.getType() == ApiConstant.SMOKING_DELETE_TYPE) {
            RestAdapter.getAdapter()
                    .deleteMyHistoryRow((modelparms.getDeleted_item_id_type()), modelparms.getIDInt(),Utils.landSend)
                    .enqueue(this);
        } else if (modelparms.getType() == ApiConstant.ALCOHOL_TYPE) {
            RestAdapter.getAdapter()
                    .saveAlcohol(modelparms.getPatientidInt(), modelparms.getIDInt(), modelparms.getWhat_happen(), modelparms.getName(),Utils.landSend)
                    .enqueue(this);
        } else if (modelparms.getType() == ApiConstant.ALCOHOL_DELETE_TYPE) {
            RestAdapter.getAdapter()
                    .deleteMyHistoryRow((modelparms.getDeleted_item_id_type()), modelparms.getIDInt(),Utils.landSend)
                    .enqueue(this);
        } else if (modelparms.getType() == ApiConstant.FAMILY_TYPE) {
            RestAdapter.getAdapter()
                    .saveFamilyHistory(modelparms.getPatientidInt(), modelparms.getIDInt(), modelparms.getName(), modelparms.getWhat_happen(),Utils.landSend)
                    .enqueue(this);
        } else if (modelparms.getType() == ApiConstant.FAMILY_DELETE_TYPE) {
            RestAdapter.getAdapter()
                    .deleteMyHistoryRow((modelparms.getDeleted_item_id_type()), modelparms.getIDInt(),Utils.landSend)
                    .enqueue(this);
        } else if (modelparms.getType() == ApiConstant.WAIST_TYPE) {
            RestAdapter.getAdapter()
                    .saveWaist(modelparms.getPatientidInt(), modelparms.getIDInt(), modelparms.getName(),Utils.landSend)
                    .enqueue(this);
        } else if (modelparms.getType() == ApiConstant.WAIST_DELETE_TYPE) {
            RestAdapter.getAdapter()
                    .deleteMyHistoryRow((modelparms.getDeleted_item_id_type()), modelparms.getIDInt(),Utils.landSend)
                    .enqueue(this);
        } else if (modelparms.getType() == ApiConstant.HEIGHT_TYPE) {
            RestAdapter.getAdapter()
                    .saveHeight(modelparms.getPatientidInt(), modelparms.getIDInt(), modelparms.getName(),Utils.landSend)
                    .enqueue(this);
        } else if (modelparms.getType() == ApiConstant.HEIGHT_DELETE_TYPE) {
            RestAdapter.getAdapter()
                    .deleteMyHistoryRow((modelparms.getDeleted_item_id_type()), modelparms.getIDInt(),Utils.landSend)
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

    public interface OnAllergiesSaveCallBackListener {
        void onAllergiesSaveCallBackSuccess(RegisterResponseModel data, int adpterPosition, int rowPosition);

        void onAllergiesSaveCallBackFailure(RegisterResponseModel data, int adpterPosition, int rowPosition);

        void onAllergiesSaveCallBackException(String message, int adpterPosition, int rowPosition);
    }
}