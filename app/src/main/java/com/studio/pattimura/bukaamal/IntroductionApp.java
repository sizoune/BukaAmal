package com.studio.pattimura.bukaamal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.google.gson.Gson;
import com.studio.pattimura.bukaamal.Model.userAuth;
import com.studio.pattimura.bukaamal.Model.userProfile;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wildan on 05/05/17.
 */

public class IntroductionApp extends AppIntro {
    private static final String TAG = "Intro";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private userAuth userData;
    private userProfile profileData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = getSharedPreferences("prefTok", MODE_PRIVATE);
        editor = preferences.edit();
        Gson gson = new Gson();
        String json = preferences.getString("prefTok", "");
        userData = gson.fromJson(json, userAuth.class);
        requestProfile(userData.getUser_id(), userData.getToken());

        addSlide(AppIntroFragment.newInstance("Donasi", "Donasikan dana anda untuk membantu ukm yang ada di Indonesia", R.drawable.donasiintro, getResources().getColor(R.color.merahBukaAmal)));
        addSlide(AppIntroFragment.newInstance("Galang Dana", "Kumpulkan dana anda untuk membantu saudar-saudara kita yang membutuhkan pertolongan", R.drawable.galangdanaintro, getResources().getColor(R.color.merahBukaAmal)));

    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        startActivity(new Intent(getApplicationContext(), LandingPage.class));
        finish();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        startActivity(new Intent(getApplicationContext(), LandingPage.class));
        finish();
    }

    public void requestProfile(final String username, final String password) {
        //Creating a string request

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://api.bukalapak.com/v2/users.json",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.i(TAG, "Message : " + response);
                            JSONObject respon = new JSONObject(response);
                            JSONObject user = respon.getJSONObject("user");
                            JSONObject alamat = user.getJSONObject("address");
                            if (respon.getString("status").equals("OK")) {
                                profileData = new userProfile(user.getString("name"), user.getString("avatar"), alamat.getString("city") + ", " + alamat.getString("province"), user.getString("email"), user.getString("phone"), "");
                                preferences = getSharedPreferences("prefProfile", MODE_PRIVATE);
                                editor = preferences.edit();
                                Gson gson = new Gson();
                                String token = gson.toJson(profileData); // myObject - instance of MyObject
                                editor.putString("prefProfile", token);
                                editor.commit();
                            } else {
                                Toast.makeText(IntroductionApp.this, "Cek Koneksi Internet anda", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Message : " + e.getMessage());
                            Toast.makeText(IntroductionApp.this, "Cek Koneksi Internet anda", Toast.LENGTH_SHORT).show();
                        }
//tempat response di dapatkan
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                        Toast.makeText(IntroductionApp.this, "Cek Koneksi Internet anda", Toast.LENGTH_SHORT).show();
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
}
