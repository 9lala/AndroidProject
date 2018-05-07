package com.james.test.activty;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.james.test.R;
import com.james.test.service.NumberLocationService;
import com.james.test.utils.PermissionUtil;
import com.james.test.utils.ServiceStateUtils;
import com.james.test.view.ToastShowAddress;

import java.security.Permissions;

public class MainActivity extends Activity {
    private ToastShowAddress mToast;
    private ToggleButton mTButton;


    protected void onCreate(Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_main);
        this.mToast = new ToastShowAddress(this);
        mTButton = findViewById(R.id.id_tbutton);
        initData();
        initListener();
    }

    private void initListener() {
        mTButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    startService(new Intent(MainActivity.this, NumberLocationService.class));
                } else {
                    stopService(new Intent(MainActivity.this, NumberLocationService.class));
                }
            }
        });
    }

    private void initData() {
        if (ServiceStateUtils.isRunning(this, NumberLocationService.class)) {
            stopService(new Intent(this, NumberLocationService.class));
            mTButton.setChecked(false);
        }
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityForResult(intent, 1);
            } else {

            }
        }
    }

}