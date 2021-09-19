package com.likesmm.instahype.main;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.likesmm.instahype.MainActivity;
import com.likesmm.instahype.Order;
import com.likesmm.instahype.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayHistoryFragment extends Fragment {
    RecyclerView payHistory;
    Adapter adapter;
    List<Order> orders;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pay_history, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("История заказов");
        payHistory = view.findViewById(R.id.payHistory);
        Call<List<Order>> call = MainActivity.apiInterface.getOrders(MainActivity.prefConfig.readEmail());
//        MainActivity.prefConfig.displayToast("0");

        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
//                MainActivity.prefConfig.displayToast("Code: "+response.code());
                if (response.code() == 200) {
                    orders = response.body();
//                    for (Order o : orders) {
//                        Log.d("type ", o.getData());
//                    }

                    payHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
                    adapter = new Adapter(orders);
                    payHistory.setAdapter(adapter);
                } else if (response.code() == 406) {
                    MainActivity.prefConfig.displayToast("Registration failed: incorrect login/password content...");
                } else if (response.code() == 500) {
                    MainActivity.prefConfig.displayToast("Server Error");
                }
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Log.e("Error ", t.getMessage());
//                MainActivity.prefConfig.displayToast("Server Error"+t.getMessage());
            }
        });





        return view;
    }




}
