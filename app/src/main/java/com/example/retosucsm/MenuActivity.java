package com.example.retosucsm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class MenuActivity extends AppCompatActivity {

    private int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
//        mAuth = FirebaseAuth.getInstance();
       // mAuth.signOut();
    }
    public void btnVirtual (View v){
        Toast.makeText(MenuActivity.this,"VIRTUAL",Toast.LENGTH_SHORT).show();
        //Intent intent = new Intent(this, ChatbotActivity.class);
       // startActivity(intent);
    }
    public void btnVincular (View v){
        Toast.makeText(MenuActivity.this,"VINCULAR X BLUETOOTH",Toast.LENGTH_SHORT).show();
        //Intent intent = new Intent(this, DeviceList.class);
        //startActivity(intent);
    }

    private void signIn() {
    }

}
