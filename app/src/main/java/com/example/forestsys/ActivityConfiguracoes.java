package com.example.forestsys;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static com.example.forestsys.ActivityLogin.nomeEmpresaPref;
import static com.example.forestsys.ActivityLogin.preferenceLogo;


public class ActivityConfiguracoes extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 0;

    private EditText editNomeEmpresa;
    private Button botaoSalvar;
    private Button botaoLogo;
    private ImageView imageView;
    private Uri mImageUri;
    private boolean mudou = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracoes);

        editNomeEmpresa = findViewById(R.id.edit_nome_empresa);
        botaoSalvar = (Button) findViewById(R.id.botao_salvar_configs);
        botaoLogo = (Button) findViewById(R.id.botao_selecionar_imagem);
        imageView = findViewById(R.id.imagem_logo);

        if(preferenceLogo == null) imageView.setImageResource(R.mipmap.ic_login_round);
        else imageView.setImageURI(Uri.parse(preferenceLogo));


        //


       // if(imageView == null) imageView.setImageResource(R.mipmap.ic_login_round);

        editNomeEmpresa.setText(nomeEmpresaPref);

        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSharedPreferences("nomeEmpresa", MODE_PRIVATE).edit()
                        .putString("nomeEmpresaPref", editNomeEmpresa.getText().toString()).commit();

                if(mudou == true) {
                    getSharedPreferences("imagemLogo", MODE_PRIVATE).edit()
                            .putString("preferenceLogo", mImageUri.toString()).commit();
                }
                voltarAoinicio();
            }
        });

        botaoLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selecionarImagem();
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
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST) {
            if (resultCode == RESULT_OK) {

                if (data != null) {
                    mImageUri = data.getData();
                    imageView.setImageURI(mImageUri);
                    imageView.invalidate();
                }
            }
        }
    }

    public void selecionarImagem() {
        checarPermissoes();
        Intent intent;
        if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_GET_CONTENT);
        } else {
            intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
        }
        mudou = true;
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Selecionar Imagem"), PICK_IMAGE_REQUEST);
    }

    public void checarPermissoes() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            return;
        }
    }

    public static void copiarArquivo(String inputPath) {
        String outputPath = "R.logos."+inputPath;
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(inputPath);
            out = new FileOutputStream(outputPath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            in = null;

            // write the output file (You have now copied the file)
            out.flush();
            out.close();
            out = null;



        } catch (FileNotFoundException fnfe1) {
        } catch (Exception e) {
        }
    }
}