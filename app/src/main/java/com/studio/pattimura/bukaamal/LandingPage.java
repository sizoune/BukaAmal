package com.studio.pattimura.bukaamal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.studio.pattimura.bukaamal.Fragment.BerandaFragment;
import com.studio.pattimura.bukaamal.Fragment.BuatGalangDanaFragment;
import com.studio.pattimura.bukaamal.Fragment.DetailProfileFragment;
import com.studio.pattimura.bukaamal.Fragment.DonasiFragment;
import com.studio.pattimura.bukaamal.Fragment.GalangDanaFragment;
import com.studio.pattimura.bukaamal.Fragment.LogoutDialogFragment;
import com.studio.pattimura.bukaamal.Model.userAuth;
import com.studio.pattimura.bukaamal.Model.userProfile;

public class LandingPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static TabLayout tabLayout;
    NavigationView navigationView;
    private Fragment fragment;
    private FragmentManager fm;
    private FragmentTransaction tukar;
    private TextView txtJudul, txtNama, txtAsal;
    private ImageView logo, avatar;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private userProfile profileData;
    private userAuth userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fm = getSupportFragmentManager();

        preferences = getSharedPreferences("prefProfile", MODE_PRIVATE);
        editor = preferences.edit();
        Gson gson = new Gson();
        String json = preferences.getString("prefProfile", "");
        profileData = gson.fromJson(json, userProfile.class);

        preferences = getSharedPreferences("prefTok", MODE_PRIVATE);
        editor = preferences.edit();
        gson = new Gson();
        json = preferences.getString("prefTok", "");
        userData = gson.fromJson(json, userAuth.class);

        txtJudul = (TextView) toolbar.findViewById(R.id.toolbarTitle);
        logo = (ImageView) toolbar.findViewById(R.id.logobuka);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setItemIconTintList(null);

        final View header = navigationView.getHeaderView(0);
        txtNama = (TextView) header.findViewById(R.id.namaProfile);
        txtAsal = (TextView) header.findViewById(R.id.asalProfile);
        avatar = (ImageView) header.findViewById(R.id.profile_image);

        tabLayout.setVisibility(View.GONE);
        txtJudul.setText("");
        txtNama.setText(profileData.getNama());
        if (profileData.getAlamat().equals("null, null"))
            txtAsal.setText("");
        else
            txtAsal.setText(profileData.getAlamat());
        Picasso.with(getApplicationContext()).load(profileData.getAvatar()).into(avatar);
        Picasso.with(getApplicationContext()).load(R.drawable.logoberanda).into(logo);
        if (userData.getUser_id().equals("33985651")) {
            Menu menu = navigationView.getMenu();
            menu.add(1, 5, 5, "Administrator");
        }
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

        if (fm.getBackStackEntryCount() > 0) {
            Log.i("MainActivity", "popping backstack");
            fm.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
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
//            tukar.addToBackStack(null);
            tukar.commit();

        } else if (id == R.id.donasi) {
            logo.setVisibility(View.GONE);
            tabLayout.setVisibility(View.VISIBLE);
            txtJudul.setText("Donasi");
            fragment = new DonasiFragment();
            tukar = getSupportFragmentManager().beginTransaction();
            tukar.replace(R.id.mainframe, fragment);
            tukar.addToBackStack("BerandaFragment");
            tukar.commit();

        } else if (id == R.id.dana) {
            logo.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            txtJudul.setText("Galang Dana");
            fragment = new GalangDanaFragment();
            tukar = getSupportFragmentManager().beginTransaction();
            tukar.replace(R.id.mainframe, fragment);
            tukar.addToBackStack("BerandaFragment");
            tukar.commit();

        } else if (id == R.id.cairkan) {
            Intent intent = new Intent(LandingPage.this,CairkanDana.class);
            startActivity(intent);
        } else if (id == R.id.bantuan) {
            Intent intent = new Intent(LandingPage.this,Bantuan.class);
            startActivity(intent);
        } else if (id == R.id.tentang) {
            Intent intent = new Intent(LandingPage.this,Tentang.class);
            startActivity(intent);
        } else if (id == R.id.logout) {
            LogoutDialogFragment alertdFragment = new LogoutDialogFragment();
            // Show Alert DialogFragment

            alertdFragment.show(fm, "Alert Dialog Fragment");
        } else if (id == R.id.profil) {
            logo.setVisibility(View.GONE);
            tabLayout.setVisibility(View.GONE);
            txtJudul.setText("Profile");
            fragment = new DetailProfileFragment();
            tukar = getSupportFragmentManager().beginTransaction();
            tukar.replace(R.id.mainframe, fragment);
            tukar.addToBackStack("BerandaFragment");
            tukar.commit();
        } else if (id == 5) {
            startActivity(new Intent(LandingPage.this, MenuAdmin.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public TextView getTxtJudul() {
        return txtJudul;
    }

    public void setTxtJudul(TextView txtJudul) {
        this.txtJudul = txtJudul;
    }

    public ImageView getLogo() {
        return logo;
    }

    public void setLogo(ImageView logo) {
        this.logo = logo;
    }
}
