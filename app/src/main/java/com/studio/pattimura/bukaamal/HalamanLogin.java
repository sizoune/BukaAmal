package com.studio.pattimura.bukaamal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class HalamanLogin extends AppCompatActivity implements View.OnClickListener {
    private Button logi,daftar;
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_login);

        logo = (ImageView) findViewById(R.id.logoLogin);
        Picasso.with(getApplicationContext()).load(R.drawable.logoberanda).into(logo);

        logi = (Button) findViewById(R.id.btnLogin);
        daftar = (Button) findViewById(R.id.btnHalDaftar);
        logi.setOnClickListener(this);
        daftar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == logi) {
            startActivity(new Intent(getApplicationContext(), IntroductionApp.class));
            finish();
        }
        else if (v == daftar){
            startActivity(new Intent(getApplicationContext(), HalamanDaftar.class));

        }
    }
}
