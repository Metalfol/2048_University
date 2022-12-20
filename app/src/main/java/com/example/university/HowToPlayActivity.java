package com.example.university;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.Locale;

public class HowToPlayActivity extends AppCompatActivity implements HowToPlayFragment3.OnSelectedButtonListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        loadLocale();

        tabLayout = findViewById(R.id.table_layout);
        viewPager = findViewById(R.id.view_pager);

        tabLayout.setupWithViewPager(viewPager);

        AdapterFragment adapterFragment = new AdapterFragment(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapterFragment.addFragment(new HowToPlayFragment1(), getString(R.string.step1));
        adapterFragment.addFragment(new HowToPlayFragment2(), getString(R.string.step2));
        adapterFragment.addFragment(new HowToPlayFragment3(), getString(R.string.step3));
        viewPager.setAdapter(adapterFragment);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.app_name));

/*        //смартфон в портретном режиме
        if (findViewById(getFragmentManager(HowToPlayFragment1.class)) != null){
            FragmentManager manager = this.getSupportFragmentManager();
            manager.beginTransaction().hide(manager.findFragmentById(R.id.frg_1)).show(R.id.frg_2).commit();
        }*/
    }

    private void loadLocale() {
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        String language = prefs.getString("My_Lang", "");
        setLocale(language);
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View view) {

    }
}
