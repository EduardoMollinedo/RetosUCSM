package com.example.retosucsm;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


public class RegistrarAcivity extends AppCompatActivity {

    LinearLayout ly_datos, ly_registro;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    AutoCompleteTextView tvNombre, tvApellido, tvCodigo;
    AutoCompleteTextView tvCorreo;
    EditText tvPass, tvPass2;

    private RadioGroup rdgGrupo;
    String genero="";

    ProgressBar simpleProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        ly_datos= (LinearLayout)findViewById(R.id.ly_datos);
        ly_registro= (LinearLayout)findViewById(R.id.ly_registrar);
        simpleProgressBar=(ProgressBar)findViewById(R.id.login_progress); // initiate the progress bar
        simpleProgressBar.setMax(100);
        tvNombre = (AutoCompleteTextView)findViewById(R.id.tvNombre);
        tvApellido = (AutoCompleteTextView)findViewById(R.id.tvApellido);
        tvCodigo = (AutoCompleteTextView)findViewById(R.id.tvCodigo);
        tvCorreo = (AutoCompleteTextView)findViewById(R.id.tvCorreo);
        tvPass = (EditText)findViewById(R.id.tvPass);
        tvPass2 = (EditText)findViewById(R.id.tvPass2);
        rdgGrupo = (RadioGroup)findViewById(R.id.gender);
        rdgGrupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId == R.id.radio_female){
                    genero = "femenino";
                }else if (checkedId == R.id.radio_male){
                    genero = "masculino";
                }
            }
        });

    }
    public void btnSiguiente (View v){
        if(tvNombre.getText().toString().equals("")||tvApellido.getText().toString().equals("") ||tvCodigo.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"INGRESE TODOS LOS CAMPOS",Toast.LENGTH_SHORT).show();
        }
        else{

            ly_datos.setVisibility(View.GONE);
            ly_registro.setVisibility(View.VISIBLE);
        }
    }
    public void btnRegistro (View v){
        simpleProgressBar.setProgress(50);
        if(tvCorreo.getText().toString().equals("")||tvPass.getText().toString().equals("") ||tvPass2.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(),"INGRESE TODOS LOS CAMPOS",Toast.LENGTH_SHORT).show();
        }
        else{
            if(!tvPass.getText().toString().equals(tvPass2.getText().toString())){
                Toast.makeText(getApplicationContext(),"Contraseñas no coinciden",Toast.LENGTH_SHORT).show();
            }
            else {
                Map<String, Object> user = new HashMap<>();
                user.put("Nombres", tvNombre.getText().toString());
                user.put("Apellidos", tvApellido.getText().toString());
                user.put("Genero", genero);
                user.put("Codigo", tvCodigo.getText().toString());
                user.put("Correo", tvCorreo.getText().toString());
                user.put("Password", tvPass.getText().toString());
                db.collection("db_users")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(),"CUENTA CREADA CORRECTAMENTE",Toast.LENGTH_SHORT).show();
                                simpleProgressBar.setProgress(100);

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"ERROR",Toast.LENGTH_SHORT).show();
                                simpleProgressBar.setProgress(0);

                            }
                        });
            }

        }
    }
    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }
    public void subirDatos(){
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Ada");
        user.put("last", "Lovelace");
        user.put("born", 1815);

// Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("dfsdf", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("sdfsfsdf", "Error adding document", e);
                    }
                });
    }
}
