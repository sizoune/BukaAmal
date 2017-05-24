package com.studio.pattimura.bukaamal.Fragment;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.studio.pattimura.bukaamal.Model.Berita;
import com.studio.pattimura.bukaamal.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DetailGalangdanDonasiAdmin extends AppCompatActivity {
    private NumberProgressBar bnp;
    private Button verif;
    private StorageReference mStorageref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_galangdan_donasi_admin);
        Bundle b = getIntent().getExtras();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        if (b != null) {
            final ImageView cover = (ImageView) findViewById(R.id.coverDetailDonasi);
            TextView danaterkumpul = (TextView) findViewById(R.id.txtSudahTerkumupl);
            TextView persen = (TextView) findViewById(R.id.txtpersen);
            TextView sisahari = (TextView) findViewById(R.id.txtsisahari);
            TextView judul = (TextView) findViewById(R.id.txtJudulDonasi);
            TextView desc = (TextView) findViewById(R.id.txtDescDonasi);
            verif = (Button) findViewById(R.id.btnKonfirmasi);
            bnp = (NumberProgressBar) findViewById(R.id.numberbar5);
            Berita mu = b.getParcelable("berita");
            judul.setText(mu.getJudul());
            desc.setText(mu.getDeskripsi());
            mStorageref = FirebaseStorage.getInstance().getReference(mu.getFoto());
            mStorageref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.with(getApplicationContext()).load(uri).fit().into(cover);
                }
            });
            danaterkumpul.setText("Rp." + mu.getDana_terkumpul());
            bnp.setProgress(Math.round((mu.getDana_terkumpul() / 100) * 100));
            persen.setText(Integer.toString(Math.round((mu.getDana_terkumpul() / 100) * 100)) + "%");
            SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            try {
                Date deadline = myFormat.parse(mu.getDeadline());
                long selisih = deadline.getTime() - date.getTime();
                sisahari.setText(Long.toString(TimeUnit.DAYS.convert(selisih, TimeUnit.MILLISECONDS)));
            } catch (ParseException e) {
                Log.e("parse error", e.getMessage());
            }
        }
    }
}
