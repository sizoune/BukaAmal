package com.studio.pattimura.bukaamal.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.studio.pattimura.bukaamal.Model.Berita;
import com.studio.pattimura.bukaamal.Model.userAuth;
import com.studio.pattimura.bukaamal.Model.userProfile;
import com.studio.pattimura.bukaamal.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.util.Calendar;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuatberitaFragment extends Fragment implements DatePickerDialog.OnDateSetListener, View.OnClickListener {

    private Spinner sp;
    private EditText deadline, judul, dana, lokasi, cerita;
    private Bitmap bitmap;
    private String file_name;
    private Button lampirfoto, simpanlanjut;
    private Uri foto = null;
    private String namefile;
    private ImageView imfoto;
    private FirebaseDatabase mDatabase;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private userAuth userData;
    private Berita berita;
    private ProgressDialog progressdialog;

    public BuatberitaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buatberita, container, false);

        preferences = this.getActivity().getSharedPreferences("prefTok", MODE_PRIVATE);
        editor = preferences.edit();
        Gson gson = new Gson();
        String json = preferences.getString("prefTok", "");
        userData = gson.fromJson(json, userAuth.class);

        mDatabase = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        lampirfoto = (Button) view.findViewById(R.id.btnFoto);
        lampirfoto.setOnClickListener(this);
        simpanlanjut = (Button) view.findViewById(R.id.btnSimpanLanjut);
        simpanlanjut.setOnClickListener(this);
        imfoto = (ImageView) view.findViewById(R.id.foto);
        imfoto.setOnClickListener(this);
        imfoto.setAdjustViewBounds(true);

        judul = (EditText) view.findViewById(R.id.edJudulBerita);
        dana = (EditText) view.findViewById(R.id.edDana);
        lokasi = (EditText) view.findViewById(R.id.edLokasi);
        cerita = (EditText) view.findViewById(R.id.edCerita);

        sp = (Spinner) view.findViewById(R.id.spinKategori);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.Kategori, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);

        deadline = (EditText) view.findViewById(R.id.edDeadline);
        deadline.setOnClickListener(this);

        progressdialog = new ProgressDialog(this.getContext());

        return view;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        deadline.setText(dayOfMonth + "/" + (++monthOfYear) + "/" + year);
    }

    @Override
    public void onClick(View view) {
        if (view == deadline) {
            Calendar now = Calendar.getInstance();
            DatePickerDialog dpd = DatePickerDialog.newInstance(
                    BuatberitaFragment.this,
                    now.get(Calendar.YEAR),
                    now.get(Calendar.MONTH),
                    now.get(Calendar.DAY_OF_MONTH)
            );
            dpd.setAccentColor(ContextCompat.getColor(BuatberitaFragment.this.getContext(), R.color.merahBukaAmal));
            dpd.dismissOnPause(true);
            dpd.showYearPickerFirst(true);
            dpd.setVersion(DatePickerDialog.Version.VERSION_2);
            dpd.show(BuatberitaFragment.this.getActivity().getFragmentManager(), "Deadline");
        } else if (view == lampirfoto) {
            photoBuilder();
        } else if (view == imfoto) {

        } else if (view == simpanlanjut) {
            if (!isValid()) {
                Toast.makeText(BuatberitaFragment.this.getContext(), "Lengkapi Data Berita Terlebih Dahulu", Toast.LENGTH_SHORT).show();
            } else {
                if(!isOnline()){
                    Toast.makeText(this.getContext(), "Cek Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
                }else {
                    progressdialog.setMessage("Mohon tunggu...");
                    progressdialog.show();
                    Submit(foto);
                }
            }
        }
    }

    @NonNull
    private Boolean isValid() {
        EditText[] fields = {deadline, judul, dana, lokasi, cerita};
        if (imfoto.getDrawable() == null) {
            return false;
        }
        for (EditText e : fields) {
            if (e.getText().length() <= 0) return false;
        }
        return true;
    }

    private void photoBuilder() {
        final CharSequence[] options = {"Ambil Foto", "Pilih dari Galeri", "Batal"};
        AlertDialog.Builder builder = new AlertDialog.Builder(BuatberitaFragment.this.getContext());
        builder.setTitle("Lampirkan Foto");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Ambil Foto")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // Choose file storage location
                    File file = new File(Environment.getExternalStorageDirectory(), UUID.randomUUID().toString() + ".jpg");
//                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                    namefile = Uri.fromFile(file).getLastPathSegment();
                    startActivityForResult(intent, 0);
                } else if (options[item].equals("Pilih dari Galeri")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 1);

                } else if (options[item].equals("Batal")) {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                File file = new File(Environment.getExternalStorageDirectory().toString());
                foto = Uri.fromFile(file);
                Picasso.with(BuatberitaFragment.this.getContext()).load(file).resize(imfoto.getWidth(), 500).centerCrop().into(imfoto);
            } else {
                Toast.makeText(BuatberitaFragment.this.getContext(), "Foto gagal diambil, silahkan coba lagi", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = BuatberitaFragment.this.getContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();
                File file = new File(filePath);
                foto = Uri.fromFile(file);
                Picasso.with(BuatberitaFragment.this.getContext()).load(file).resize(imfoto.getWidth(), 500).centerCrop().into(imfoto);
            } else {
                Toast.makeText(BuatberitaFragment.this.getContext(), "Foto gagal dipilih, silahkan coba lagi", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void Submit(Uri fileUri) {
        try {
            final StorageReference photoRef = mStorageRef.child(userData.getUser_id()).child("galangdana").child(fileUri.getLastPathSegment());
            photoRef.putFile(fileUri)
                    .addOnSuccessListener(BuatberitaFragment.this.getActivity(), new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests")
                            final String urlgambar = taskSnapshot.getMetadata().getPath();
//                            Toast.makeText(BuatberitaFragment.this.getContext(), urlgambar, Toast.LENGTH_SHORT).show();
                        /*Glide.with(getApplicationContext())
                                .using(new FirebaseImageLoader())
                                .load(photoRef)
                                .dontAnimate()
                                .into(gambarprof);*/
                            final long id = Long.parseLong(userData.getUser_id() + "1");
                            Query query = mDatabase.getReference("user").child("profil").child(userData.getUser_id()).child("galang_dana");
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        long val = dataSnapshot.getChildrenCount();
                                        berita = new Berita(id + val, judul.getText().toString(), deadline.getText().toString(), sp.getSelectedItem().toString(), lokasi.getText().toString(), cerita.getText().toString(), urlgambar, false, false, Long.parseLong(dana.getText().toString()), 0);
                                        mDatabase.getReference("user").child("profil").child(userData.getUser_id()).child("galang_dana").child(String.valueOf(id + val)).child("berita").setValue(berita);
                                        mDatabase.getReference("admin").child("galang_dana").child("belum_terverifikasi").child(String.valueOf(id + val)).child("berita").setValue(berita);
                                        progressdialog.dismiss();
                                    } else {
                                        berita = new Berita(id, judul.getText().toString(), deadline.getText().toString(), sp.getSelectedItem().toString(), lokasi.getText().toString(), cerita.getText().toString(), urlgambar, false, false, Long.parseLong(dana.getText().toString()), 0);
                                        mDatabase.getReference("user").child("profil").child(userData.getUser_id()).child("galang_dana").child(String.valueOf(id)).child("berita").setValue(berita);
                                        mDatabase.getReference("admin").child("galang_dana").child("belum_terverifikasi").child(String.valueOf(id)).child("berita").setValue(berita);
                                        progressdialog.dismiss();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    progressdialog.dismiss();
                                }
                            });
                        }
                    }).addOnFailureListener(BuatberitaFragment.this.getActivity(), new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressdialog.dismiss();
                    Toast.makeText(BuatberitaFragment.this.getContext(), "Error: upload failed", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            progressdialog.dismiss();
            e.printStackTrace();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

}
