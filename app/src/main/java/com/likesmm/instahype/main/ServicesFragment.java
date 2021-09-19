package com.likesmm.instahype.main;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.likesmm.instahype.Data;
import com.likesmm.instahype.MainActivity;
import com.likesmm.instahype.Order;
import com.likesmm.instahype.R;
import com.likesmm.instahype.welcome.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.likesmm.instahype.main.Activity.DATA1;

public class  ServicesFragment extends Fragment {
    RelativeLayout root;
    public static TextView tBalance;
    TextView sPrice, minCount;
    TextInputEditText sLink;
    EditText sCount;
    Spinner spinner;
    ArrayAdapter arrayAdapter2;
    Button next;
    int pos = 0;
    double count;
    double price;
    double balance;
    String link;
    boolean flag = false;
    NumberFormat nf = new DecimalFormat("#.######");

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_services, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Услуги");

        next = view.findViewById(R.id.next);
        tBalance = view.findViewById(R.id.balance);
        sPrice = view.findViewById(R.id.sPrice);
        sCount = view.findViewById(R.id.sCount);
        root = view.findViewById(R.id.rootServices);
        sLink = view.findViewById(R.id.sLink);
        minCount = view.findViewById(R.id.about2);
        minCount.setText("Минимальный заказ: " + nf.format(Data.minOrder[0]));
        arrayAdapter2 = new ArrayAdapter(getActivity(), R.layout.option_item, getResources().getTextArray(R.array.Inst));
        spinner = view.findViewById(R.id.spinner);
        spinner.setAdapter(arrayAdapter2);
        sCount.setText(nf.format(Data.minOrder[0]));
        sPrice.setText("Цена: " + getPrice(Data.minOrder[0], Data.price[0]) + "Руб.");

        setListener();
        flag = false;
        setBalance();
        setOnKeyListener(sLink);
        return view;
    }
//ПРОВЕРКА НА ССЫЛКУ
    boolean isUrlValid(CharSequence url) {
        return Patterns.WEB_URL.matcher(url).matches();
    }

    private void setListener() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sCount.setText("" + nf.format(Data.minOrder[position]));
                pos = position;
                minCount.setText("Минимальный заказ: " + nf.format(Data.minOrder[position]));
                sPrice.setText("Цена: " + getPrice(Data.minOrder[position], Data.price[position]) + " Руб.");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        sCount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int cont) {
                if (s.length() > 0) {
                    count = Double.parseDouble(s.toString());
                    if(count>5000){
                        count=5000;
                        sCount.setText(""+nf.format(count));
                    }
                    sPrice.setText("Цена: " + getPrice(count, Data.price[pos]) + " Руб.");
                    price = count * Data.price[pos];
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
//КНОПКА ЗАКАЗА
        next.setOnClickListener(v -> {
            flag = true;
            if (TextUtils.isEmpty(sLink.getText().toString().trim())) {
                Snackbar.make(root, "ВВЕДИТЕ ССЫЛКУ",
                        Snackbar.LENGTH_SHORT).show();
                flag = false;
                return;
            }
            if (!isUrlValid(sLink.getText().toString().trim())) {
                Snackbar.make(root, "ССЫЛКА НЕ КОРРЕКТНА",
                        Snackbar.LENGTH_SHORT).show();
                flag = false;
                return;
            }
            if (TextUtils.isEmpty(sCount.getText().toString().trim())) {
                Snackbar.make(root, "ВВЕДИТЕ КОЛИЧЕСТВО",
                        Snackbar.LENGTH_SHORT).show();
                flag = false;
                return;
            }
            if (count < Data.minOrder[pos]) {
                Snackbar.make(root, "МИНИМАЛЬНОЕ КОЛИЧЕСТВО " + nf.format(Data.minOrder[pos]),
                        Snackbar.LENGTH_SHORT).show();
                flag = false;
                return;
            }
            if (count > 5000.0) {
                Snackbar.make(root, "МАКСИМАЛЬНО КОЛИЧЕСТВО 5000",
                        Snackbar.LENGTH_SHORT).show();
                flag = false;
                return;
            }
            if (balance < price) {
                Snackbar.make(root, "НЕДОСТАТОЧНО ДЕНЕГ",
                        Snackbar.LENGTH_SHORT).show();
                flag = false;
                return;
            }
            setBalance();

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

    String getPrice(double count, double mPrice) {
        double b = count * mPrice;
        String a = nf.format(b);
        return a;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void setBalance() {
        Call<User> call = MainActivity.apiInterface.getBalance(MainActivity.prefConfig.readEmail());

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
//                    MainActivity.prefConfig.displayToast("ВАНДУФЛ");
                    tBalance.setText(response.body().getBalance() + " Руб.");
                    balance = Double.parseDouble(response.body().getBalance());
                    if (flag) {
                        part2();
                    }
                } else if (response.code() == 404) {
                    MainActivity.prefConfig.displayToast("404");
                } else if (response.code() == 406) {
                    MainActivity.prefConfig.displayToast("Registration failed: incorrect login/password content...");
                } else if (response.code() == 500) {
                    MainActivity.prefConfig.displayToast("Server Error");
                } else {
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                System.out.print(t);
            }
        });
    }

    private void part2() {
        DATA1.order = new Order(pos,
                sLink.getText().toString().trim(),
                count,
                price,
                pos,
                "");

        showDialog();
    }


    public void showDialog() {

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.confirmation_dialog_fragment, null);
        dialog.setView(view);
        Button confirm, cancel;
        TextView dType, dLink, dCount, dPr;
        String[] array;
        dType = view.findViewById(R.id.dType);
        dLink = view.findViewById(R.id.dLink);
        dCount = view.findViewById(R.id.dCount);
        dPr = view.findViewById(R.id.dPr);

        array = getResources().getStringArray(R.array.Inst);
        dType.setText("Тип заказа: " + array[pos]);
        dLink.setText(sLink.getText().toString().trim());
        dCount.setText("Кол-во: " + nf.format(count));
        dPr.setText("Цена: " + nf.format((price = count * Data.price[pos])) + " Руб.");

        dialog.setNegativeButton("Отмена", ( dialog12, which) -> {
            dialog12.dismiss();
        });

        dialog.setPositiveButton("Подтвердить", (dialog1, which) -> {
            Call<User> call = MainActivity.apiInterface.setOrder(MainActivity.prefConfig.readEmail(),
                    count,
                    price,
                    DATA1.type[pos],
                    sLink.getText().toString().trim()
            );
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.code() == 200) {
//                        MainActivity.prefConfig.displayToast("ВАНДУФЛ");
                        balance = balance - price;
                        tBalance.setText(balance + " Руб.");

                        dialog1.dismiss();
                    } else if (response.body().getResponse().equals("exist")) {
                        MainActivity.prefConfig.displayToast("User already exist...");
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
        });

        dialog.show();
    }
}