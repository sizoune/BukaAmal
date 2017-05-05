package com.studio.pattimura.bukaamal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class HalamanDaftar extends AppCompatActivity {
    private TextView disclaimer;
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_daftar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDaftar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        logo = (ImageView) findViewById(R.id.logoDaftar);
        Picasso.with(getApplicationContext()).load(R.drawable.logoberanda).into(logo);

        disclaimer = (TextView) findViewById(R.id.disclaim);
        disclaimer.setText(Html.fromHtml(getString(R.string.disclaimer)));
    }
}
