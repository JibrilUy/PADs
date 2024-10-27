package com.example.pads;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>   {

    private ArrayList<String> month, net_income, expenses;
    private Context context;
    private static final String TAG = "Creo"; // Declare the tag

     RecyclerViewAdapter(Context context, ArrayList month, ArrayList net_income, ArrayList expenses){
         this.context = context;
         this.month = month;
         this.net_income = net_income;
         this.expenses = expenses;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.itemview,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.MyViewHolder holder, int position) {

        holder.month.setText(String.valueOf(month.get(position)));
        holder.net_income.setText(String.valueOf(net_income.get(position)));
        holder.expenses.setText(String.valueOf(expenses.get(position)));

    }

    @Override
    public int getItemCount() {
        return month.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView month, net_income, expenses;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            month = itemView.findViewById(R.id.textViewMonth);
            net_income = itemView.findViewById(R.id.textViewNetIncome);
            expenses = itemView.findViewById(R.id.textViewExpenses);

        }
    }
}
