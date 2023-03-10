package com.example.university;

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class SwipeListener implements GestureDetector.OnGestureListener {

    private GestureDetector detector;
    private SwipeCallback callback;

    public SwipeListener(Context context, SwipeCallback callback) {
        this.callback = callback;
        detector = new GestureDetector(context, this);
    }

    public void onTouchEvent(MotionEvent e) {
        detector.onTouchEvent(e);
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float vX, float vY) {
        if(Math.abs(vX) > Math.abs(vY)) {
            if(vX > 0) {
                callback.onSwipe(SwipeCallback.Direction.RIGHT);
            }
            else {
                callback.onSwipe(SwipeCallback.Direction.LEFT);
            }
        }
        else {
            if(vY > 0) {
                callback.onSwipe(SwipeCallback.Direction.DOWN);
            }
            else {
                callback.onSwipe(SwipeCallback.Direction.UP);
            }
        }
        return false;
    }
}
