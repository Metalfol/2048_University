//класс отвечает за логику игры и работу с цифрами
package com.example.university;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.example.university.Sprites.Sprite;
import com.example.university.Sprites.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class TileManager implements TileManagerCallback, Sprite {

    private Resources resources;
    private int standardSize, screenWidth, screenHeight;
    private ArrayList<Integer> drawables = new ArrayList<>();
    private HashMap<Integer, Bitmap> tileBitmaps = new HashMap<>();
    private Tile[][] matrix = new Tile[4][4];
    private boolean moving = false;
    private ArrayList<Tile> movingTiles;
    private boolean toSpawn = false;
    private boolean endGame = false;
    private GameManagerCallback callback;

    public TileManager(Resources resources, int standardSize, int screenWidth, int screenHeight, GameManagerCallback callback) {
        this.resources = resources;
        this. standardSize = standardSize;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.callback = callback;
        initBitmaps();

        initGame();
    }

    //загрузка изображений цифр
    private void initBitmaps() {

        drawables.add(R.drawable.one_nr);
        drawables.add(R.drawable.two_nr);
        drawables.add(R.drawable.three_nr);
        drawables.add(R.drawable.four_nr);
        drawables.add(R.drawable.five_nr);
        drawables.add(R.drawable.six_nr);
        drawables.add(R.drawable.seven_nr);
        drawables.add(R.drawable.eight_nr);
        drawables.add(R.drawable.nine_nr);
        drawables.add(R.drawable.ten_nr);
        drawables.add(R.drawable.eleven_nr);
        drawables.add(R.drawable.twelve_nr);
        drawables.add(R.drawable.thirteen_nr);
        drawables.add(R.drawable.fourteen_nr);
        drawables.add(R.drawable.fifteen_nr);
        drawables.add(R.drawable.sixteen_nr);

        for(int i =1; i <= 16; i++) {
            Bitmap bmp = BitmapFactory.decodeResource(resources, drawables.get(i-1));
            Bitmap tileBmp = Bitmap.createScaledBitmap(bmp, standardSize, standardSize, false);
            tileBitmaps.put(i, tileBmp);
        }
    }

    //появление изображений с цифрами
    public void initGame() {
        matrix = new Tile[4][4];
        movingTiles = new ArrayList<>();

        for(int i = 0; i < 2; i++){
            int x = new Random().nextInt(4);
            int y = new Random().nextInt(4);
            if(matrix[x][y] == null) {
                Tile tile = new Tile(standardSize, screenWidth, screenHeight, this, x, y);
                matrix[x][y] = tile;
            }
            else {
                i--;
            }
        }

/*        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if(i != 3 || j != 3) {
                    Tile t = new Tile(standardSize, screenWidth, screenHeight, this, i, j, 3 * i + j + 4);
                    matrix[i][j] = t;
                }
            }
        }*/
    }

    //отрисовка изображений с цифрами
    @Override
    public void draw(Canvas canvas) {
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                if(matrix[i][j] != null) {
                    matrix[i][j].draw(canvas);
                }
            }
        }

        if(endGame) {
            callback.gameOver();
        }
    }

    //обновление цифр
    @Override
    public void update() {
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                if(matrix[i][j] != null) {
                    matrix[i][j].update();
                }
            }
        }
    }

    @Override
    public Bitmap getBitmap(int count) {
        return tileBitmaps.get(count);
    }

    //логика игры, передвижение и суммирование цифр
    public void onSwipe(SwipeCallback.Direction direction) {
        if(!moving) {
            moving = true;
            Tile[][] newMatrix = new Tile[4][4];

            switch (direction) {

                //передвижение и суммирование цифр "ВВЕРХ"
                case UP:
                    for (int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            if (matrix[i][j] != null) {
                                newMatrix[i][j] = matrix[i][j];
                                for (int k = i - 1; k >= 0; k--) {
                                    if (newMatrix[k][j] == null) {
                                        newMatrix[k][j] = matrix[i][j];
                                        if (newMatrix[k + 1][j] == matrix[i][j]) {
                                            newMatrix[k + 1][j] = null;
                                        }
                                    } else if (newMatrix[k][j].getValue() == matrix[i][j].getValue() && !newMatrix[k][j].toIncrement()) {
                                        newMatrix[k][j] = matrix[i][j].increment();
                                        if(newMatrix[k+1][j] == matrix[i][j]) {
                                            newMatrix[k+1][j] = null;
                                        }
                                    }
                                    else {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    for(int i = 0; i < 4; i++) {
                        for(int j = 0; j < 4; j++) {
                            Tile t = matrix[i][j];
                            Tile newT = null;
                            int matrixX = 0;
                            int matrixY = 0;
                            for(int a = 0; a < 4; a++) {
                                for(int b = 0; b < 4; b++) {
                                    if(newMatrix[a][b] == t) {
                                        newT = newMatrix[a][b];
                                        matrixX = a;
                                        matrixY = b;
                                        break;
                                    }
                                }
                            }
                            if(newT != null) {
                                movingTiles.add(t);
                                t.move(matrixX, matrixY);
                            }
                        }
                    }
                    break;

                //передвижение и суммирование цифр "ВНИЗ"
                case DOWN:
                    for(int i = 3; i >= 0; i--) {
                        for(int j = 0; j < 4; j ++) {
                            if(matrix[i][j] != null) {
                                newMatrix[i][j] = matrix[i][j];
                                for(int k = i + 1; k < 4; k++) {
                                    if(newMatrix[k][j] == null) {
                                        newMatrix[k][j] = matrix[i][j];
                                        if(newMatrix[k-1][j] == matrix[i][j]) {
                                            newMatrix[k-1][j] = null;
                                        }
                                    }
                                    else if (newMatrix[k][j].getValue() == matrix[i][j].getValue() && !newMatrix[k][j].toIncrement()) {
                                        newMatrix[k][j] = matrix[i][j].increment();
                                        if(newMatrix[k-1][j] == matrix[i][j]) {
                                            newMatrix[k-1][j] = null;
                                        }
                                    }
                                    else {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    for(int i = 3; i >= 0; i--) {
                        for (int j = 0; j < 4; j++) {
                            Tile t = matrix[i][j];
                            Tile newT = null;
                            int matrixX = 0;
                            int matrixY = 0;
                            for (int a = 0; a < 4; a++) {
                                for (int b = 0; b < 4; b++) {
                                    if(newMatrix[a][b] == t) {
                                        newT = newMatrix[a][b];
                                        matrixX = a;
                                        matrixY = b;
                                        break;
                                    }
                                }
                            }
                            if(newT != null) {
                                movingTiles.add(t);
                                t.move(matrixX, matrixY);
                            }
                        }
                    }
                    break;

                //передвижение и суммирование цифр "ВЛЕВО"
                case LEFT:
                    for(int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            if(matrix[i][j] != null) {
                                newMatrix[i][j] = matrix[i][j];
                                for (int k = j - 1; k >= 0; k--){
                                    if (newMatrix[i][k] == null) {
                                        newMatrix[i][k] = matrix[i][j];
                                        if(newMatrix[i][k+1] == matrix[i][j]) {
                                            newMatrix[i][k+1] = null;
                                        }
                                    }
                                    else if (newMatrix[i][k].getValue() == matrix[i][j].getValue() && !newMatrix[i][k].toIncrement()) {
                                        newMatrix[i][k] = matrix[i][j].increment();
                                        if(newMatrix[i][k+1] == matrix[i][j]) {
                                            newMatrix[i][k+1] = null;
                                        }
                                    }
                                    else {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    for(int i = 0; i < 4; i++) {
                        for (int j = 0; j < 4; j++) {
                            Tile t = matrix[i][j];
                            Tile newT = null;
                            int matrixX = 0;
                            int matrixY = 0;
                            for (int a = 0; a < 4; a++) {
                                for (int b = 0; b < 4; b++) {
                                    if(newMatrix[a][b] == t) {
                                        newT = newMatrix[a][b];
                                        matrixX = a;
                                        matrixY = b;
                                        break;
                                    }
                                }
                            }
                            if(newT != null) {
                                movingTiles.add(t);
                                t.move(matrixX, matrixY);
                            }
                        }
                    }
                    break;

                //передвижение и суммирование цифр "ВПРАВО"
                case RIGHT:
                    for (int i = 0; i < 4; i++) {
                        for (int j = 3; j >= 0; j--) {
                            if (matrix[i][j] != null) {
                                newMatrix[i][j] = matrix[i][j];
                                for (int k = j + 1; k < 4; k++) {
                                    if (newMatrix[i][k] == null) {
                                        newMatrix[i][k] = matrix[i][j];
                                        if (newMatrix[i][k-1] == matrix[i][j]) {
                                            newMatrix[i][k-1] = null;
                                        }
                                    }
                                    else if (newMatrix[i][k].getValue() == matrix[i][j].getValue() && !newMatrix[i][k].toIncrement()) {
                                        newMatrix[i][k] = matrix[i][j].increment();
                                        if (newMatrix[i][k-1] == matrix[i][j]){
                                            newMatrix[i][k-1] = null;
                                        }
                                    }
                                    else {
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    for (int i = 0; i < 4; i++) {
                        for (int j = 3; j >= 0; j--) {
                            Tile t = matrix[i][j];
                            Tile newT = null;
                            int matrixX = 0;
                            int matrixY = 0;
                            for (int a = 0; a < 4; a++) {
                                for (int b = 0; b < 4; b++) {
                                    if(newMatrix[a][b] == t) {
                                        newT = newMatrix[a][b];
                                        matrixX = a;
                                        matrixY = b;
                                        break;
                                    }
                                }
                            }
                            if(newT != null) {
                                movingTiles.add(t);
                                t.move(matrixX, matrixY);
                            }
                        }
                    }
                    break;
            }

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (newMatrix[i][j] != matrix[i][j]) {
                        toSpawn = true;
                        break;
                    }
                }
            }
            matrix = newMatrix;
        }
    }

    //окончание передвижение и проверка на появление цифры и конец игры
    @Override
    public void finishedMoving(Tile t) {
        movingTiles.remove(t);
        if(movingTiles.isEmpty()) {
            moving = false;
            spawn();
            checkEndGame();
        }
    }

    //проверка на проигрыш игры (заполненного поля)
    private void checkEndGame() {
        endGame = true;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (matrix[i][j] == null) {
                    endGame = false;
                    break;
                }
            }
        }

        if (endGame) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if((i > 0 && matrix[i-1][j].getValue() == matrix[i][j].getValue()) ||
                            (i < 3 && matrix[i+1][j].getValue() == matrix[i][j].getValue()) ||
                            (j < 0 && matrix[i][j-1].getValue() == matrix[i][j].getValue()) ||
                            (j < 3 && matrix[i][j+1].getValue() == matrix[i][j].getValue())) {
                        endGame = false;
                    }
                }
            }
        }
    }

    //появление цифры
    private void spawn() {
        if (toSpawn) {
            toSpawn = false;
            Tile t = null;
            while (t == null) {
                int x = new Random().nextInt(4);
                int y = new Random().nextInt(4);
                if (matrix[x][y] == null) {
                    t = new Tile(standardSize, screenWidth, screenHeight, this, x, y);
                    matrix[x][y] = t;
                }
            }
        }
    }

    @Override
    public void updateScore(int delta) {
        callback.updateScore(delta);
    }
}
