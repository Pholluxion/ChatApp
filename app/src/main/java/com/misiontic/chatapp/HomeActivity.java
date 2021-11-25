package com.misiontic.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.zip.Inflater;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
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
        Toast.makeText(HomeActivity.this, "Hasta Luegos", Toast.LENGTH_SHORT).show();
        Intent gotoLogin = new Intent(HomeActivity.this,LoginActivity.class);
        startActivity(gotoLogin);
        finish();


    }
}