package com.example.retosucsm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class IngresarAcivity extends AppCompatActivity {

    AutoCompleteTextView etCorreo;
    EditText etPass;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingresar);
       // mAuth = FirebaseAuth.getInstance();
       // mAuth.signOut();

        etCorreo = (AutoCompleteTextView)findViewById(R.id.email);
        etPass = (EditText)findViewById(R.id.password);

    }
    public void btnIngresar(View v){
        //Toast.makeText(getApplicationContext(),"INGRESE TODOS LOS CAMPOS",Toast.LENGTH_SHORT).show();
        if(etCorreo.getText().toString().equals("")|etPass.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"INGRESE TODOS LOS CAMPOS",Toast.LENGTH_SHORT).show();
        }
        else {
            verificarUsuario();
        }

    }
    public void verificarUsuario(){
        Toast.makeText(getApplicationContext(),"VERIFICAR",Toast.LENGTH_SHORT).show();
        db.collection("db_users")
                .whereEqualTo("Correo", etCorreo.getText().toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.getData().get("Password").toString().equals(etPass.getText().toString())){
                                    //Toast.makeText(getApplicationContext(),"Bienvenido "+document.getId() ,Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                                    intent.putExtra("id", document.getId());
                                    subirPreferences(document.getId());
                                    startActivity(intent);
                                }
                                else{
                                    Toast.makeText(getApplicationContext(),"Contraseña incorrecta",Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),"USUARIO NO EXISTE",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void subirPreferences(String id) {
        SharedPreferences preferencias=getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferencias.edit();
        editor.putString("id", id);
        editor.commit();
        finish();
    }
}
