package com.studio.pattimura.bukaamal;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.studio.pattimura.bukaamal.Fragment.BerandaFragment;
import com.studio.pattimura.bukaamal.Fragment.BuatGalangDanaFragment;
import com.studio.pattimura.bukaamal.Fragment.GalangDanaFragment;

public class LandingPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Fragment fragment;
    private FragmentTransaction tukar;
    private TextView txtJudul;
    private ImageView logo;
    public static TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtJudul = (TextView) toolbar.findViewById(R.id.toolbarTitle);
        logo = (ImageView) toolbar.findViewById(R.id.logobuka);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        tabLayout.setVisibility(View.GONE);
        txtJudul.setText("");
        Picasso.with(getApplicationContext()).load(R.drawable.logoberanda).into(logo);
//        toolbar.setBackground(getResources().getDrawable(R.drawable.toolbarbg));
        fragment = new BerandaFragment();
        tukar = getSupportFragmentManager().beginTransaction();
        tukar.replace(R.id.mainframe, fragment);
        tukar.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.landing_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.beranda) {
            logo.setVisibility(View.VISIBLE);
            tabLayout.setVisibility(View.GONE);
            txtJudul.setText("");
            fragment = new BerandaFragment();
            tukar = getSupportFragmentManager().beginTransaction();
            tukar.replace(R.id.mainframe, fragment);
            tukar.commit();

        } else if (id == R.id.donasi) {
            logo.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            txtJudul.setText("Donasi");

        } else if (id == R.id.dana) {
            logo.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            txtJudul.setText("Galang Dana");
            fragment = new GalangDanaFragment();
            tukar = getSupportFragmentManager().beginTransaction();
            tukar.replace(R.id.mainframe, fragment);
            tukar.commit();

        } else if (id == R.id.pengaturan) {

        } else if (id == R.id.bantuan) {

        } else if (id == R.id.tentang) {

        } else if (id == R.id.logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}
