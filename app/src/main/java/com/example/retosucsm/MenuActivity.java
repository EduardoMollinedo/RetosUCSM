package com.example.retosucsm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class MenuActivity extends AppCompatActivity {

    private int RC_SIGN_IN = 1;
    public Toolbar toolbar;
    ImageView tvMenu,ivBack;
    String id;
    Usuario u1;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Bundle extras = getIntent().getExtras();
        id = extras.getString("id"); // retrieve the data using keyName
        //obtenerPreferences();
        if(id.equals("")){
            finish();
        }
        //Toast.makeText(MenuActivity.this,id,Toast.LENGTH_SHORT).show();
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        tvMenu = (ImageView) toolbar.findViewById(R.id.btnMenu);
        ivBack = (ImageView) toolbar.findViewById(R.id.btnBack);
        ivBack.setVisibility(View.GONE);
        tvMenu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast.makeText(MenuActivity.this,"BOTON MENU" + id + "dsfsd",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(),PerfilActivity.class);
                intent.putExtra("id", id);
                finish();
                startActivity(intent);
            }
        });
//        mAuth = FirebaseAuth.getInstance();
       // mAuth.signOut();
    }
    public void obtenerPreferences(){
        SharedPreferences prefe=getSharedPreferences("datos", Context.MODE_PRIVATE);
        id= prefe.getString("id","");
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
    public void buscarUser() {
        //Toast.makeText(getApplicationContext(),"buscando usuario "+id,Toast.LENGTH_SHORT).show();

        db.collection("db_users").whereEqualTo("id", id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                u1 = document.toObject(Usuario.class);
                                Toast.makeText(getApplicationContext(), "BIENVENIDO" + " => " + u1.getNombres(),Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
