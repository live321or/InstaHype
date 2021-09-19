package com.likesmm.instahype.welcome;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.likesmm.instahype.MainActivity;
import com.likesmm.instahype.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationFragment extends Fragment {
    TextView t;
    TextInputEditText Email;
    TextInputEditText Password1;
    RelativeLayout root;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ResourceAsColor")
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registration, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Регистрация");

        TextView textView = view.findViewById(R.id.policy);
//        MainActivity.prefConfig.displayToast(getResources().getConfiguration().locale.toString());
        if (foo(getResources().getConfiguration().locale.toString())) {
            String text = "Нажимая кнопку ''Зарегистрироваться'' вы соглашаетесь с лицензионным соглашением  и правилами использования сервиса.";

            textView.setText(ss(text,56,81,84,115));
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }else{
            String text ="By clicking the ''Register'' button, you agree to the license agreement and the terms of use of the service.";

            textView.setText(ss(text,53,71,76,107));
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
        Email = view.findViewById(R.id.r_email);
        Password1 = view.findViewById(R.id.r_password);
//        Password2 = view.findViewById(R.id.r_password_repeat);
        root = view.findViewById(R.id.rootReg);

//        setOnKeyListener(Email);
        setOnKeyListener(Password1);
//        setOnKeyListener(Password2);
        return view;
    }

    private boolean foo (String line) {
        String[] strings = line.split("_"); // делим строку на отдельные слова
        for (String word : strings) {

            if (word.matches("ru")||word.matches("RU")) {  // проверяем в цикле каждое отдельное слово
                return true;
            }
        }
        return false;
    }

    private SpannableString ss(String text,int one,int two,int three,int four){
        SpannableString ss =new SpannableString(text);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                RegistrationFragmentDirections.ActionRegistrationFragmentToPolicyFragment action = RegistrationFragmentDirections.actionRegistrationFragmentToPolicyFragment();
                action.setResId(1);
                NavHostFragment.findNavController(RegistrationFragment.this)
                        .navigate(action);
            }
        };
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                RegistrationFragmentDirections.ActionRegistrationFragmentToPolicyFragment action = RegistrationFragmentDirections.actionRegistrationFragmentToPolicyFragment();
                action.setResId(2);
                NavHostFragment.findNavController(RegistrationFragment.this)
                        .navigate(action);
            }
        };

        ss.setSpan(clickableSpan1, one, two, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(clickableSpan2, three, four, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.r_registration).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(), "Кнопка", Toast.LENGTH_SHORT).show();
                onRegistration();

            }
        });
    }

    private void setOnKeyListener(View view) {
        view.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                closeKeyboard();
                return true;
            }
            return false;
        });
    }

    private void closeKeyboard() {
        View view = this.getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void onRegistration() {
        closeKeyboard();
        String email = Email.getText().toString().trim();
        String password = Password1.getText().toString().trim();
//        String password2 = Password2.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Snackbar.make(root, "ВВЕДИТЕ ПОЧТУ",
                    Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (!isEmailValid(email)) {
            Snackbar.make(root, "ТАКОЙ ПОЧТЫ НЕ СУЩЕСТВУЕТ",
                    Snackbar.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Snackbar.make(root, "ВВЕДИТЕ ПАРОЛЬ",
                    Snackbar.LENGTH_SHORT).show();
            return;
        }
//        if(TextUtils.isEmpty(password2)){
//            Snackbar.make(root,"ПОВТОРИТЕ ПАРОЛЬ",
//                    Snackbar.LENGTH_SHORT).show();
//            return;
//        }

        Call<User> call = MainActivity.apiInterface.performRegistration(email, md5(password));

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                assert response.body() != null;
                if (response.body().getResponse().equals("200")) {
//                    MainActivity.prefConfig.displayToast("ВАНДУФЛ");
                    MainActivity.prefConfig.writeLoginStatus(true);
                    MainActivity.prefConfig.writeEmail(email);

                    NavHostFragment.findNavController(RegistrationFragment.this)
                            .navigate(R.id.action_registrationFragment_to_activity2);
                } else if (response.body().getResponse().equals("exist")) {
                    Snackbar.make(root, "ТАКАЯ ПОЧТА УЖЕ ЗАРЕГИСТРИРОВАНА",
                            Snackbar.LENGTH_LONG).show();
                } else if (response.code() == 406) {
                    MainActivity.prefConfig.displayToast("Registration failed: incorrect login/password content...");
                } else if (response.code() == 500) {
                    MainActivity.prefConfig.displayToast("Server Error");
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                MainActivity.prefConfig.displayToast("Server Error");
                Log.e("onFailure", t.getMessage());
            }
        });

    }

    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
