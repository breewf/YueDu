package com.ghy.yuedu.listener;

import android.view.MotionEvent;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by GHY on 2015/7/29.
 */
public class DoubleClickListener {

    private int clickCount;
    private long firstClickTime;
    private int CLICK_DELAY = 300;
    private final static int MOVE_OFFSET = 20;
    private float mLastMotionY;
    private float mLastMotionX;
    private Timer cleanClickTimer = new Timer();

    private OnDoubleClickListener listener;

    public DoubleClickListener(OnDoubleClickListener l){
        listener = l;
    }

    public interface OnDoubleClickListener{
        void onDoubleClick();
    }

    public void dispatchTouchEvent(MotionEvent event){
        final float y = event.getY();
        final float x = event.getX();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {

            mLastMotionY = y;
            mLastMotionX = x;

            clickCount++;
            if(clickCount == 1){
                firstClickTime = System.currentTimeMillis();
                //超过监听时间100MS还没有再次点击，则将点击次数，点击事件清零。
                cleanClickTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        clickCount = 0;
                        firstClickTime = 0;
                    }
                }, CLICK_DELAY+100);
            }else if(clickCount == 2){
                long secondClickTime = System.currentTimeMillis();
                if (secondClickTime - firstClickTime <= CLICK_DELAY) {
                    listener.onDoubleClick();
                }
                clickCount = 0;
                firstClickTime = 0;
            }
        }

        if(event.getAction() == MotionEvent.ACTION_MOVE){
            final int yDiff = (int) Math.abs(y - mLastMotionY);
            final int xDiff = (int) Math.abs(x - mLastMotionX);
            boolean yMoved = yDiff > MOVE_OFFSET;
            boolean xMoved = xDiff > MOVE_OFFSET;
            // 判断是否是移动
            if (yMoved || xMoved) {
                clickCount = 0;
                firstClickTime = 0;
            }
        }
    }
}
