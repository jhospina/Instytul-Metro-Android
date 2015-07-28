package com.appsthergo.instytul.metro.appsthergoappname;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import libreria.conexion.Conexion;
import libreria.extensiones.ComponenteInterfaz;
import libreria.sistema.App;
import libreria.sistema.AppConfig;
import libreria.sistema.AppMeta;


public class RegistroUsuarioActivity extends ActionBarActivity {

    String resp_genero = null;
    static String reg_genero="genero";
    static String reg_edad="edad";
    static boolean registro=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(AppMeta.findByClave(RegistroUsuarioActivity.this, reg_genero)!=null) {
            Intent intent=new Intent(RegistroUsuarioActivity.this,MenuPrincipal.class);
            startActivity(intent);
        }


        setContentView(R.layout.activity_registro_usuario);

        this.getSupportActionBar().hide();

        establecerTextos();

        final LinearLayout layInicial = (LinearLayout) findViewById(R.id.layContent_msj_inicial);
        final LinearLayout layPregunta1 = (LinearLayout) findViewById(R.id.layContent_pregunta1);
        final LinearLayout layPregunta2 = (LinearLayout) findViewById(R.id.layContent_pregunta2);

        layInicial.setClickable(true);
        layInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layInicial.setVisibility(View.GONE);
                layPregunta1.setVisibility(View.VISIBLE);
            }
        });


        Button btn_pregunta1_op_hombre = (Button) findViewById(R.id.Btn_Hombre);
        btn_pregunta1_op_hombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resp_genero = AppConfig.txt_reg_pregunta1_resp_op1;
                layPregunta1.setVisibility(View.GONE);
                layPregunta2.setVisibility(View.VISIBLE);
            }
        });

        Button btn_pregunta1_op_mujer = (Button) findViewById(R.id.Btn_Mujer);
        btn_pregunta1_op_mujer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resp_genero = AppConfig.txt_reg_pregunta1_resp_op2;
                layPregunta1.setVisibility(View.GONE);
                layPregunta2.setVisibility(View.VISIBLE);
            }
        });


        LinearLayout layEdades = (LinearLayout) findViewById(R.id.layContent_pregunta2_edades);
        ComponenteInterfaz interfaz = new ComponenteInterfaz(this);
        int n = 1;
        for (int i = 8; i <= 70; i++) {
            final Button btnEdad = new Button(this);
            btnEdad.setText(String.valueOf(i));
            btnEdad.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    registro=true;

                    Thread hilo = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Conexion.registrarMetaDato(RegistroUsuarioActivity.this, App.obtenerIdDispositivo(RegistroUsuarioActivity.this)+"_"+reg_genero, resp_genero);
                            Conexion.registrarMetaDato(RegistroUsuarioActivity.this,App.obtenerIdDispositivo(RegistroUsuarioActivity.this)+"_"+reg_edad, btnEdad.getText().toString());
                        }
                    });
                    hilo.start();
                    Intent intent=new Intent(RegistroUsuarioActivity.this,MenuPrincipal.class);
                    startActivity(intent);
                }
            });
            layEdades.addView(btnEdad);
        }

    }


    private void establecerTextos() {
        ((TextView) findViewById(R.id.txt_msj_inicial)).setText(AppConfig.txt_reg_msj_inicial);
        ((TextView) findViewById(R.id.txt_pulsar_pantalla)).setText(AppConfig.txt_reg_pulsar_pantalla);
        ((TextView) findViewById(R.id.txt_pregunta1)).setText(AppConfig.txt_reg_pregunta1);
        ((Button) findViewById(R.id.Btn_Hombre)).setText(AppConfig.txt_reg_pregunta1_resp_op1);
        ((Button) findViewById(R.id.Btn_Mujer)).setText(AppConfig.txt_reg_pregunta1_resp_op2);
        ((TextView) findViewById(R.id.txt_pregunta2)).setText(AppConfig.txt_reg_pregunta2);
    }


}
