package com.studio.pattimura.bukaamal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Tentang extends AppCompatActivity {
    ImageView logo,graham,hilmi,aswan,wildan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView judul = (TextView) toolbar.findViewById(R.id.toolbarTitle);
        judul.setText("Tentang BukaAmal");
        logo = (ImageView) findViewById(R.id.imageLogo);
        graham = (ImageView) findViewById(R.id.graham);
        hilmi = (ImageView) findViewById(R.id.hilmi);
        wildan = (ImageView) findViewById(R.id.wildan);
        aswan = (ImageView) findViewById(R.id.aswan);
        Picasso.with(getApplicationContext()).load(R.drawable.logobukaamal).fit().into(logo);
        Picasso.with(getApplicationContext()).load(R.drawable.aswan).fit().into(aswan);
        Picasso.with(getApplicationContext()).load(R.drawable.graham).fit().into(graham);
        Picasso.with(getApplicationContext()).load(R.drawable.wildan).fit().into(wildan);
        Picasso.with(getApplicationContext()).load(R.drawable.hilmi).fit().into(hilmi);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            android.app.FragmentManager fm = getFragmentManager();
            fm.popBackStack();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
