//основной класс, отображения интерфейса игры
package com.example.university;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import com.example.university.Sprites.EndGame;
import com.example.university.Sprites.Grid;
import com.example.university.Sprites.Score;

public class GameManager extends SurfaceView implements SurfaceHolder.Callback, SwipeCallback, View.OnClickListener, GameManagerCallback {

    private static final String APP_NAME = "2048";

    private MainThread thread;
    private Grid grid;
    private int scWidth, scHeight, standardSize;
    private TileManager tileManager;
    private boolean endGame = false;
    private EndGame endGameSprite;
    private int menuButtonX, menuButtonY, menuButtonSize;
    private Score score;

    private SwipeListener swipe;

    public GameManager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLongClickable(true);

        getHolder().addCallback(this);
        swipe = new SwipeListener(getContext(), this);

        DisplayMetrics dn = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dn);
        scWidth = dn.widthPixels;
        scHeight = dn.heightPixels;
        standardSize = (int) (scWidth * .88) / 4;

        grid = new Grid(getResources(), scWidth, scHeight, standardSize);
        tileManager = new TileManager(getResources(), standardSize, scWidth, scHeight, this);
        endGameSprite = new EndGame(getResources(), scWidth, scHeight);
        score = new Score(getResources(), scWidth, scHeight, standardSize, getContext().getSharedPreferences(APP_NAME, MODE_PRIVATE));

    }

    public void initGame() {
        endGame = false;
        tileManager.initGame();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        thread = new MainThread(holder, this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int i, int i1, int i2) {
        thread.setSurfaceHolder(holder);
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        boolean retry = true;
        while(retry) {
            try {
                thread.setRunning(false);
                thread.join();
                retry = false;
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        if (!endGame) {
            SharedPreferences save = getContext().getSharedPreferences("Save", MODE_PRIVATE);
            SharedPreferences.Editor editor = save.edit();
            editor.putInt("Level", 1);
            editor.commit();
            tileManager.update();
        }
    }

    //отрисовка объектов на игровом экране
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.drawRGB(60, 179, 113);
        grid.draw(canvas);
        /*canvas.drawBitmap(menuButton, menuButtonX, menuButtonY, null);*/
        tileManager.draw(canvas);
        score.draw(canvas);
        if (endGame) {
            endGameSprite.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(endGame){
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                initGame();
            }
        }
        else {
            swipe.onTouchEvent(event);
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onSwipe(Direction direction) {
        tileManager.onSwipe(direction);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void gameOver() {
        endGame = true;
    }

    @Override
    public void updateScore(int delta) {
        score.updateScore(delta);
    }
}
