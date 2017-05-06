package com.studio.pattimura.bukaamal;

import android.content.Intent;
import android.media.ImageReader;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;

public class SplashScreen extends AppCompatActivity {
    private static boolean splashLoaded = false;
    private AVLoadingIndicatorView avi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        String indicator = getIntent().getStringExtra("indicator");
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        avi.setIndicator(indicator);

        ImageView logo = (ImageView) findViewById(R.id.logosplash);
        Picasso.with(getApplicationContext()).load(R.drawable.logobukaamal).fit().into(logo);

        if (!splashLoaded) {
            new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    Intent i = new Intent(SplashScreen.this, HalamanLogin.class);
                    startActivity(i);

                    // close this activity
                    finish();
                }
            }, 3000);
            splashLoaded = true;
        } else {
            Intent goToMainActivity = new Intent(SplashScreen.this, HalamanLogin.class);
            goToMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(goToMainActivity);
            finish();
        }

    }

    public void hideClick(View view) {
        //avi.hide();
        avi.smoothToHide();
    }

    public void showClick(View view) {
        avi.show();
        avi.smoothToShow();
    }
}
