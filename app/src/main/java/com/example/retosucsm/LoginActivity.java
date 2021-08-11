package com.example.retosucsm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       // mAuth = FirebaseAuth.getInstance();
       // mAuth.signOut();
    }
    public void btnIngresar (View v){
        Toast.makeText(LoginActivity.this,"LOGIN X GOOGLE",Toast.LENGTH_SHORT).show();
        //signIn();
        Intent intent = new Intent(this, IngresarAcivity.class);
        startActivity(intent);
    }
    public void btnRegistro (View v){
        Toast.makeText(LoginActivity.this,"Registrar",Toast.LENGTH_SHORT).show();
        //signIn();
        Intent intent = new Intent(this, RegistrarAcivity.class);
        startActivity(intent);
    }
    private void signIn() {

    }

}
