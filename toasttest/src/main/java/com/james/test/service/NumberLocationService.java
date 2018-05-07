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
            // 监听手机电话的状态,当有电话打入时 获取地址 显示出来
            if (paramInt == TelephonyManager.CALL_STATE_RINGING) {
                if (paramString.equals("888888")) {
                    mToast.show("");
                }
            } else if (paramInt == TelephonyManager.CALL_STATE_IDLE) {
                // 闲置状态的时候,使显示出的地址消失
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