package com.james.test.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.Toast;

import com.james.test.R;

public class ToastShowAddress
        implements View.OnTouchListener {
    private static final long DURATION = 400L;
    private Context con;
    private float downX;
    private float downY;
    private ImageView iv_1;
    private WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
    private View mView;
    private WindowManager mWM;
    private Toast toast;

    public ToastShowAddress(Context paramContext) {
        this.con = paramContext;
        this.mWM = ((WindowManager) paramContext.getSystemService(Context.WINDOW_SERVICE));
        this.mView = View.inflate(paramContext, R.layout.toast_address, null);
        mParams.height = -2;
        mParams.width = -2;
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                // | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        mParams.format = -3;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            mParams.type = LayoutParams.TYPE_PHONE;
        }
        mParams.gravity = 17;
        mParams.setTitle("Toast");
        this.mView.setOnTouchListener(this);
    }

    private ObjectAnimator alpha(ImageView paramImageView) {
        ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(paramImageView, "alpha", new float[]{1.0F, 0.0F});
        localObjectAnimator.setRepeatCount(-1);
        localObjectAnimator.setRepeatMode(ValueAnimator.RESTART);
        localObjectAnimator.setDuration(400L);
        return localObjectAnimator;
    }

    private ObjectAnimator scaleX(ImageView paramImageView) {
        ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(paramImageView, "scaleX", new float[]{1.0F, 3.0F});
        localObjectAnimator.setRepeatCount(-1);
        localObjectAnimator.setRepeatMode(ValueAnimator.RESTART);
        localObjectAnimator.setDuration(400L);
        return localObjectAnimator;
    }

    private ObjectAnimator scaleY(ImageView paramImageView) {
        ObjectAnimator localObjectAnimator = ObjectAnimator.ofFloat(paramImageView, "scaleY", new float[]{1.0F, 3.0F});
        localObjectAnimator.setRepeatCount(-1);
        localObjectAnimator.setRepeatMode(ValueAnimator.RESTART);
        localObjectAnimator.setDuration(400L);
        return localObjectAnimator;
    }

    private void setAnimation(ImageView paramImageView) {
        AnimatorSet localAnimatorSet = new AnimatorSet();
        Animator[] arrayOfAnimator = new Animator[3];
        arrayOfAnimator[0] = alpha(paramImageView);
        arrayOfAnimator[1] = scaleX(paramImageView);
        arrayOfAnimator[2] = scaleY(paramImageView);
        localAnimatorSet.playTogether(arrayOfAnimator);
        localAnimatorSet.start();
    }

    public void hide() {
        if (this.mView.getParent() != null)
            this.mWM.removeView(this.mView);
//        if (toast != null)
//            toast.cancel();
    }

    public boolean onTouch(View paramView, MotionEvent paramMotionEvent) {
        switch (paramMotionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = paramMotionEvent.getRawX();
                downY = paramMotionEvent.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                float MoveX = paramMotionEvent.getRawX();
                float MoveY = paramMotionEvent.getRawY();
                float subX = MoveX - downX;
                float subY = MoveY - downY;
                mParams.x += subX;
                mParams.y += subY;
                mWM.updateViewLayout(mView, mParams);
                downX = MoveX;
                downY = MoveY;
                break;
        }
        return true;
    }
//        while (true) {
//            this.downX = paramMotionEvent.getRawX();
//            this.downY = paramMotionEvent.getRawY();
//            float f1 = paramMotionEvent.getRawX();
//            float f2 = paramMotionEvent.getRawY();
//            float f3 = f1 - this.downX;
//            float f4 = f2 - this.downY;
//            WindowManager.LayoutParams localLayoutParams1 = this.mParams;
//            localLayoutParams1.x = ((int) (f3 + localLayoutParams1.x));
//            WindowManager.LayoutParams localLayoutParams2 = this.mParams;
//            localLayoutParams2.y = ((int) (f4 + localLayoutParams2.y));
//            this.mWM.updateViewLayout(this.mView, this.mParams);
//            this.downX = f1;
//            this.downY = f2;
//        }
//    }

    public void show(String paramString) {
        hide();
        this.iv_1 = ((ImageView) this.mView.findViewById(R.id.tv_toast_address));
        setAnimation(this.iv_1);
        this.mWM.addView(this.mView, this.mParams);

//        toast = new Toast(con);
//        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
//        toast.setDuration(Toast.LENGTH_LONG);
//        toast.setView(this.mView);
//        toast.show();
    }
}