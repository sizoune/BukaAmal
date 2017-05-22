package com.studio.pattimura.bukaamal.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.studio.pattimura.bukaamal.HalamanLogin;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by desmoncode on 20/05/17.
 */

public class LogoutDialogFragment extends DialogFragment {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    String tok;
    private FirebaseAuth mAuth;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        return new AlertDialog.Builder(getActivity())
                // Set Dialog Icon

                // Set Dialog Title
                .setTitle("Logout")
                // Set Dialog Message
                .setMessage("Anda yakin untuk logout ?")

                // Positive button
                .setPositiveButton("YA", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something else
                        preferences = getContext().getSharedPreferences("prefTok", MODE_PRIVATE);

                        editor = preferences.edit();
                        editor.putString("prefTok", "kosong");
                        editor.commit();
                        mAuth.signOut();
                        Intent ten = new Intent(getActivity(), HalamanLogin.class);
                        ten.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(ten);
                    }
                })

                // Negative Button
                .setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Do something else
                    }
                }).create();
    }
}
