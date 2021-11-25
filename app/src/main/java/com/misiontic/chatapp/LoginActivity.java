package com.misiontic.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    
    private EditText emailTxt,passTxt;
    private Button btnIniSe;
    private FirebaseAuth myAuth;
    private TextView gotoReg;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        this.myAuth = FirebaseAuth.getInstance();
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.emailTxt =  findViewById(R.id.txtEmailLogin);
        this.passTxt  =  findViewById(R.id.txtContraLogin);
        this.btnIniSe =  findViewById(R.id.btnLogin);
        this.gotoReg  =  findViewById(R.id.btnGoToRegistro);

        this.btnIniSe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoHome();
            }
        });

        this.gotoReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gotoReg = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(gotoReg);
            }
        });


    }

    private void gotoHome() {

        if(passTxt.getText().toString().isEmpty() || passTxt.getText().toString().isEmpty()){
            Toast.makeText(LoginActivity.this, "Debe llenas los campos de correo y contraseña", Toast.LENGTH_SHORT).show();
        }else {
            singIn(emailTxt.getText().toString(),passTxt.getText().toString());
        }

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

    private void singIn(String email,String pass){

        this.myAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){

                    FirebaseUser user = myAuth.getCurrentUser();
                    loginC(user);

                    Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();


                }else {
                    Toast.makeText(LoginActivity.this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void loginC(FirebaseUser user){

        Intent gotoHome = new Intent(this,HomeActivity.class);

        gotoHome.putExtra("email",user.getEmail());
        startActivity(gotoHome);
        finish();


    }


}