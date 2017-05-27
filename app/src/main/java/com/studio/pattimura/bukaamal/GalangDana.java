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

import com.studio.pattimura.bukaamal.Fragment.BagikanFragment;
import com.studio.pattimura.bukaamal.Fragment.BuatberitaFragment;
import com.studio.pattimura.bukaamal.Fragment.IdentitasFragment;
import com.studio.pattimura.bukaamal.Fragment.VerifikasiFragment;
import com.studio.pattimura.bukaamal.Model.Berita;
import com.studio.pattimura.bukaamal.Model.Identitas;

public class GalangDana extends AppCompatActivity {
    public static TabLayout tabLayout;
    private Fragment fragment;
    private FragmentManager fm;
    private FragmentTransaction tukar;
    private BuatberitaFragment buatberitafragment;
    private IdentitasFragment identitasfragment;
    private VerifikasiFragment verifikasifragment;
    private BagikanFragment bagikanfragment;
    private boolean beritaTrue, identitasTrue, verifikasiTrue, bagikanTrue;
    private long idBerita;
    private Berita berita = null;
    private Identitas identitas = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galang_dana);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            berita = b.getParcelable("Berita");
            identitas = b.getParcelable("Identitas");
        }

        fm = getSupportFragmentManager();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView judul = (TextView) toolbar.findViewById(R.id.toolbarTitle);
        judul.setText("Galang Dana");

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        bindWidgetsWithAnEvent();
        setupTabLayout();
        replaceFragment(buatberitafragment);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            if (beritaTrue) {
                if (identitasTrue) {
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
        if (beritaTrue) {
            if (identitasTrue) {
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
        buatberitafragment = new BuatberitaFragment();
        identitasfragment = new IdentitasFragment();
        verifikasifragment = new VerifikasiFragment();
        bagikanfragment = new BagikanFragment();
        tabLayout.addTab(tabLayout.newTab().setText("BERITA"), true);
        tabLayout.addTab(tabLayout.newTab().setText("IDENTITAS"));
        tabLayout.addTab(tabLayout.newTab().setText("VERIFIKASI"));
        tabLayout.addTab(tabLayout.newTab().setText("BAGIKAN"));
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
                replaceFragment(buatberitafragment);
                break;
            case 1:
                if (isBeritaTrue())
                    replaceFragment(identitasfragment);
                else {
                    Toast.makeText(this, "Selesaikan Data Berita Dengan Menyimpan Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                    TabLayout.Tab tab = tabLayout.getTabAt(0);
                    tab.select();
                }
                break;
            case 2:
                if (isBeritaTrue())
                    if (isIdentitasTrue())
                        replaceFragment(verifikasifragment);
                    else {
                        Toast.makeText(this, "Selesaikan Data Identitas Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                        TabLayout.Tab tab = tabLayout.getTabAt(1);
                        tab.select();
                    }
                else {
                    Toast.makeText(this, "Selesaikan Data Berita Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                    TabLayout.Tab tab = tabLayout.getTabAt(0);
                    tab.select();
                }
                break;
            case 3:
                if (isBeritaTrue())
                    if (isIdentitasTrue())
                        if (isVerifikasiTrue())
                            replaceFragment(bagikanfragment);
                        else {
                            Toast.makeText(this, "Tunggu Verifikasi Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                            TabLayout.Tab tab = tabLayout.getTabAt(2);
                            tab.select();
                        }
                    else {
                        Toast.makeText(this, "Selesaikan Data Identitas Terlebih Dahulu", Toast.LENGTH_SHORT).show();
                        TabLayout.Tab tab = tabLayout.getTabAt(1);
                        tab.select();
                    }
                else {
                    Toast.makeText(this, "Selesaikan Data Berita Terlebih Dahulu", Toast.LENGTH_SHORT).show();
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

    public boolean isBeritaTrue() {
        return beritaTrue;
    }

    public void setBeritaTrue(boolean beritaTrue) {
        this.beritaTrue = beritaTrue;
    }

    public boolean isIdentitasTrue() {
        return identitasTrue;
    }

    public void setIdentitasTrue(boolean identitasTrue) {
        this.identitasTrue = identitasTrue;
    }

    public boolean isVerifikasiTrue() {
        return verifikasiTrue;
    }

    public void setVerifikasiTrue(boolean verifikasiTrue) {
        this.verifikasiTrue = verifikasiTrue;
    }

    public boolean isBagikanTrue() {
        return bagikanTrue;
    }

    public void setBagikanTrue(boolean bagikanTrue) {
        this.bagikanTrue = bagikanTrue;
    }

    public long getIdBerita() {
        return idBerita;
    }

    public void setIdBerita(long idBerita) {
        this.idBerita = idBerita;
    }

    public Berita getBerita() {
        return berita;
    }

    public void setBerita(Berita berita) {
        this.berita = berita;
    }

    public Identitas getIdentitas() {
        return identitas;
    }

    public void setIdentitas(Identitas identitas) {
        this.identitas = identitas;
    }


}
