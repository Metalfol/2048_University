//класс отвечает за появление изображения цифры на поле игры
package com.example.university.Sprites;

import android.graphics.Canvas;

import com.example.university.TileManagerCallback;

import java.util.Random;

public class Tile implements Sprite {

    private int screenWidth, screenHeight, standardSize;
    private TileManagerCallback callback;
    private int count = 1;
    private int currentX, correntY;
    private int destX, destY;
    private boolean moving = false;
    private int speed = 100;
    private boolean increment = false;

/*    public Tile(int standardSize, int screenWidth, int screenHeight, TileManagerCallback callback, int matrixX, int matrixY, int count) {
        this(standardSize, screenWidth, screenHeight, callback, matrixX, matrixY);
        this.count = count;
    }*/

    public Tile(int standardSize, int screenWidth, int screenHeight, TileManagerCallback callback, int matrixX, int matrixY) {
        this.standardSize = standardSize;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.callback = callback;
        currentX = destX = screenWidth / 2 - 2 * standardSize + matrixY * standardSize;
        correntY = destY = screenHeight / 2 - 2 * standardSize + matrixX * standardSize;
        int chance = new Random().nextInt(100);
        if(chance >= 90) {
            count = 2;
        }
    }

    public void move(int matrixX, int matrixY) {
        moving = true;
        destX = screenWidth / 2 - 2 * standardSize + matrixY * standardSize;
        destY = screenHeight / 2 - 2 * standardSize + matrixX * standardSize;
    }

    public int getValue() {
        return count;
    }

    public Tile increment() {
        increment = true;
        return this;
    }

    public boolean toIncrement() {
        return increment;
    }

    //отрисовка изображения цифры
    @Override
    public void draw(Canvas canvas) {
        canvas.drawBitmap(callback.getBitmap(count), currentX, correntY, null);
        if (moving && currentX == destX && correntY == destY) {
            moving = false;
            if(increment) {
                count++;
                increment = false;
                int amount = (int) Math.pow(2, count);
                callback.updateScore(amount);
            }
            callback.finishedMoving(this);
        }
    }

    @Override
    public void update() {
        if(currentX < destX) {
            if (currentX + speed > destX) {
                currentX = destX;
            }
            else {
                currentX += speed;
            }
        }
        else if (currentX > destX) {
            if(currentX - speed < destX) {
                currentX = destX;
            }
            else {
                currentX -= speed;
            }
        }

        if (correntY < destY) {
            if (correntY + speed > destY) {
                correntY = destY;
            }
            else {
                correntY += speed;
            }
        }
        else if (correntY > destY) {
            if (correntY - speed < destY) {
                correntY = destY;
            }
            else {
                correntY -= speed;
            }
        }
    }
}
