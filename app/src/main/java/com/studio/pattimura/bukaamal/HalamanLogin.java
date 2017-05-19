package com.studio.pattimura.bukaamal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.studio.pattimura.bukaamal.Model.userAuth;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HalamanLogin extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Login";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private String token;
    private Button login, daftar;
    private EditText username, password;
    private ImageView logo;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private userAuth userData;
    private ProgressDialog progressdialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_halaman_login);


        logo = (ImageView) findViewById(R.id.logoLogin);
        Picasso.with(getApplicationContext()).load(R.drawable.logoberanda).into(logo);
        username = (EditText) findViewById(R.id.edUsernameLogin);
        password = (EditText) findViewById(R.id.edPasswordLogin);
        login = (Button) findViewById(R.id.btnLogin);
        daftar = (Button) findViewById(R.id.btnHalDaftar);
        progressdialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();

        preferences = getSharedPreferences("prefTok",MODE_PRIVATE);
        editor = preferences.edit();
        editor.putString("prefTok", "kosong");
        editor.commit();
//        String loggedin = preferences.getString("tok", "kosong");
//        if (!loggedin.equals("kosong")) {
//            Log.d("PHP", "onCreate login: " + loggedin);
//            //start main activity
//            Intent ten = new Intent(this, LandingPage.class);
//            ten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(ten);
//        }
        login.setOnClickListener(this);
        daftar.setOnClickListener(this);
    }

    @NonNull
    private Boolean isValid() {
        EditText[] fields = {username, password};
        for (EditText e : fields) {
            if (e.getText().length() <= 0) return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v == login) {
            if (!isValid())
                Toast.makeText(this, "Username atau password masih ada yang kosong", Toast.LENGTH_SHORT).show();
            else {
                requestMasuk(username.getText().toString(), password.getText().toString());
                progressdialog.setMessage("Mohon tunggu...");
                progressdialog.show();
            }
        } else if (v == daftar) {
            startActivity(new Intent(getApplicationContext(), HalamanDaftar.class));

        }
    }


    public void requestMasuk(final String username, final String password) {
        //Creating a string request

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://api.bukalapak.com/v2/authenticate.json",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            Log.i(TAG, "Message : " + response);
                            JSONObject respon = new JSONObject(response);
                            if (respon.getString("confirmed") == "true") {
                                userData = new userAuth(respon.getString("user_id"), respon.getString("user_name"), respon.getString("token"), respon.getString("email"), respon.getString("omnikey"));
                                //token = respon.getString("token");
                                Gson gson = new Gson();
                                String token = gson.toJson(userData); // myObject - instance of MyObject
                                editor.putString("prefTok", token);
                                editor.commit();
                                masuk();
                            } else {
                                progressdialog.dismiss();
                                Toast.makeText(HalamanLogin.this, "Username dan Password tidak cocok", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            progressdialog.dismiss();
                            Log.e(TAG, "Message : " + e.getMessage());
                            Toast.makeText(HalamanLogin.this, "Username atau Password tidak cocok", Toast.LENGTH_SHORT).show();
                        }
//tempat response di dapatkan
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        progressdialog.dismiss();
                        Toast.makeText(HalamanLogin.this, "Cek Koneksi Internet anda", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                        Log.e(TAG, "Message : " + error.getMessage());

                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                String credentials = username + ":" + password;
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                headers.put("Content-Type", "application/json");
                headers.put("Authorization", auth);
                return headers;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void masuk() {
//        Toast.makeText(this, preferences.getString("token", "kosong"), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, userData.getUser_id(), Toast.LENGTH_SHORT).show();
        if (!preferences.getString("prefTok", "kosong").equals("kosong")) {
            mAuth.signInAnonymously()
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            Toast.makeText(HalamanLogin.this, "a", Toast.LENGTH_SHORT).show();
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInAnonymously:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Intent ten = new Intent(HalamanLogin.this, IntroductionApp.class);
                                ten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                Query query = mDatabase.getReference("userAuth").orderByChild("user_id").equalTo(userData.getUser_id());
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Boolean ada = false;
                                        if (dataSnapshot.exists()) {
                                            for (DataSnapshot data : dataSnapshot.getChildren()) {
                                                //Toast.makeText(HalamanLogin.this, data.get, Toast.LENGTH_SHORT).show();
                                                if (data.child("user_id").getValue().equals(userData.getUser_id())) {
                                                    data.getRef().child("token").setValue(userData.getToken());
                                                    ada = true;
                                                    break;
                                                }
                                            }
                                            if (!ada) {
                                                dataSnapshot.getRef().push().setValue(userData);
                                            }
                                        } else {
                                            mDatabase.getReference().child("userAuth").push().setValue(userData);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        progressdialog.dismiss();
                                        Toast.makeText(HalamanLogin.this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                Query query1 = mDatabase.getReference("user").child("profil").orderByChild("user_id").equalTo(userData.getUser_id());
                                query1.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        Object badge = "";
                                        if (!dataSnapshot.exists()) {
                                            mDatabase.getReference("user").child("profil").child(userData.getUser_id()).child("badge").setValue(badge);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                        progressdialog.dismiss();
                                        Toast.makeText(HalamanLogin.this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                progressdialog.dismiss();
                                startActivity(ten);
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInAnonymously:failure", task.getException());
                                Toast.makeText(HalamanLogin.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                            // ...
                        }
                    });
        } else {
            Toast.makeText(this, "Cek koneksi internet anda", Toast.LENGTH_SHORT).show();
        }
    }
}
