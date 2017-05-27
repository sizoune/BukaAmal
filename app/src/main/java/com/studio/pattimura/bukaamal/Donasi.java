package com.studio.pattimura.bukaamal;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.studio.pattimura.bukaamal.Fragment.JumlahDonasiFragment;
import com.studio.pattimura.bukaamal.Fragment.MetodePembayaranFragment;
import com.studio.pattimura.bukaamal.Fragment.PembayaranFragment;
import com.studio.pattimura.bukaamal.Model.Berita;
import com.studio.pattimura.bukaamal.Model.DonasiSaya;

public class Donasi extends AppCompatActivity {
    public static TabLayout tabLayout;
    private Fragment fragment;
    private FragmentManager fm;
    private FragmentTransaction tukar;
    private JumlahDonasiFragment jumlahdonasifragment;
    private MetodePembayaranFragment metodePembayaranFragment;
    private PembayaranFragment pembayaranfragment;
    private Berita berita;
    private long idDonasi;
    private boolean isJumlah, isMetode;
    private String bank;
    private DonasiSaya donasi = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donasi);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            if (b.getParcelable("Berita") != null)
                berita = b.getParcelable("Berita");
            if (b.getParcelable("Donasi") != null) {
                donasi = b.getParcelable("Donasi");
                berita = donasi.getDataBerita();
            }
        }

        fm = getSupportFragmentManager();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView judul = (TextView) toolbar.findViewById(R.id.toolbarTitle);
        judul.setText("Donasi");

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        bindWidgetsWithAnEvent();
        setupTabLayout();
        replaceFragment(jumlahdonasifragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            if (isJumlah) {
                if (isMetode) {
                    android.app.FragmentManager fm = getFragmentManager();
                    fm.popBackStack();
                    finish();
                } else {
                    Toast.makeText(this, "Lengkapi Data Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                }
            } else {
                android.app.FragmentManager fm = getFragmentManager();
                fm.popBackStack();
                finish();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (Integer.parseInt(android.os.Build.VERSION.SDK) > 5
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            Log.d("CDA", "onKeyDown Called");
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onBackPressed() {
        if (isJumlah) {
            if (isMetode) {
                android.app.FragmentManager fm = getFragmentManager();
                fm.popBackStack();
                finish();
            } else {
                Toast.makeText(this, "Lengkapi Data Terlebih Dahulu", Toast.LENGTH_SHORT).show();
            }
        } else {
            android.app.FragmentManager fm = getFragmentManager();
            fm.popBackStack();
            finish();
        }
    }

    private void setupTabLayout() {
        jumlahdonasifragment = new JumlahDonasiFragment();
        metodePembayaranFragment = new MetodePembayaranFragment();
        pembayaranfragment = new PembayaranFragment();
        tabLayout.addTab(tabLayout.newTab().setText("Donasi"), true);
        tabLayout.addTab(tabLayout.newTab().setText("Metode Pembayaran"));
        tabLayout.addTab(tabLayout.newTab().setText("Pembayaran"));
    }

    private void bindWidgetsWithAnEvent() {
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setCurrentTabFragment(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setCurrentTabFragment(int tabPosition) {
        switch (tabPosition) {
            case 0:
                replaceFragment(jumlahdonasifragment);
                break;
            case 1:
                if (isJumlah())
                    replaceFragment(metodePembayaranFragment);
                else {
                    Toast.makeText(this, "Selesaikan Jumlah Donasi Dengan Menyimpan Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                    TabLayout.Tab tab = tabLayout.getTabAt(0);
                    tab.select();
                }
                break;
            case 2:
                if (isJumlah() && isMetode())
                    replaceFragment(pembayaranfragment);
                else {
                    Toast.makeText(this, "Selesaikan Data Jumlah dan Metode Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                    TabLayout.Tab tab = tabLayout.getTabAt(0);
                    tab.select();
                }
                break;
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.mainframe, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }

    public boolean isJumlah() {
        return isJumlah;
    }

    public void setJumlah(boolean jumlah) {
        isJumlah = jumlah;
    }

    public boolean isMetode() {
        return isMetode;
    }

    public void setMetode(boolean metode) {
        isMetode = metode;
    }

    public Berita getBerita() {
        return berita;
    }

    public void setBerita(Berita berita) {
        this.berita = berita;
    }

    public long getIdDonasi() {
        return idDonasi;
    }

    public void setIdDonasi(long idDonasi) {
        this.idDonasi = idDonasi;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public DonasiSaya getDonasi() {
        return donasi;
    }

    public void setDonasi(DonasiSaya donasi) {
        this.donasi = donasi;
    }
}
