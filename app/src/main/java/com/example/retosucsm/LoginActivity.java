package com.example.retosucsm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private int RC_SIGN_IN = 1;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        obtenerPreferences();
       // Toast.makeText(LoginActivity.this,"id de preference " + id,Toast.LENGTH_SHORT).show();

        if(!id.equals("")){
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            intent.putExtra("id", id);
            finish();
            startActivity(intent);
        }
       // mAuth = FirebaseAuth.getInstance();
       // mAuth.signOut();
    }
    public void btnIngresar (View v){
        //Toast.makeText(LoginActivity.this,"LOGIN X GOOGLE",Toast.LENGTH_SHORT).show();
        //signIn();
        Intent intent = new Intent(this, IngresarAcivity.class);
        finish();
        startActivity(intent);
    }
    public void btnRegistro (View v){
        Toast.makeText(LoginActivity.this,"Registrar",Toast.LENGTH_SHORT).show();
        //signIn();
        Intent intent = new Intent(this, RegistrarAcivity.class);
        finish();
        startActivity(intent);
    }
    private void signIn() {

    }
    public void obtenerPreferences(){
        SharedPreferences prefe=getSharedPreferences("datos",Context.MODE_PRIVATE);
        id= prefe.getString("id","");
    }
    public void subirPreferences() {
        SharedPreferences preferencias=getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferencias.edit();
        editor.putString("id", "");
        editor.commit();
        finish();
    }
}
