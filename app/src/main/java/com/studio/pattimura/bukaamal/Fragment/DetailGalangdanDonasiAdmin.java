package com.studio.pattimura.bukaamal.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.studio.pattimura.bukaamal.MenuAdmin;
import com.studio.pattimura.bukaamal.Model.*;
import com.studio.pattimura.bukaamal.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class DetailGalangdanDonasiAdmin extends AppCompatActivity implements View.OnClickListener {
    private NumberProgressBar bnp;
    private Button verif;
    private StorageReference mStorageref;
    private FirebaseDatabase mDatabase;
    private Berita mu;
    private Identitas identitas;
    private ProgressDialog progressdialog;
    private com.studio.pattimura.bukaamal.Model.DonasiSaya donasi;
    private Bundle b;
    private userProfile user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_galangdan_donasi_admin);
        b = getIntent().getExtras();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView title = (TextView) toolbar.findViewById(R.id.toolbarTitle);
        title.setText("Detail");
        mDatabase = FirebaseDatabase.getInstance();
        progressdialog = new ProgressDialog(this);
        if (b != null) {
            final ImageView cover = (ImageView) findViewById(R.id.coverDetailDonasi);
            TextView danaterkumpul = (TextView) findViewById(R.id.txtSudahTerkumupl);
            TextView persen = (TextView) findViewById(R.id.txtpersen);
            TextView sisahari = (TextView) findViewById(R.id.txtsisahari);
            TextView judul = (TextView) findViewById(R.id.txtJudulDonasi);
            TextView desc = (TextView) findViewById(R.id.txtDescDonasi);
            TextView orang = (TextView) findViewById(R.id.orang);
            TextView nama = (TextView) findViewById(R.id.txtPenggalang);
            TextView target = (TextView) findViewById(R.id.txtTargetDonasi);
            ImageView avatar = (ImageView) findViewById(R.id.profile_detailDonasi);
            verif = (Button) findViewById(R.id.btnKonfirmasi);
            verif.setOnClickListener(this);
            bnp = (NumberProgressBar) findViewById(R.id.numberbar5);
            mu = b.getParcelable("berita");
            identitas = b.getParcelable("identitas");
            if (b.getParcelable("donasi") != null) {
                donasi = b.getParcelable("donasi");
                user = b.getParcelable("user");
                if (donasi.isStatus())
                    verif.setVisibility(GONE);
                else {
                    verif.setVisibility(VISIBLE);
                }
                verif.setText("Verifikasi Donasi");
                orang.setText("Donatur");
                judul.setText(mu.getJudul());
                if(donasi.getMetode_bayar()!=null)
                    desc.setText("Jumlah Donasi Sebesar Rp " + donasi.getJumlah() + " Melalui Rekening " + donasi.getMetode_bayar());
                else
                    desc.setText(user.getNama()+" Belum Memilih Metode Pembayaran");
                nama.setText(user.getNama());
                Picasso.with(getApplicationContext()).load(user.getAvatar()).fit().into(avatar);
                target.setText("Rp. " + String.valueOf(mu.getDana()));
                mStorageref = FirebaseStorage.getInstance().getReference(mu.getFoto());
                mStorageref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.with(getApplicationContext()).load(uri).fit().into(cover);
                    }
                });
                danaterkumpul.setText("Rp." + mu.getDana_terkumpul());
                double d = (double) mu.getDana_terkumpul() /  mu.getDana();
                bnp.setProgress((int)Math.round((d) * 100));
                persen.setText(String.format("%.2f",((d) * 100)) + "%");
                SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                try {
                    Date deadline = myFormat.parse(mu.getDeadline());
                    long selisih = deadline.getTime() - date.getTime();
                    sisahari.setText(Long.toString(TimeUnit.DAYS.convert(selisih, TimeUnit.MILLISECONDS)));
                } catch (ParseException e) {
                    Log.e("parse error", e.getMessage());
                }
            } else {
                verif.setText("Verifikasi Galang Dana");
                if (mu.getStatus())
                    verif.setVisibility(GONE);
                else {
                    verif.setVisibility(VISIBLE);
                }
                judul.setText(mu.getJudul());
                desc.setText(mu.getDeskripsi());
                nama.setText(identitas.getNama());
                target.setText(String.valueOf("Rp. " + mu.getDana()));
                Picasso.with(getApplicationContext()).load(identitas.getAvatar()).fit().into(avatar);
                mStorageref = FirebaseStorage.getInstance().getReference(mu.getFoto());
                mStorageref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.with(getApplicationContext()).load(uri).fit().into(cover);
                    }
                });
                danaterkumpul.setText("Rp." + mu.getDana_terkumpul());
                double d = (double) mu.getDana_terkumpul() /  mu.getDana();
                bnp.setProgress((int)Math.round((d) * 100));
                persen.setText(String.format("%.2f",((d) * 100)) + "%");
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

    @Override
    public void onClick(View view) {
        if (view == verif) {
            progressdialog.setMessage("Mohon tunggu...");
            progressdialog.show();
            SubmitGalang();
        }
    }

    public void SubmitGalang() {
        if (isOnline()) {
//            Toast.makeText(this, donasi.getId_user(), Toast.LENGTH_SHORT).show();
//            Toast.makeText(this, String.valueOf(donasi.getId()), Toast.LENGTH_SHORT).show();
            if (b.getParcelable("donasi") != null) {
                Query query = mDatabase.getReference("user").child("profil").child(donasi.getId_user()).child("donasi").child(String.valueOf(donasi.getIdDonasi()));
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            mDatabase.getReference("user").child("profil").child(donasi.getId_user()).child("donasi").child(String.valueOf(donasi.getIdDonasi())).child("status").setValue(true);
                            long jumlah = mu.getDana_terkumpul() + Long.parseLong(donasi.getJumlah());
                            mDatabase.getReference("user").child("profil").child(identitas.getId()).child("galang_dana").child(String.valueOf(donasi.getId_berita_galang())).child("berita").child("dana_terkumpul").setValue(jumlah);
                            mDatabase.getReference("admin").child("galang_dana").child("sudah_terverifikasi").child(String.valueOf(donasi.getId_berita_galang())).child("berita").child("dana_terkumpul").setValue(jumlah);
                            mDatabase.getReference("admin").child("donasi").child("sudah_terverifikasi").child(String.valueOf(donasi.getIdDonasi())).setValue(dataSnapshot.getValue());
                            mDatabase.getReference("admin").child("donasi").child("belum_terverifikasi").child(String.valueOf(donasi.getIdDonasi())).setValue(null);
                            mDatabase.getReference("admin").child("donasi").child("sudah_terverifikasi").child(String.valueOf(donasi.getIdDonasi())).child("status").setValue(true);
                            mDatabase.getReference("admin").child("total_donasi").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        long total = dataSnapshot.getValue(long.class) + Long.parseLong(donasi.getJumlah());
                                        mDatabase.getReference("admin").child("total_donasi").setValue(total);
                                    } else {
                                        mDatabase.getReference("admin").child("total_donasi").setValue(Long.parseLong(donasi.getJumlah()));
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(DetailGalangdanDonasiAdmin.this, "Cek Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
                                    progressdialog.dismiss();
                                }
                            });
                            mDatabase.getReference("admin").child("top_user").child(donasi.getId_user()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        mDatabase.getReference("admin").child("top_user").child(donasi.getId_user()).child("id_user").setValue(donasi.getId_user());
                                        long jumlah = dataSnapshot.child("jumlah_donasi").getValue(long.class)+Long.parseLong(donasi.getJumlah());
                                        mDatabase.getReference("admin").child("top_user").child(donasi.getId_user()).child("jumlah_donasi").setValue(jumlah);
                                    }else{
                                        mDatabase.getReference("admin").child("top_user").child(donasi.getId_user()).child("id_user").setValue(donasi.getId_user());
                                        mDatabase.getReference("admin").child("top_user").child(donasi.getId_user()).child("jumlah_donasi").setValue(Long.parseLong(donasi.getJumlah()));
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(DetailGalangdanDonasiAdmin.this, "Cek Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
                                    progressdialog.dismiss();
                                }
                            });
                            progressdialog.dismiss();
                            Toast.makeText(DetailGalangdanDonasiAdmin.this, "Donasi Berhasil Diverifikasi", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DetailGalangdanDonasiAdmin.this, MenuAdmin.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(DetailGalangdanDonasiAdmin.this, "Cek Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
                        progressdialog.dismiss();
                    }
                });
            } else {
                Query query = mDatabase.getReference("user").child("profil").child(identitas.getId()).child("galang_dana").child(String.valueOf(mu.getId()));
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            mDatabase.getReference("user").child("profil").child(identitas.getId()).child("galang_dana").child(String.valueOf(mu.getId())).child("berita").child("status").setValue(true);
                            mDatabase.getReference("admin").child("galang_dana").child("sudah_terverifikasi").child(String.valueOf(mu.getId())).setValue(dataSnapshot.getValue());
                            mDatabase.getReference("admin").child("galang_dana").child("belum_terverifikasi").child(String.valueOf(mu.getId())).setValue(null);
                            mDatabase.getReference("admin").child("galang_dana").child("sudah_terverifikasi").child(String.valueOf(mu.getId())).child("berita").child("status").setValue(true);
                            progressdialog.dismiss();
                            Toast.makeText(DetailGalangdanDonasiAdmin.this, "Galang Dana Berhasil Diverifikasi", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(DetailGalangdanDonasiAdmin.this, MenuAdmin.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(DetailGalangdanDonasiAdmin.this, "Cek Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
                        progressdialog.dismiss();
                    }
                });
            }
        } else {
            Toast.makeText(DetailGalangdanDonasiAdmin.this, "Cek Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
            progressdialog.dismiss();
        }

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
