package com.likesmm.instahype.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.likesmm.instahype.Order;
import com.likesmm.instahype.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    List<Order> orders;

    public Adapter(List<Order> orders) {
        this.orders = orders;
    }

//    public interface SelectMenuItem{
//        void idMenuItem(int id);
//    }
//    private static SelectMenuItem selectMenuItem;
//    public void setSelectMenuItemListener(SelectMenuItem listener){
//        selectMenuItem=listener;
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext())
               .inflate(R.layout.order_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.id.setText("№ "+orders.get(position).getId());
        holder.type.setText(orders.get(position).getType());
        holder.price.setText(""+orders.get(position).getPrice()+" Руб. ");
        holder.count.setText(""+orders.get(position).getCount()+" Шт.");
        holder.date.setText(""+orders.get(position).getDate());
        holder.link.setText(""+orders.get(position).getLink());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
//        Button repeat,send;
        TextView id,type,price,count,date,link;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id=itemView.findViewById(R.id.hId);
            type=itemView.findViewById(R.id.hType);
            price=itemView.findViewById(R.id.hPrice);
            count=itemView.findViewById(R.id.hCount);
            date=itemView.findViewById(R.id.hDate);
            link=itemView.findViewById(R.id.hLink);
//            repeat=itemView.findViewById(R.id.hRepeat);
//            send=itemView.findViewById(R.id.hSend);
        }
    }
}
