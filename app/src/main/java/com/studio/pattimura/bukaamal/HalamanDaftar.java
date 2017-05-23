package com.studio.pattimura.bukaamal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.studio.pattimura.bukaamal.R.id.view;

public class HalamanDaftar extends AppCompatActivity {
    private TextView disclaimer;
    private ImageView logo;
    private Spinner sp;

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

        sp = (Spinner) findViewById(R.id.spinJenisKelamin);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.kategoriJenisKelamin, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
    }
}
