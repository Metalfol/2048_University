package com.example.university;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HowToPlayFragment3 extends Fragment implements View.OnClickListener {

    Button button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_how_to_play3, container, false);
    }


    @Override
    public void onClick(View view) {

    };

    public interface OnSelectedButtonListener {
        void onClick(View view);
    }
}