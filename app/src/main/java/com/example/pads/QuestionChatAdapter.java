package com.example.pads;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class QuestionChatAdapter extends RecyclerView.Adapter<QuestionChatAdapter.MyViewHolder>   {

    List<Message> messageList;
    private Context context;
    private static final String TAG = "Creo"; // Declare the tag

    QuestionChatAdapter(Context context, List messageList){
        this.context = context;
        this.messageList = messageList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.question_chatbox,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestionChatAdapter.MyViewHolder holder, int position) {
        Message message = messageList.get(position);
        if(message.getSentBy().equals(Message.SENT_BY_ME)){
            holder.answer.setVisibility(View.GONE);
            holder.question.setText(message.getMessage());
        }else{

            holder.question.setVisibility(View.GONE);
            holder.answer.setText(message.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView question, answer;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            question = itemView.findViewById(R.id.textViewQuestion);
            answer = itemView.findViewById(R.id.textViewAnswer);

        }
    }
}
