package com.example.forestsys;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import static com.example.forestsys.ActivityLogin.nomeEmpresaPref;
import static com.example.forestsys.ActivityLogin.preferenceLogo;
import static com.example.forestsys.ActivityLogin.uriLogo;


public class ActivityConfiguracoes extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "nomeEmpresaPref";

    private static final int PICK_IMAGE_REQUEST = 0;


    private EditText editNomeEmpresa;
    private Button botaoSalvar;
    private Button botaoLogo;
    private ImageView imagemLogo;
    private Uri mImageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        editNomeEmpresa = findViewById(R.id.edit_nome_empresa);
        botaoSalvar = (Button) findViewById(R.id.botao_salvar_configs);
        botaoLogo = (Button) findViewById(R.id.botao_selecionar_imagem);
        imagemLogo = findViewById(R.id.imagem_logo);

        preferenceLogo = getSharedPreferences("image", MODE_PRIVATE);
        uriLogo = preferenceLogo.getString("image", null);

        if (uriLogo != null) {
            imagemLogo.setImageURI(Uri.parse(uriLogo));
        } else {
            imagemLogo.setImageResource(R.mipmap.ic_login_round);
        }

        nomeEmpresaPref = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getString("nomeEmpresaPref", "Plantar Siderurgica S/A");

        editNomeEmpresa.setText(nomeEmpresaPref);


        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                        .putString("nomeEmpresaPref", editNomeEmpresa.getText().toString()).commit();
                voltarAoinicio();
            }
        });

        botaoLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageSelect();
            }
        });
    }

    public void voltarAoinicio() {
        AlertDialog dialog = new AlertDialog.Builder(ActivityConfiguracoes.this)
                .setTitle("ERRO")
                .setMessage("Você será direcionado para a tela de login")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent it = new Intent(ActivityConfiguracoes.this, ActivityLogin.class);
                        startActivity(it);
                    }
                }).create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a image.
                // The Intent's data Uri identifies which item was selected.
                if (data != null) {

                    // This is the key line item, URI specifies the name of the data
                    mImageUri = data.getData();

                    // Saves image URI as string to Default Shared Preferences
                    preferenceLogo = getSharedPreferences("image", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferenceLogo.edit();
                    editor.putString("image", String.valueOf(mImageUri));
                    editor.commit();

                    // Sets the ImageView with the Image URI
                    imagemLogo.setImageURI(mImageUri);
                    imagemLogo.invalidate();
                }
            }
        }
    }

    public void imageSelect() {
        permissionsCheck();
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    public void permissionsCheck() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            return;
        }
    }

    /**
     * Clear Default Shared Preferences
     */
    public void clearData() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        finish();
        startActivity(getIntent());
    }
}