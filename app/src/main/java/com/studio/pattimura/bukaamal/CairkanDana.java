package com.studio.pattimura.bukaamal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.studio.pattimura.bukaamal.Adapter.AdapterDonasiSaya;
import com.studio.pattimura.bukaamal.Adapter.AdapterGalangDanaSaya;
import com.studio.pattimura.bukaamal.Fragment.GalangDanaSaya;
import com.studio.pattimura.bukaamal.Model.Berita;
import com.studio.pattimura.bukaamal.Model.DonasiSaya;
import com.studio.pattimura.bukaamal.Model.Identitas;
import com.studio.pattimura.bukaamal.Model.userAuth;

import java.util.ArrayList;

public class CairkanDana extends AppCompatActivity {
    private ArrayList<Berita> databerita = new ArrayList<>();
    private ArrayList<Identitas> dataIdentitas = new ArrayList<>();
    private AdapterGalangDanaSaya adapter;
    private RecyclerView list;
    private GridLayoutManager gridLayoutManager;
    private FirebaseDatabase database;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private userAuth userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cairkan_dana);
        preferences = getSharedPreferences("prefTok", MODE_PRIVATE);
        editor = preferences.edit();
        Gson gson = new Gson();
        String json = preferences.getString("prefTok", "");
        userData = gson.fromJson(json, userAuth.class);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView judul = (TextView) toolbar.findViewById(R.id.toolbarTitle);
        judul.setText("Cairkan Dana");

        database = FirebaseDatabase.getInstance();

        list = (RecyclerView) findViewById(R.id.recyclerViewgalangdanasaya);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1, GridLayoutManager.VERTICAL, false);
        getAllData();
    }

    private void getAllData() {
        Query query = database.getReference("user").child("profil").child(userData.getUser_id()).child("galang_dana");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Berita berita = data.child("berita").getValue(Berita.class);
                    databerita.add(berita);
                    Identitas identitas = data.child("identitas").getValue(Identitas.class);
                    dataIdentitas.add(identitas);
                }
                adapter = new AdapterGalangDanaSaya(getApplicationContext(), databerita);
                list.setAdapter(adapter);
                list.setLayoutManager(gridLayoutManager);
//                adapter.SetOnItemClickListener(new AdapterGalangDanaSaya.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(View view, int position) {
//                        Berita brt = databerita.get(position);
//                        Identitas idt = dataIdentitas.get(position);
//                        Intent intent = new Intent(GalangDanaSaya.this.getContext(),GalangDana.class);
//                        intent.putExtra("Berita",brt);
//                        intent.putExtra("Identitas",idt);
//                        startActivity(intent);
//                    }
//                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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
