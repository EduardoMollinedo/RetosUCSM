package com.example.retosucsm;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;


public class RegistrarAcivity extends AppCompatActivity {

    LinearLayout ly_datos, ly_registro;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    AutoCompleteTextView tvNombre, tvApellido, tvCodigo;
    AutoCompleteTextView tvCorreo;
    EditText tvPass, tvPass2;
    Uri imageUri;
    ProgressDialog progress;
    ImageView ivFoto;
    private RadioGroup rdgGrupo;
    String genero = "";
    StorageReference mStorageRef;
    TextView tvTexto;
    ProgressBar simpleProgressBar;
    int flImagen = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        ly_datos = (LinearLayout) findViewById(R.id.ly_datos);
        ly_registro = (LinearLayout) findViewById(R.id.ly_registrar);
        simpleProgressBar = (ProgressBar) findViewById(R.id.login_progress); // initiate the progress bar
        simpleProgressBar.setMax(100);
        tvNombre = (AutoCompleteTextView) findViewById(R.id.tvNombre);
        tvApellido = (AutoCompleteTextView) findViewById(R.id.tvApellido);
        tvCodigo = (AutoCompleteTextView) findViewById(R.id.tvCodigo);
        tvCorreo = (AutoCompleteTextView) findViewById(R.id.tvCorreo);
        tvPass = (EditText) findViewById(R.id.tvPass);
        tvPass2 = (EditText) findViewById(R.id.tvPass2);
        rdgGrupo = (RadioGroup) findViewById(R.id.gender);
        ivFoto = (ImageView) findViewById(R.id.ivFoto);
        progress = new ProgressDialog(getApplicationContext());
        mStorageRef = FirebaseStorage.getInstance().getReference();
        tvTexto = (TextView) findViewById(R.id.tvTexto);
        rdgGrupo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId == R.id.radio_female) {
                    genero = "femenino";
                } else if (checkedId == R.id.radio_male) {
                    genero = "masculino";
                }
            }
        });

    }

    public void btnSiguiente(View v) {
        if (tvNombre.getText().toString().equals("") || tvApellido.getText().toString().equals("") || tvCodigo.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "INGRESE TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show();
        } else {
            tvTexto.setText("AGREGAR IMAGEN");
            ivFoto.setImageResource(R.drawable.profile);
            ly_datos.setVisibility(View.GONE);
            ly_registro.setVisibility(View.VISIBLE);

        }
    }

    public void btnImagen(View v) {
        abrirGaleria(0);
    }

    public void abrirGaleria(int type) {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, type);
    }

    int id = 234;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (null != data) {
            if (requestCode == 0) {
                imageUri = data.getData();
                ivFoto.setImageURI(imageUri);
                progress.setMax(100);
                progress.setMessage("Subiendo....");
                progress.setTitle("SUBIR FOTO PERFIL");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.show();

                Calendar mCalendar = Calendar.getInstance();
                final Date date = mCalendar.getTime();
                String path = id + Long.toHexString(date.getTime());
                //      Toast.makeText(getContext(), "id : " + id + " ruta : " + data.getData().getPath(), Toast.LENGTH_SHORT).show();
                final StorageReference sRef = mStorageRef.child("images/" + path);
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] datos = baos.toByteArray();
                UploadTask uploadTask = sRef.putBytes(datos);
                uploadTask = sRef.putFile(imageUri);
                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        // Continue with the task to get the download URL
                        return sRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            /*Uri downloadUri = task.getResult();
                            ivFoto.setVisibility(View.VISIBLE);
                            //u1.setImgNegocio(downloadUri.toString());
                            //Toast.makeText(getContext(), downloadUri.toString(), Toast.LENGTH_SHORT).show();
                            Map<String, Object> data = new HashMap<>();
                            data.put("imgEstado", downloadUri.toString());
                           // db.collection("users").document(id)
                             //       .set(data, SetOptions.merge());*/
                            progress.dismiss();
                            flImagen = 1;

                        } else {
                            //       Toast.makeText(getContext(), "FAILL ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


            }
        }
    }

    public void btnRegistro(View v) {
        flImagen=1;
        simpleProgressBar.setProgress(50);
        if (tvCorreo.getText().toString().equals("") || tvPass.getText().toString().equals("") || tvPass2.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "INGRESE TODOS LOS CAMPOS", Toast.LENGTH_SHORT).show();
        } else {
            if (!tvPass.getText().toString().equals(tvPass2.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            } else {
                if (flImagen == 0) {
                    Toast.makeText(getApplicationContext(), "AGREGAR IMAGEN", Toast.LENGTH_SHORT).show();
                } else {
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
                                    Toast.makeText(getApplicationContext(), "CUENTA CREADA CORRECTAMENTE", Toast.LENGTH_SHORT).show();
                                    Map<String, Object> data = new HashMap<>();
                                    data.put("id", documentReference.getId());
                                    db.collection("db_users").document(documentReference.getId())
                                            .set(data, SetOptions.merge());


                                    simpleProgressBar.setProgress(100);
                                    subirPreferences(documentReference.getId());
                                    Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                                    intent.putExtra("id", documentReference.getId());
                                    startActivity(intent);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                                    simpleProgressBar.setProgress(0);

                                }
                            });
                }

            }

        }
    }
    public void subirPreferences(String id) {
        SharedPreferences preferencias=getSharedPreferences("datos", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferencias.edit();
        editor.putString("id", id);
        editor.commit();
        finish();
    }
    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public void subirDatos() {
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
