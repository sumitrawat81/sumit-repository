package com.sibsefid;

/**
 * Created by ubuntu on 8/2/17.
 */
public class ChangeLanguage{

  /*  private Context context;
    private CustomDialogListner customDialogListner;
    private ChangeLanguage dia;
    private Button btn_positive, btn_negative;
    private ListView langauge_list;
    private ArrayList<LanguageModule.DataBean> langage_data;
    private LangageAdpater langageAdpater;
    private View.OnClickListener onClickListener;
    private LanguageModule.DataBean selected_lanages;

    public ChangeLanguage (Context context
            , CustomDialogListner customDialogListner) {
        super(context);
        this.context = context;
        this.customDialogListner = customDialogListner;
        this.dia = this;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(R.color.transparent);
        LayoutInflater inflate = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflate.inflate(R.layout.language_selection, null);
        setContentView(layout);
        WindowManager.LayoutParams wlmp = getWindow().getAttributes();
        wlmp.windowAnimations = R.style.Message_dialog_style;
        wlmp.gravity = Gravity.CENTER;
        wlmp.width = ViewGroup.LayoutParams.MATCH_PARENT;
        setTitle(null);
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        langauge_list = (ListView) layout.findViewById(R.id.langauge_list);

        CallLanguageApi();

        onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pos = (int) v.getTag();

                selected_lanages = langage_data.get(pos);
                Utils.showCustomToast(langage_data.get(pos).getName(),context);

                RestartDialogShow();


            }
        };

    }



*//*
* restart dailog
* *//*

    private void RestartDialogShow() {

        new RestartAppDialog(context, new CustomDialogListner() {
            @Override
            public void positiveButtonClick(Dialog dialog, View v) {
                dialog.dismiss();

                callUpdateDefaultlangae();

            }

            @Override
            public void negativeButtonClick(Dialog dialog, View v) {

                dialog.dismiss();

            }
        }).show();

    }


    private void CallLanguageApi() {


       // new Rest(context, ChangeLanguage.this).getAllLangauge();
    }

    private void callUpdateDefaultlangae() {


       // new Rest(context, ChangeLanguage.this).UpdateDefaultLanagage(selected_lanages);
    }


    @Override
    public void onResponse(Call<Objects> call, Response<Objects> response) {

        if (response.isSuccessful()) {

            Object obj = response.body();

            if (obj instanceof LanguageModule) {

                try {
                    LanguageModule languageModule = (LanguageModule) obj;
                    if (!languageModule.isError() && languageModule.getData() != null) {

                        langage_data = (ArrayList<LanguageModule.DataBean>) languageModule.getData();
                        langageAdpater = new LangageAdpater(context, langage_data, onClickListener);
                        langauge_list.setAdapter(langageAdpater);

                        Utils.showCustomToast( languageModule.getMessage(),context);


                    } else {

                        Utils.showCustomToast(languageModule.getMessage(),context);

                    }

                //    Utils.dismissProgressdialog();

                } catch (Exception e) {

                    Utils.showCustomToast("errror is" + e.getMessage(),context);

                    e.printStackTrace();
                   // Utils.dismissProgressdialog();
                }


            } *//*else if (obj instanceof CommonModule) {
                try {
                    CommonModule commonModule = (CommonModule) obj;
                    if (!commonModule.isError()) {

                        String language_id = selected_lanages.getId();
                        SharedPreferences.setStringKeyvaluePrefs(context, AppPrefs.KEY_LANGUAGE,
                                language_id);
                        if (selected_lanages.getName().equalsIgnoreCase("Urdu")) {
                            AppPrefs.setStringKeyvaluePrefs(context, AppPrefs.KEY_LANGUAGE_CODE,
                                    "ur");
                        } else if (selected_lanages.getName().equalsIgnoreCase("Arabic")) {

                            AppPrefs.setStringKeyvaluePrefs(context, AppPrefs.KEY_LANGUAGE_CODE,
                                    "ar");
                        } else {

                            AppPrefs.setStringKeyvaluePrefs(context, AppPrefs.KEY_LANGUAGE_CODE,
                                    "en");
                        }

                        Utils.showCustomToast( commonModule.getMessage(),context);
                        Intent i = context.getPackageManager()
                                .getLaunchIntentForPackage(context.getPackageName());
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(i);


                    } else {

                        Utils.showCustomToast( commonModule.getMessage(),context);

                    }

                  //  Utils.dismissProgressdialog();

                } catch (Exception e) {

                    Utils.showCustomToast("errror is" + e.getMessage(),context);
/
                    e.printStackTrace();
                 //   Utils.dismissProgressdialog();
                }


            }*//*


        } else if (response.code() == 400) {

            try {

                Utils.showCustomToast( response.errorBody().string(),context);

                Log.v("Error code 400", response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Utils.dismissProgressdialog();
        } else {

            //Utils.dismissProgressdialog();
        }

    }

    @Override
    public void onFailure(Call<Objects> call, Throwable t) {
        *//*if (call instanceof CommonModule) {
           // Utils.dismissProgressdialog();
            Utils.showCustomToast( t.getMessage(),context);

        }*//*

    }*/


}
