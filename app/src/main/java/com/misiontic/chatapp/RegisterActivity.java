package com.misiontic.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText emailTxtR,passTxtR1,passTxtR2;
    private Button registroBtn;
    private FirebaseAuth myAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.myAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        this.emailTxtR = findViewById(R.id.txtEmailRegistro);
        this.passTxtR1 = findViewById(R.id.txtContraRegistro_1);
        this.passTxtR2 = findViewById(R.id.txtContraRegistro_2);
        this.registroBtn = findViewById(R.id.btnRegistro);
        this.registroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if((emailTxtR.getText().toString().isEmpty() || passTxtR1.getText().toString().isEmpty() || passTxtR2.getText().toString().isEmpty())){

                    Toast.makeText(RegisterActivity.this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();

                }else {

                    if(passTxtR1.getText().toString().equals(passTxtR2.getText().toString())){

                        createAccount(emailTxtR.getText().toString(),passTxtR1.getText().toString());

                    }else{
                        Toast.makeText(RegisterActivity.this, "Las contraseñas no coinsiden", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = myAuth.getCurrentUser();
        if (currentUser!=null){
            reload();
        }
    }

    private void reload() {
    }

    private void createAccount(String email, String pass){

        this.myAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    Toast.makeText(RegisterActivity.this, "Registrado con exito!!!", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = myAuth.getCurrentUser();

                    onCompleteReg(user);


                }

            }
        });

    }

    private void onCompleteReg(FirebaseUser user){

        Intent gotoLogin = new Intent(this,LoginActivity.class);
        startActivity(gotoLogin);
        finish();


    }



}