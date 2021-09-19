package com.likesmm.instahype.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.likesmm.instahype.R;


public class BlankFragment extends Fragment   {
    WebView webView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Потдержка");
        webView=view.findViewById(R.id.wbSend);
        webView.getSettings().setJavaScriptEnabled(true);
        String url = "https://t.me/E34525i";
        
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(url);
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(FirstFragment.this)
//                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
//            }
//        });
    }

//    @Override
//    public void idMenuItem(int id) {
//
//    }
}