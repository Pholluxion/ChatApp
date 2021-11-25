package com.misiontic.chatapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.misiontic.chatapp.models.Message;

import java.util.ArrayList;

public class MyAdactador extends RecyclerView.Adapter<MyAdactador.MyViewHolder> {

    private ArrayList<Message> messages;
    private  Context context;

    public MyAdactador(Context context, ArrayList<Message> messages) {
        this.messages = messages;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(this.context).inflate(R.layout.item_message,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Message message = messages.get(position);

        holder.messageTxt.setText(message.getMessage());
        holder.userTxt.setText(message.getUser());
    }

    @Override
    public int getItemCount() {
        return this.messages.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView userTxt,messageTxt;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);

            this.userTxt = itemView.findViewById(R.id.user);
            this.messageTxt = itemView.findViewById(R.id.message);

        }
    }


}
