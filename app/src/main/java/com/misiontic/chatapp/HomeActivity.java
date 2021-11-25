package com.misiontic.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.misiontic.chatapp.models.Message;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {

    private EditText txtMessage;
    private FloatingActionButton sendMessages;
    private DatabaseReference reference;
    private MyAdactador adactador;
    private ArrayList<Message> messages;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.sendMessages = findViewById(R.id.send);
        this.txtMessage   = findViewById(R.id.txtMessage);

        this.recyclerView = findViewById(R.id.messagesList);

        this.reference = FirebaseDatabase.getInstance().getReference("messages");

        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        messages = new ArrayList<>();
        adactador = new MyAdactador(this,messages);
        recyclerView.setAdapter(adactador);

        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                messages.clear();

                for(DataSnapshot dataSnapshot:snapshot.getChildren()){

                    String message = dataSnapshot.getValue(String.class);
                    String user = dataSnapshot.getKey();

                    Message message1 = new  Message(user,message);

                    if(!messages.contains(message1)){
                        messages.add(message1);
                    }
                }

                Collections.reverse(messages);

                adactador.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        this.sendMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readAndWrite(txtMessage.getText().toString());
                txtMessage.setText("");
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.logOut){
            cerrarSesion();

        }
        return  true;
    }

    private void cerrarSesion() {
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(HomeActivity.this, "Hasta Luego", Toast.LENGTH_SHORT).show();
        Intent gotoLogin = new Intent(HomeActivity.this,LoginActivity.class);
        startActivity(gotoLogin);
        finish();


    }

    private void readAndWrite(String m){

        if(getIntent().hasExtra("email")){

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("messages").child(getIntent().getStringExtra("email").toString().substring(0,6) + " " + getDateTime());

            reference.setValue(m);

            reference.setValue(txtMessage.getText().toString());

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String val = snapshot.getValue(String.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(HomeActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference("message").child("Anonimo");

            reference.setValue(txtMessage.getText().toString());

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String val = snapshot.getValue(String.class);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(HomeActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });

        }



    }

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }




}