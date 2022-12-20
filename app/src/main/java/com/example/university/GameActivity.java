//класс отвечает за окно с игрой (вторая активность)
package com.example.university;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import android.content.SharedPreferences;

import java.util.Locale;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    final String TAG = "lifecycle";

    Button btnPause;
    private TileManager tileManager;
    private GameManager gameManager;
    private boolean endGame = false;

    public void initGame() {
        endGame = false;
        tileManager.initGame();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main2);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));
        Log.d(TAG, "Activity создано");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = new Intent(this, MainActivity.class);
        Intent intent2 = new Intent(this, GameActivity.class);
        btnPause = (Button)findViewById(R.id.action_pause);
        switch (id) {
            case R.id.action_menu:
                //Toast.makeText(GameActivity.this, getString(R.string.action_menu), Toast.LENGTH_LONG).show();
                //startActivity(new Intent(GameActivity.this, MainActivity.class));
                startActivity(intent);
                break;
            case R.id.action_pause:
                /*Toast.makeText(GameActivity.this, getString(R.string.action_pause), Toast.LENGTH_LONG).show();*/
                /*Intent intent3 = new Intent(this, GameActivity.class);
                onPause();*/
                AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                builder.setTitle("ПАУЗА")
                        .setCancelable(false)
                        .setMessage("Чтобы продолжить, нажмите кнопку 'ПРОДОЛЖИТЬ'")
                        .setPositiveButton("Продолжить", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // Закрываем диалоговое окно
                    dialog.dismiss();
                }
            });
                builder.show();
                break;
            case R.id.action_settings:
                //Toast.makeText(GameActivity.this, getString(R.string.action_settings), Toast.LENGTH_LONG).show();
                showChangeLanguageDialog();
                break;
            case R.id.action_information:
                Toast.makeText(GameActivity.this, getString(R.string.action_information_dop), Toast.LENGTH_LONG).show();
                break;
            case R.id.action_restart:
                finish();
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //функция выбора языка приложения
    private void showChangeLanguageDialog() {
        final String[] listItems = {getResources().getString(R.string.language_ru), getResources().getString(R.string.language_en)};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(GameActivity.this);
        mBuilder.setTitle(R.string.change_language_1)
                .setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
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

    protected void onStart() {
        super.onStart();
        Log.d(TAG, "Activity становится видимым");
    }

    protected void onPause() {
        super.onPause();
        Log.d(TAG, "Activity приостановлено (состояние Paused)");
    }

    protected void onResume() {
        super.onResume();
        Log.d(TAG, "Activity получает фокус (состояние Resumed)");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Activity уничтожено");
    }

    @Override
    public void onClick(View view) {

    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "Activity сохранено");
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "Activity восстановлено");
    }
}