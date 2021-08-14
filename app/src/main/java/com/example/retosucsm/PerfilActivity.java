package com.example.retosucsm;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;

public class PerfilActivity extends AppCompatActivity {
    public Toolbar toolbar;

    Dialog dialogEntrarGrupo;
    Dialog dialogCrearGrupo;
    String id;
    Usuario u1;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    TextView tvNombre, tvApellido;
    ImageView btnBack,btnMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Bundle extras = getIntent().getExtras();
        id = extras.getString("id"); // retrieve the data using keyName
        tvNombre = (TextView)findViewById(R.id.tvNombre);
        tvApellido = (TextView)findViewById(R.id.tvApellido);
        buscarUser();
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        btnMenu = (ImageView) toolbar.findViewById(R.id.btnMenu);
        btnMenu.setVisibility(View.GONE);
        btnBack = (ImageView) toolbar.findViewById(R.id.btnBack);
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MenuActivity.class);
                intent.putExtra("id", id);
                finish();
                startActivity(intent);
            }
        });
    }
    public void btnCrearGrupo(View v){
        abrirDialogCrearGrupo();
    }
    public void btnEntrarGrupo(View v){
        abrirDialogEntrarGrupo();
    }
    public void btnCerrarSesion(View v){
        Toast.makeText(PerfilActivity.this,"CERRANDO SESION",Toast.LENGTH_SHORT).show();
        subirPreferences("");
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        intent.putExtra("id", id);
        finish();
        startActivity(intent);
    }
    public void subirPreferences(String datos) {
        SharedPreferences preferencias=getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferencias.edit();
        editor.putString("id", datos);
        editor.commit();
    }
    public void abrirDialogEntrarGrupo() {
        dialogEntrarGrupo = new Dialog(PerfilActivity.this);
        dialogEntrarGrupo.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialogEntrarGrupo.setContentView(R.layout.dialog_ingresargrupo);
        dialogEntrarGrupo.setCancelable(true);
        dialogEntrarGrupo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogEntrarGrupo.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        ((Button) dialogEntrarGrupo.findViewById(R.id.btnCancelar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogEntrarGrupo.dismiss();

            }
        });

        ((Button) dialogEntrarGrupo.findViewById(R.id.btnSave)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"ACA SE AÑADE A UN GRUPO",Toast.LENGTH_SHORT).show();
                // dialog.dismiss();
            }
        });

        /*
        etNegocio = (TextInputEditText) dialogNegocio.findViewById(R.id.etNegocio);
        imgNegocio = (ImageView) dialogNegocio.findViewById(R.id.imgNegocio);
        etNegocio.setText(u1.getDetNegocio());
        spin = (Spinner) dialogNegocio.findViewById(R.id.spinner1);
        spin.setOnItemSelectedListener(this);
        CustomAdapter customAdapter = new CustomAdapter(getContext(), imagenesNegocios, negocios, 2);
        spin.setAdapter(customAdapter);
        spin.setSelection(Arrays.asList(negocios).indexOf(u1.getNegocio())); // pass value);
        iCurrentSelection = spin.getSelectedItemPosition();
        if (u1.imgNegocio != null) {
            Picasso.get().load(u1.getImgNegocio()).into(imgNegocio);
            imgNegocio.setVisibility(View.VISIBLE);
        }
*/
        dialogEntrarGrupo.show();
        dialogEntrarGrupo.getWindow().setAttributes(lp);
    }
    public void abrirDialogCrearGrupo(){
        dialogCrearGrupo = new Dialog(PerfilActivity.this);
        dialogCrearGrupo.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialogCrearGrupo.setContentView(R.layout.dialog_creargrupo);
        dialogCrearGrupo.setCancelable(true);
        dialogCrearGrupo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogCrearGrupo.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;


        ((Button) dialogCrearGrupo.findViewById(R.id.btnCancelar)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialogCrearGrupo.dismiss();

            }
        });

        ((Button) dialogCrearGrupo.findViewById(R.id.btnSave)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(),"ACA VA A CREAR UN NUEVO GRUPO",Toast.LENGTH_SHORT).show();
                // dialog.dismiss();
            }
        });

        dialogCrearGrupo.show();
        dialogCrearGrupo.getWindow().setAttributes(lp);
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
                                //Toast.makeText(getApplicationContext(), document.getId() + " => " + u1.getCodigo(),Toast.LENGTH_SHORT).show();

                                fillData(u1);
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void fillData(Usuario u1) {
        tvNombre.setText(u1.getNombres().toUpperCase());
        tvApellido.setText(u1.getApellidos().toUpperCase());

    }
}