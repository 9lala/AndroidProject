package com.james.test.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.james.test.view.ToastShowAddress;

public class NumberLocationService extends Service {
    private CallInListener callInListener;
    private CallOutReceiver callOutReceiver;
    private ToastShowAddress mToast;
    private TelephonyManager tpManager;

    public IBinder onBind(Intent paramIntent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        this.mToast = new ToastShowAddress(this);
        this.tpManager = ((TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE));
        this.callInListener = new CallInListener();
        this.tpManager.listen(this.callInListener, 32);
        this.callOutReceiver = new CallOutReceiver();
        IntentFilter localIntentFilter = new IntentFilter();
        localIntentFilter.addAction("android.intent.action.NEW_OUTGOING_CALL");
        registerReceiver(this.callOutReceiver, localIntentFilter);
    }

    public void onDestroy() {
        super.onDestroy();
        this.tpManager.listen(this.callInListener, 0);
        unregisterReceiver(this.callOutReceiver);
    }

    private class CallInListener extends PhoneStateListener {
        private CallInListener() {
        }

        public void onCallStateChanged(int paramInt, String paramString) {
            // �����ֻ��绰��״̬,���е绰����ʱ ��ȡ��ַ ��ʾ����
            if (paramInt == TelephonyManager.CALL_STATE_RINGING) {
                if (paramString.equals("888888")) {
                    mToast.show("");
                }
            } else if (paramInt == TelephonyManager.CALL_STATE_IDLE) {
                // ����״̬��ʱ��,ʹ��ʾ���ĵ�ַ��ʧ
                mToast.hide();
            }
        }
    }

    private class CallOutReceiver extends BroadcastReceiver {
        private CallOutReceiver() {
        }

        public void onReceive(Context paramContext, Intent paramIntent) {
            if (paramIntent.getStringExtra("android.intent.extra.PHONE_NUMBER").equals("888888"))
                NumberLocationService.this.mToast.show("");
        }
    }
}