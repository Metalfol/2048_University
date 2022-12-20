//класс создания и отрисовки изображений "Счет" и "Лучший счет" в интерфейсе игры
package com.example.university.Sprites;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.university.R;

public class Score implements Sprite {

    private static final String SCORE_PREF = "Score pref";

    private int screenWidth, screenHeight, standardSize;
    private Bitmap bmpScore, bmpTopScore;
    private int score, topScore;
    private SharedPreferences prefs;
    private Paint paint;

    public Score(Resources resources, int screenWidth, int screenHeight, int standardSize, SharedPreferences prefs) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.standardSize = standardSize;
        this.prefs = prefs;

        topScore = prefs.getInt(SCORE_PREF, 0);
        //считывание данных размерности ширины и высоты с dimens.xml
        int width = (int) resources.getDimension(R.dimen.score_label_width);
        int height = (int) resources.getDimension(R.dimen.score_label_height);
        int tWidth = (int) resources.getDimension(R.dimen.topScore_label_width);
        int tHeight = (int) resources.getDimension(R.dimen.topScore_label_height);

        //подзагрузка каринки "Счет" и ее создание с данными размерности
        Bitmap sc = BitmapFactory.decodeResource(resources, R.drawable.score_nr);
        bmpScore = Bitmap.createScaledBitmap(sc, width, height, false);

        //подзагрузка каринки "Лучший счет" и ее создание с данными размерности
        Bitmap tsc = BitmapFactory.decodeResource(resources, R.drawable.topscore_nr);
        bmpTopScore = Bitmap.createScaledBitmap(tsc, tWidth, tHeight, false);

        //установка цвета, стиля и размера для подсчета очков
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(resources.getDimension(R.dimen.score_text_size));
    }

    //отрисовка изображений и расположение на игровом интерфейсе
    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(bmpScore, screenWidth / 4 - bmpScore.getWidth() / 2, bmpScore.getHeight(), null );
        canvas.drawBitmap(bmpTopScore, 3 * screenWidth / 4 - bmpTopScore.getWidth() / 2, bmpTopScore.getHeight(), null);

        int width1 = (int) paint.measureText(String.valueOf(score));
        int width2 = (int) paint.measureText(String.valueOf(topScore));
        canvas.drawText(String.valueOf(score), screenWidth / 4 - width1 / 2, bmpScore.getHeight() * 4, paint);
        canvas.drawText(String.valueOf(topScore), 3 * screenWidth / 4 - width2 / 2, bmpTopScore.getHeight() * 4, paint);
    }

    @Override
    public void update() {

    }

    public void updateScore(int delta) {
        score += delta;
        checkTopScore();
    }

    public void checkTopScore() {
        topScore = prefs.getInt(SCORE_PREF, 0);
        if (topScore < score) {
            prefs.edit().putInt(SCORE_PREF, score).apply();
            topScore = score;
        }
    }
}
