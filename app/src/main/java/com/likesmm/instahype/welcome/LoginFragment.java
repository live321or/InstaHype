package com.likesmm.instahype.welcome;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.likesmm.instahype.MainActivity;
import com.likesmm.instahype.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.likesmm.instahype.main.Activity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    EditText Email;
    TextInputEditText Password1,pas2;
    RelativeLayout root;
    String kostyl;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Вход");
        Email = view.findViewById(R.id.l_email);
        Email.setText(Activity.a);
        Password1 = view.findViewById(R.id.l_password);
//        pas2 = view.findViewById(R.id.l_p);
        root = view.findViewById(R.id.rootLogin);



        setOnKeyListener(Password1);
        return view;
    }
    private void setOnKeyListener(View view){
        view.setOnKeyListener((v, keyCode, event) -> {
//            Toast.makeText(getActivity(), "k1", Toast.LENGTH_SHORT).show();
            if(keyCode == KeyEvent.KEYCODE_ENTER){
//                Toast.makeText(getActivity(), "k2", Toast.LENGTH_SHORT).show();
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
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(), "Кнопка", Toast.LENGTH_SHORT).show();
                onLogin();

            }
        });

    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void onLogin() {
        closeKeyboard();
        String email = Email.getText().toString().trim();
        String password = Password1.getText().toString().trim();
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

        Call<User> call = MainActivity.apiInterface.performUserLogin(email, md5(password));

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body().getResponse().equals("200")) {
//                    MainActivity.prefConfig.displayToast("ВАНДУФЛ");
                    MainActivity.prefConfig.writeLoginStatus(true);
                    MainActivity.prefConfig.writeEmail(email);
                    NavHostFragment.findNavController(LoginFragment.this)
                            .navigate(R.id.action_loginFragment_to_activity2);
                } else if (response.body().getResponse().equals("failed")) {
                    Snackbar.make(root, "НЕ ПРАВИЛЬНО ВВЕДЕН EMAIL ИЛИ ПАРОЛЬ",
                            Snackbar.LENGTH_SHORT).show();
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
    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}