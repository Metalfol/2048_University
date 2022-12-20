//класс отвечает за окно с меню игры (главная активность)
package com.example.university;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button button1;
    Button button2;
    Button button3;
    Button button4;
    TextView MText;
    TextView DText;
    TextView PText;
    ImageView imageView;
    Animation animOne;
    CoordinatorLayout act2;
    GameManager gameManager;

    public static final String[] Languages = {"Выберете язык", "Русский", "Английский"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, GameActivity.class);
        Intent intent2 = new Intent(this, HowToPlayActivity.class);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));

        MText = (TextView)findViewById(R.id.MText);
        DText = (TextView)findViewById(R.id.DText);
        PText = (TextView)findViewById(R.id.PText);
        button1 = (Button)findViewById(R.id.button1);
        button2 = (Button)findViewById(R.id.button2);
        button3 = (Button)findViewById(R.id.button3);
        button4 = (Button)findViewById(R.id.button4);

        imageView=(ImageView)findViewById(R.id.imageView);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        //Подгружаем анимацию:
        animOne = AnimationUtils.loadAnimation(this,R.anim.animation_one);

        //кнопка "СЮРПРИЗ" (отображение анимации и появление текста внизу)
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PText.setText(R.string.but1text);
                imageView.setVisibility(View.VISIBLE);
                imageView.startAnimation(animOne);
                imageView.setVisibility(View.INVISIBLE);
            }
        });

        //НАЧАТЬ ИГРУ
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent2);
            }
        });

        //кнопка "ЯЗЫК" (смена языка с использованием всплывающего окна выбора языка)
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLanguageDialog();
            }
        });
    }

    //функция выбора языка приложения
    private void showChangeLanguageDialog() {
        final String[] listItems = {getResources().getString(R.string.language_ru), getResources().getString(R.string.language_en)};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
        mBuilder.setTitle(R.string.change_language);
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    //Русский
                    setLocale("ru");
                    recreate();
                }
                else if (i == 1){
                    //Английский
                    setLocale("en");
                    recreate();
                }
                dialogInterface.dismiss();
            }
        });

        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }


    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        SharedPreferences.Editor editor = getSharedPreferences("Settings", MODE_PRIVATE).edit();
        editor.putString("My_Lang", lang);
        editor.apply();
    }

    public void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLocale(language);
    }

    @Override
    public void onClick(View v) {
    }

    public void onResume(Intent intent) {
        super.onResume();
    }
}