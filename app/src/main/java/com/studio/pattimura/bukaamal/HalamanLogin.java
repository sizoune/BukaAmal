package com.studio.pattimura.bukaamal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HalamanLogin extends AppCompatActivity implements View.OnClickListener {
    private Button logi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_login);

        logi = (Button) findViewById(R.id.btnLogin);
        logi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == logi) {
            startActivity(new Intent(getApplicationContext(), IntroductionApp.class));
            finish();
        }
    }
}
