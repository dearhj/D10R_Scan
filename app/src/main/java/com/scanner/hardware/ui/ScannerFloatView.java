package com.scanner.hardware.ui;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.scanner.hardware.MyApplication;
import com.scanner.hardware.R;
import com.scanner.hardware.util.ExtKt;

public class ScannerFloatView {
    private static ScannerFloatView floatView = null;
    private final Context mContext;
    private WindowManager mWindowManager;
    private WindowManager.LayoutParams wmParams;
    private View mView;
    private boolean isShow = false;
    private boolean isMove = true;
    private OnFloatClickListener mOnFloatClickListener;
    private ImageView ivLight;
    private ImageView ivDark;

    public interface OnFloatClickListener {
        void onCancelClick(View view);

        void onClick();
    }

    public void setOnFloatClickListener(OnFloatClickListener onFloatClickListener) {
        mOnFloatClickListener = onFloatClickListener;
    }


    private ScannerFloatView() {
        mContext = MyApplication.application;
    }

    public static synchronized ScannerFloatView getInstance() {
        if (floatView == null) floatView = new ScannerFloatView();
        return floatView;
    }

    public static void resize() {
        if (floatView != null) {
            ViewGroup.LayoutParams lp = floatView.ivLight.getLayoutParams();
            lp.width = (ExtKt.getFloatSize() + 2) * 60;
            lp.height = (ExtKt.getFloatSize() + 2) * 60;
            floatView.ivLight.setLayoutParams(lp);
            floatView.ivDark.setLayoutParams(lp);
        }
    }

    public void showFloatView() {
        if (isShow) return;
        mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        wmParams = new WindowManager.LayoutParams();
        wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        wmParams.gravity = Gravity.CENTER;
        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.x = mContext.getResources().getDisplayMetrics().widthPixels;
        wmParams.y = 0;

        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

        mView = LayoutInflater.from(mContext).inflate(R.layout.layout_float_click, null);

        ivLight = mView.findViewById(R.id.iv_light);
        ivDark = mView.findViewById(R.id.iv_dark);
        ivLight.setVisibility(View.GONE);
        ivDark.setVisibility(View.VISIBLE);
        mWindowManager.addView(mView, wmParams);

        resize();
        mView.setOnTouchListener(new View.OnTouchListener() {
            float downX = 0;
            float downY = 0;
            int oddOffsetX = 0;
            int oddOffsetY = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN -> {
                        downX = event.getX();
                        downY = event.getY();
                        oddOffsetX = wmParams.x;
                        oddOffsetY = wmParams.y;
                        if (mView != null && mOnFloatClickListener != null) {
                            ivLight.setVisibility(View.VISIBLE);
                            ivDark.setVisibility(View.GONE);
                            mWindowManager.updateViewLayout(mView, wmParams);
                            mOnFloatClickListener.onClick();
                        }
                    }
                    case MotionEvent.ACTION_MOVE -> {
                        if (!isMove) break;
                        float moveX = event.getX();
                        float moveY = event.getY();
                        wmParams.x += (moveX - downX) / 3;
                        wmParams.y += (moveY - downY) / 3;
                        int scaledTouchSlop = ViewConfiguration.get(mContext).getScaledTouchSlop();
                        if (Math.abs(moveX - downX) > scaledTouchSlop || Math.abs(moveY - downY) > scaledTouchSlop) { //移动超过了阈值，表示移动了
                            isMove = true;
                            if (mOnFloatClickListener != null) {
                                if (mView != null) {
                                    ivLight.setVisibility(View.GONE);
                                    ivDark.setVisibility(View.VISIBLE);
                                    mWindowManager.updateViewLayout(mView, wmParams);
                                }
                                mOnFloatClickListener.onCancelClick(mView);
                            }
                        }
                    }
                    case MotionEvent.ACTION_UP -> {
                        int newOffsetX = wmParams.x;
                        int newOffsetY = wmParams.y;
                        if (Math.abs(newOffsetX - oddOffsetX) <= 20 || Math.abs(newOffsetY - oddOffsetY) <= 20) {
                            isMove = true;
                            if (mView != null && mOnFloatClickListener != null) {
                                ivLight.setVisibility(View.GONE);
                                ivDark.setVisibility(View.VISIBLE);
                                mWindowManager.updateViewLayout(mView, wmParams);
                                mOnFloatClickListener.onCancelClick(mView);
                            }
                        }
                    }
                }
                return true;
            }

        });
        isShow = true;
    }

    public void hideFloatView() {
        if (mView != null && mWindowManager != null && isShow) {
            mWindowManager.removeView(mView);
            isShow = false;
        }
    }
}
