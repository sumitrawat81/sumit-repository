package com.sibsefid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sibsefid.model.doctor.UserLoginDetails;
import com.sibsefid.util.MyPrefs;
import com.quickblox.chat.model.QBChatMessage;

import java.util.ArrayList;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ubuntu on 31/5/16.
 */
public class FrgChatWAdapter extends BaseAdapter {
    public ArrayList<QBChatMessage> messageList;
    LayoutInflater inflater;
    View.OnClickListener onClickListener;
    private Context context;
    private UserLoginDetails.LoginDetails details;

    public FrgChatWAdapter(Context context, ArrayList<QBChatMessage> messageList, View.OnClickListener onClickListener) {
        this.context = context;
        this.messageList = messageList;
        this.onClickListener = onClickListener;
        details = MyPrefs.getLoginDetails(context);
        if (context != null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public QBChatMessage getItem(int position) {
        return messageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderChatW viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolderChatW();
            convertView = inflater.inflate(R.layout.frg_chat_w_row, null);
            viewHolder.llReceiver = (LinearLayout) convertView.findViewById(R.id.llReceiver);
            viewHolder.imgReceiver = (CircleImageView) convertView.findViewById(R.id.imgReceiver);
            viewHolder.txtMsgRec = (TextView) convertView.findViewById(R.id.txtMsgRec);
            viewHolder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
            viewHolder.llSender = (LinearLayout) convertView.findViewById(R.id.llSender);
            viewHolder.llReceiver2 = (LinearLayout) convertView.findViewById(R.id.llReceiver2);
            viewHolder.llSender2 = (LinearLayout) convertView.findViewById(R.id.llSender2);
            viewHolder.llMsgRec = (LinearLayout) convertView.findViewById(R.id.llMsgRec);
            viewHolder.txtMsgSend = (TextView) convertView.findViewById(R.id.txtMsgSend);


            viewHolder.time = (TextView) convertView.findViewById(R.id.time);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderChatW) convertView.getTag();
        }
        QBChatMessage frgChatWModel = messageList.get(position);
        hideAllFields(viewHolder);
        viewHolder.currentPosition = position;
        viewHolder.frgChatWModel = frgChatWModel;
        Map<String, String> propertiesMap = frgChatWModel.getProperties();
        if (!frgChatWModel.getSenderId().equals(Integer.valueOf(details.getQuickBloxId()))) {
            viewHolder.llReceiver.setVisibility(View.VISIBLE);
            viewHolder = setDataToRow(frgChatWModel, viewHolder.txtMsgRec, viewHolder, viewHolder.llReceiver2);
            // Utils.loadUImageUsing(context, propertiesMap.get(Constants.SENDER_IMAGE_URL), R.drawable.default_user_profile, R.drawable.default_user_profile, viewHolder.imgReceiver);

        } else {
            viewHolder.llSender.setVisibility(View.VISIBLE);
            viewHolder = setDataToRow(frgChatWModel, viewHolder.txtMsgSend, viewHolder, viewHolder.llSender2);

        }
        return convertView;
    }

    private void hideAllFields(ViewHolderChatW viewHolderChatW) {
        int vis = View.GONE;
        viewHolderChatW.llReceiver.setVisibility(vis);
        viewHolderChatW.txtDate.setVisibility(vis);
        viewHolderChatW.llSender.setVisibility(vis);
    }

    public ViewHolderChatW setDataToRow(QBChatMessage frgChatWModel, TextView txtBody, ViewHolderChatW viewHolder, LinearLayout llReceiver2) {

        txtBody.setText(frgChatWModel.getBody());

        llReceiver2.setVisibility(View.VISIBLE);

        return viewHolder;
    }

    public class ViewHolderChatW {
        public LinearLayout llReceiver;
        public CircleImageView imgReceiver;
        public TextView txtMsgRec;
        public TextView txtDate;
        public LinearLayout llSender;
        public TextView txtMsgSend;
        public ImageView imgAttachSend;

        public LinearLayout llReceiver2;
        public LinearLayout llSender2;
        public LinearLayout llMsgRec;
        public int currentPosition;
        public ProgressBar myaudio_progress;
        public ProgressBar myaudio_progressRec;
        public QBChatMessage frgChatWModel;
        public TextView time;

    }

}

