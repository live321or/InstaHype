package com.likesmm.instahype.main;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.likesmm.instahype.MainActivity;
import com.likesmm.instahype.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.likesmm.instahype.main.Activity.DATA1;

public class WebViewFragment extends Fragment {
    WebView webView;
    @SuppressLint("SetJavaScriptEnabled")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.web_view_fragment, container, false);
        webView=view.findViewById(R.id.wb);
        webView.getSettings().setJavaScriptEnabled(true);
        String url = "https://like-smm.ru/android/enotPay.php";
        String postData = null;
        try {
            postData = "price=" + URLEncoder.encode(DATA1.paySum, "UTF-8")
                   + "&email=" + URLEncoder.encode(MainActivity.prefConfig.readEmail(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        webView.setWebChromeClient(new WebChromeClient());
        webView.postUrl(url,postData.getBytes());
        return view;
    }
}
