package com.example.university;

import android.view.View;

public interface SwipeCallback {

    void onSwipe(Direction direction);

    enum Direction {
        LEFT,
        RIGHT,
        UP,
        DOWN
    }
}
