package com.sibsefid.quickbloxchat.chat;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.sibsefid.ApplicationLifecycleHandler;
import com.sibsefid.FrgChatWindow;
import com.sibsefid.OnDataNotify;
import com.sibsefid.PatientActivity;
import com.sibsefid.services.AppService;
import com.sibsefid.util.MyPrefs;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBMessageStatusesManager;
import com.quickblox.chat.QBPrivateChat;
import com.quickblox.chat.QBPrivateChatManager;
import com.quickblox.chat.exception.QBChatException;
import com.quickblox.chat.listeners.QBMessageListenerImpl;
import com.quickblox.chat.listeners.QBMessageSentListener;
import com.quickblox.chat.listeners.QBMessageStatusListener;
import com.quickblox.chat.listeners.QBPrivateChatManagerListener;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.chat.model.QBDialog;

import org.jivesoftware.smack.SmackException;


public class PrivateChatImpl extends QBMessageListenerImpl<QBPrivateChat> implements Chat, QBPrivateChatManagerListener,
        QBMessageSentListener<QBPrivateChat>, QBMessageStatusListener {
    private static final String TAG = "PrivateChatManagerImpl";
    public static QBPrivateChat chat;
    public static QBPrivateChatManager chatManager;
    public OnDataNotify onDataNotify;
    QBDialog dialogToCreate;
    QBMessageStatusListener qbMessageStatusListener;
    QBPrivateChatManagerListener qbPrivateChatManagerListener;
    private Context context;
    private AppService doctrorDrawr;
    private FrgChatWindow chatActivity;
    private Integer opponentID;
    private QBMessageStatusesManager messageStatusesManager;

    public PrivateChatImpl(AppService doctrorDrawr) {
        try {
            initManagerIfNeed();
            if (doctrorDrawr != null) {
                this.doctrorDrawr = doctrorDrawr;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static PrivateChatImpl getInstance(Integer opponentID) {
        if (AppService.privateChat == null)
            AppService.privateChat = new PrivateChatImpl(AppService.getInstance());
        if (AppService.privateChat != null && opponentID > 0) {
            if (chatManager == null) {
                chatManager = QBChatService.getInstance().getPrivateChatManager();
            }
            try {
                chat = chatManager.getChat(opponentID);
            } catch (Exception e) {

            }

            if (chat == null) {
                chat = chatManager.createChat(opponentID, AppService.privateChat);
            }
            chat.addMessageListener(AppService.privateChat);
            chat.addMessageSentListener(AppService.privateChat);

        }
        return AppService.privateChat;
    }

    private void initManagerIfNeed() {
        if (chatManager == null) {
            qbMessageStatusListener = this;
            qbPrivateChatManagerListener = this;
            chatManager = AppService.chatService.getPrivateChatManager();
            if (chatManager != null)
                chatManager.addPrivateChatManagerListener(qbPrivateChatManagerListener);
            messageStatusesManager = AppService.chatService.getMessageStatusesManager();
            if (qbMessageStatusListener == null) {
                qbMessageStatusListener = this;
            }
            messageStatusesManager.addMessageStatusListener(qbMessageStatusListener);
        }

    }

    @Override
    public void sendMessage(QBChatMessage message) {
        try {
            if (chat != null) {
                chat.sendMessage(message);
                if (onDataNotify != null) {
                    onDataNotify.dataNotify(message, message);
                }
            }
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void release() {
        Log.w(TAG, "release private chat");
        chat.removeMessageListener(this);
        chatManager.removePrivateChatManagerListener(this);
    }

    @Override
    public void processMessage(QBPrivateChat chat, QBChatMessage message) {
        Log.w(TAG, "new incoming message: " + message);
        Log.e("Message from User", "new incoming message: " + message);
        Log.e("Mess Info", "new incoming messageInfo: " + chat + "");
        AppService.msgCounter = AppService.msgCounter + 1;
        int count = Integer.valueOf(MyPrefs.getUserMsgCount(doctrorDrawr).trim());
        count = count + 1;
        MyPrefs.saveUserMsgCountl(doctrorDrawr, String.valueOf(count));
        if (!ApplicationLifecycleHandler.isInBackground) {
            if (message.getBody().equalsIgnoreCase("Chat_Disconnected_Form")) {
                doctrorDrawr.hangUpCurrentSession();
            } else if (message.getBody().equalsIgnoreCase("Chat_Disconnected_Time_Over")) {
                Log.e("Comes to end of time", "Comes to end of time");
                int LOGINTYPE = MyPrefs.getUser(doctrorDrawr);
                if (LOGINTYPE == 1) {
                    Message msg = PatientActivity.SessionTimeCloseCallBack.obtainMessage();
                    Bundle b = new Bundle();
                    b.putString("session_end", "session_end");
                    msg.setData(b);
                    PatientActivity.SessionTimeCloseCallBack.sendMessage(msg);

                } else if (LOGINTYPE == 2) {

                }


            } else {
                if (onDataNotify != null) {
                    onDataNotify.dataNotify(chat, message);
                } else {
                    AppService.onDataNotify.dataNotify(chat, message);
                }
            }
        }
    }

    @Override
    public void processError(QBPrivateChat chat, QBChatException error, QBChatMessage originChatMessage) {

    }

    @Override
    public void chatCreated(QBPrivateChat incomingPrivateChat, boolean createdLocally) {
        if (!createdLocally) {
            chat = incomingPrivateChat;
            chat.addMessageListener(PrivateChatImpl.this);
            chat.addMessageSentListener(PrivateChatImpl.this);
        }

        Log.w(TAG, "private chat created: " + incomingPrivateChat.getParticipant() + ", createdLocally:" + createdLocally);
    }

    @Override
    public void processMessageSent(QBPrivateChat qbPrivateChat, QBChatMessage qbChatMessage) {
        //Toast.makeText(chatActivity, "message was sent to " + qbChatMessage.getRecipientId(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void processMessageFailed(QBPrivateChat qbPrivateChat, QBChatMessage qbChatMessage) {

        //Toast.makeText(chatActivity, "message sent failed to " + qbChatMessage.getRecipientId(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void processMessageDelivered(String s, String s1, Integer integer) {

    }

    @Override
    public void processMessageRead(String s, String s1, Integer integer) {

    }

}
