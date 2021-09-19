package com.likesmm.instahype.welcome;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.likesmm.instahype.MainActivity;
import com.likesmm.instahype.PrefConfig;
import com.likesmm.instahype.R;

public class WelcomeFragment extends Fragment {
TextView welcomeTitle;
    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_welcome, container, false);

        welcomeTitle=view.findViewById(R.id.welcome_title);
        Spannable wordtoSpan =  new SpannableString( "Добро пожаловать в InstaHype!");
        ForegroundColorSpan colorSpan= new ForegroundColorSpan(Color.BLACK);
        ForegroundColorSpan colorSpan1= new ForegroundColorSpan(Color.BLACK);
        wordtoSpan.setSpan(colorSpan, 0, 18, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(colorSpan1, 28, 29, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        welcomeTitle.setText(wordtoSpan);
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.button_registration).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(WelcomeFragment.this)
                        .navigate(R.id.action_welcomeFragment_to_registrationFragment);
            }
        });
        view.findViewById(R.id.button_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(WelcomeFragment.this)
                        .navigate(R.id.action_welcomeFragment_to_loginFragment);
            }
        });
    }

    @Override
    public void onResume() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        super.onResume();
    }
}