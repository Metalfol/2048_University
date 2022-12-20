package com.example.university;

import android.graphics.Bitmap;

import com.example.university.Sprites.Tile;

public interface TileManagerCallback {
    Bitmap getBitmap(int count);
    void finishedMoving(Tile t);
    void updateScore(int delta);
}
