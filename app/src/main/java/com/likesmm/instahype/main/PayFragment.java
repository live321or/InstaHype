package com.likesmm.instahype.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.likesmm.instahype.MainActivity;
import com.likesmm.instahype.R;
import com.likesmm.instahype.welcome.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.likesmm.instahype.main.Activity.DATA1;

public class PayFragment extends Fragment {
    MaterialButton button;
    Button btnPay;
    Button btnPay1;
    TextInputEditText payText;
    TextView pBalance;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pay, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Пополнить Баланс");

        payText = view.findViewById(R.id.pay);
        pBalance = view.findViewById(R.id.pBalance);
        btnPay = view.findViewById(R.id.button_pay);
        btnPay.setOnClickListener(v -> {
            closeKeyboard();
            String p1 = payText.getText().toString();
            if (!p1.isEmpty()) {
//                DATA1.paySUm=Integer.parseInt(p1);
                DATA1.paySum = p1;
                NavHostFragment.findNavController(PayFragment.this)
                        .navigate(R.id.action_payFragment_to_webViewFragment);
            }
        });
//        btnPay1 = view.findViewById(R.id.button_pay1);
//        btnPay1.setOnClickListener(v -> {
//                NavHostFragment.findNavController(PayFragment.this)
//                        .navigate(R.id.action_payFragment_to_ok);
//
//        });

        button = view.findViewById(R.id.button_hist);
        button.setOnClickListener(v -> {
            NavHostFragment.findNavController(PayFragment.this)
                    .navigate(R.id.action_payFragment_to_payHistoryFragment);
        });
        setBalance();
        return view;
    }

    //    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        view.findViewById(R.id.button_second).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                NavHostFragment.findNavController(SecondFragment.this)
//                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
//            }
//        });
//    }
    private void setBalance() {
        Call<User> call = MainActivity.apiInterface.getBalance(MainActivity.prefConfig.readEmail());

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
//                    MainActivity.prefConfig.displayToast("ВАНДУФЛ");
                    pBalance.setText("Баланс: " + response.body().getBalance() + " Руб.");
                } else if (response.code() == 404) {
                    MainActivity.prefConfig.displayToast("404");
                } else if (response.code() == 406) {
                    MainActivity.prefConfig.displayToast("Registration failed: incorrect login/password content...");
                } else if (response.code() == 500) {
                    MainActivity.prefConfig.displayToast("Server Error");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.print(t);
            }
        });
    }

    private void closeKeyboard() {
        View view = this.getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}