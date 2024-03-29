package com.studio.pattimura.bukaamal.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

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
import com.studio.pattimura.bukaamal.GalangDana;
import com.studio.pattimura.bukaamal.Model.Berita;
import com.studio.pattimura.bukaamal.Model.Identitas;
import com.studio.pattimura.bukaamal.Model.userAuth;
import com.studio.pattimura.bukaamal.Model.userProfile;
import com.studio.pattimura.bukaamal.R;
import com.studio.pattimura.bukaamal.Utility;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.io.File;
import java.util.Calendar;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class IdentitasFragment extends Fragment implements View.OnClickListener {
    private EditText nama, alamat, norek, notel;
    private Spinner sp;
    private Button lampirfoto, simpanlanjut;
    private Uri foto = null;
    private String namefile;
    private ImageView imfoto;
    private FirebaseDatabase mDatabase;
    private StorageReference mStorageRef;
    private FirebaseAuth mAuth;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private userProfile userProfile;
    private userAuth userData;
    private ProgressDialog progressdialog;
    private File files;
    public static TabLayout tabLayout;
    private GalangDana g;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;

    public IdentitasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_identitas, container, false);

        g = (GalangDana) getActivity();
        tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);

        preferences = this.getActivity().getSharedPreferences("prefProfile", MODE_PRIVATE);
        editor = preferences.edit();
        Gson gson = new Gson();
        String json = preferences.getString("prefProfile", "");
        userProfile = gson.fromJson(json, userProfile.class);

        preferences = this.getActivity().getSharedPreferences("prefTok", MODE_PRIVATE);
        editor = preferences.edit();
        gson = new Gson();
        json = preferences.getString("prefTok", "");
        userData = gson.fromJson(json, userAuth.class);

        mDatabase = FirebaseDatabase.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        lampirfoto = (Button) view.findViewById(R.id.btnFoto);
        lampirfoto.setOnClickListener(this);
        simpanlanjut = (Button) view.findViewById(R.id.btnSimpanLanjut);
        simpanlanjut.setOnClickListener(this);
        nama = (EditText) view.findViewById(R.id.edNamaLengkap);
        alamat = (EditText) view.findViewById(R.id.edAlamat);
        norek = (EditText) view.findViewById(R.id.edNoRek);
        notel = (EditText) view.findViewById(R.id.edNoTelp);
        imfoto = (ImageView) view.findViewById(R.id.fotoKTP);
        imfoto.setOnClickListener(this);
        imfoto.setAdjustViewBounds(true);
        if (!userProfile.getNama().equals("null"))
            nama.setText(userProfile.getNama());
        if (!userProfile.getAlamat().equals("null, null"))
            alamat.setText(userProfile.getAlamat());
        if (!userProfile.getTelepon().equals("null"))
            notel.setText(userProfile.getTelepon());

        sp = (Spinner) view.findViewById(R.id.spinBank);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.KategoriBank, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);

        if (g.getIdentitas() != null) {
            setIdentitas();
        }

        progressdialog = new ProgressDialog(this.getContext());

        return view;
    }


    @Override
    public void onClick(View view) {
        if (view == lampirfoto) {
            photoBuilder();
        } else if (view == imfoto) {

        } else if (view == simpanlanjut) {
            if (!isValid()) {
                Toast.makeText(IdentitasFragment.this.getContext(), "Lengkapi Data Berita Terlebih Dahulu", Toast.LENGTH_SHORT).show();
            } else {
                if (!isOnline()) {
                    Toast.makeText(this.getContext(), "Cek Koneksi Internet Anda", Toast.LENGTH_SHORT).show();
                } else {
                    if (g.getBerita() != null) {
                        if (!g.getBerita().getStatus()) {
                            progressdialog.setMessage("Mohon tunggu...");
                            progressdialog.show();
                            Submit(foto);
                        } else {
                            Toast.makeText(this.getContext(), "Data Sudah Diverifikasi Tidak Bisa Di Ubah", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        progressdialog.setMessage("Mohon tunggu...");
                        progressdialog.show();
                        Submit(foto);
                    }
                }
            }
        }
    }

    @NonNull
    private Boolean isValid() {
        EditText[] fields = {nama, alamat, norek, notel};
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
        AlertDialog.Builder builder = new AlertDialog.Builder(IdentitasFragment.this.getContext());
        builder.setTitle("Lampirkan Foto");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(IdentitasFragment.this.getActivity());
                if (options[item].equals("Ambil Foto")) {
                    if (result) {
//                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        // Choose file storage location
//                        File file = new File(Environment.getExternalStorageDirectory(), UUID.randomUUID().toString() + ".jpg");
//                        files = file;
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
//                        namefile = Uri.fromFile(file).getLastPathSegment();
//                        startActivityForResult(intent, 0);
                        userChoosenTask = "Take Photo";
                        if (result)
                            cameraIntent();
                    }
                } else if (options[item].equals("Pilih dari Galeri")) {
//                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    startActivityForResult(intent, 1);
                    userChoosenTask = "Choose from Library";
                    if (result)
                        galleryIntent();

                } else if (options[item].equals("Batal")) {
                    dialog.dismiss();
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void galleryIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, SELECT_FILE);
    }

    private void cameraIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Choose file storage location
        File file = new File(Environment.getExternalStorageDirectory(), UUID.randomUUID().toString() + ".jpg");
        files = file;
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        namefile = Uri.fromFile(file).getLastPathSegment();
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        } else {
            Toast.makeText(IdentitasFragment.this.getContext(), "Foto gagal diambil, silahkan coba lagi", Toast.LENGTH_SHORT).show();
        }
//        if (requestCode == 0) {
//            if (resultCode == RESULT_OK) {
//
//                foto = Uri.fromFile(files);
////                picKtp = Drawable.createFromPath(foto.getPath());
//                Picasso.with(BuatberitaFragment.this.getContext()).load(files).resize(imfoto.getWidth(), 500).centerCrop().into(imfoto);
//            } else {
//                Toast.makeText(BuatberitaFragment.this.getContext(), "Foto gagal diambil, silahkan coba lagi", Toast.LENGTH_SHORT).show();
//            }
//        } else if (requestCode == 1) {
//            if (resultCode == RESULT_OK) {
//                Uri selectedImage = data.getData();
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                Cursor cursor = BuatberitaFragment.this.getContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//                cursor.moveToFirst();
//                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                String filePath = cursor.getString(columnIndex);
//                cursor.close();
//                File file = new File(filePath);
//                foto = Uri.fromFile(file);
//                Picasso.with(BuatberitaFragment.this.getContext()).load(file).resize(imfoto.getWidth(), 500).centerCrop().into(imfoto);
//            } else {
//                Toast.makeText(BuatberitaFragment.this.getContext(), "Foto gagal dipilih, silahkan coba lagi", Toast.LENGTH_SHORT).show();
//            }
//        }
    }

    private void onCaptureImageResult(Intent data) {
//        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

//        File destination = new File(Environment.getExternalStorageDirectory(),
//                System.currentTimeMillis() + ".jpg");
        foto = Uri.fromFile(files);

//        FileOutputStream fo;
//        try {
//            destination.createNewFile();
//            fo = new FileOutputStream(destination);
//            fo.write(bytes.toByteArray());
//            fo.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Picasso.with(IdentitasFragment.this.getContext()).load(files).resize(imfoto.getWidth(), 500).centerCrop().into(imfoto);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm = null;
        if (data != null) {
//            try {
//                bm = MediaStore.Images.Media.getBitmap(BuatberitaFragment.this.getActivity().getApplicationContext().getContentResolver(), data.getData());
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = IdentitasFragment.this.getContext().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();
            File file = new File(filePath);
            foto = Uri.fromFile(file);
            Picasso.with(IdentitasFragment.this.getContext()).load(file).resize(imfoto.getWidth(), 500).centerCrop().into(imfoto);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }


    }

    public void Submit(Uri fileUri) {
        try {
            final StorageReference photoRef = mStorageRef.child(userData.getUser_id()).child("galangdana").child(fileUri.getLastPathSegment());
            photoRef.putFile(fileUri)
                    .addOnSuccessListener(IdentitasFragment.this.getActivity(), new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests")
                            final String urlgambar = taskSnapshot.getMetadata().getPath();
//                            Toast.makeText(IdentitasFragment.this.getContext(), urlgambar, Toast.LENGTH_SHORT).show();
                        /*Glide.with(getApplicationContext())
                                .using(new FirebaseImageLoader())
                                .load(photoRef)
                                .dontAnimate()
                                .into(gambarprof);*/
                            final long id = Long.parseLong(userData.getUser_id() + "1");
                            Query query = mDatabase.getReference("user").child("profil").child(userData.getUser_id()).child("galang_dana").child(String.valueOf(id));
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        long val = dataSnapshot.getChildrenCount();
                                        if (val == 1) {
                                            userProfile.setRekening(norek.getText().toString());
                                            userProfile.setKtp(urlgambar);
                                            mDatabase.getReference("user").child("profil").child(userData.getUser_id()).child("galang_dana").child(String.valueOf(id)).child("identitas").setValue(userProfile);
                                            mDatabase.getReference("user").child("profil").child(userData.getUser_id()).child("galang_dana").child(String.valueOf(id)).child("identitas").child("id").setValue(userData.getUser_id());
                                            mDatabase.getReference("user").child("profil").child(userData.getUser_id()).child("galang_dana").child(String.valueOf(id)).child("identitas").child("bank").setValue(sp.getSelectedItem().toString());
                                            g.setIdentitasTrue(true);
                                            progressdialog.dismiss();
                                            TabLayout.Tab tab = tabLayout.getTabAt(2);
                                            tab.select();
                                            mDatabase.getReference("admin").child("galang_dana").child("belum_terverifikasi").child(String.valueOf(id)).child("identitas").setValue(userProfile);
                                            mDatabase.getReference("admin").child("galang_dana").child("belum_terverifikasi").child(String.valueOf(id)).child("identitas").child("id").setValue(userData.getUser_id());
                                            mDatabase.getReference("admin").child("galang_dana").child("belum_terverifikasi").child(String.valueOf(id)).child("identitas").child("bank").setValue(sp.getSelectedItem().toString());
                                        } else {
                                            if (g.getBerita() != null) {
                                                userProfile.setRekening(norek.getText().toString());
                                                userProfile.setKtp(urlgambar);
                                                mDatabase.getReference("user").child("profil").child(userData.getUser_id()).child("galang_dana").child(String.valueOf(g.getBerita().getId())).child("identitas").setValue(userProfile);
                                                mDatabase.getReference("user").child("profil").child(userData.getUser_id()).child("galang_dana").child(String.valueOf(g.getBerita().getId())).child("identitas").child("id").setValue(userData.getUser_id());
                                                mDatabase.getReference("user").child("profil").child(userData.getUser_id()).child("galang_dana").child(String.valueOf(g.getBerita().getId())).child("identitas").child("bank").setValue(sp.getSelectedItem().toString());
                                                g.setIdentitasTrue(true);
                                                progressdialog.dismiss();
                                                TabLayout.Tab tab = tabLayout.getTabAt(2);
                                                tab.select();
                                                mDatabase.getReference("admin").child("galang_dana").child("belum_terverifikasi").child(String.valueOf(g.getBerita().getId())).child("identitas").setValue(userProfile);
                                                mDatabase.getReference("admin").child("galang_dana").child("belum_terverifikasi").child(String.valueOf(g.getBerita().getId())).child("identitas").child("id").setValue(userData.getUser_id());
                                                mDatabase.getReference("admin").child("galang_dana").child("belum_terverifikasi").child(String.valueOf(g.getBerita().getId())).child("identitas").child("bank").setValue(sp.getSelectedItem().toString());
                                            } else {
                                                userProfile.setRekening(norek.getText().toString());
                                                userProfile.setKtp(urlgambar);
                                                mDatabase.getReference("user").child("profil").child(userData.getUser_id()).child("galang_dana").child(String.valueOf(g.getIdBerita())).child("identitas").setValue(userProfile);
                                                mDatabase.getReference("user").child("profil").child(userData.getUser_id()).child("galang_dana").child(String.valueOf(g.getIdBerita())).child("identitas").child("id").setValue(userData.getUser_id());
                                                mDatabase.getReference("user").child("profil").child(userData.getUser_id()).child("galang_dana").child(String.valueOf(g.getIdBerita())).child("identitas").child("bank").setValue(sp.getSelectedItem().toString());
                                                g.setIdentitasTrue(true);
                                                progressdialog.dismiss();
                                                TabLayout.Tab tab = tabLayout.getTabAt(2);
                                                tab.select();
                                                mDatabase.getReference("admin").child("galang_dana").child("belum_terverifikasi").child(String.valueOf(g.getIdBerita())).child("identitas").setValue(userProfile);
                                                mDatabase.getReference("admin").child("galang_dana").child("belum_terverifikasi").child(String.valueOf(g.getIdBerita())).child("identitas").child("id").setValue(userData.getUser_id());
                                                mDatabase.getReference("admin").child("galang_dana").child("belum_terverifikasi").child(String.valueOf(g.getIdBerita())).child("identitas").child("bank").setValue(sp.getSelectedItem().toString());
                                            }
                                        }
//                                        progressdialog.dismiss();
                                    } else {
                                        /*userProfile.setRekening(norek.getText().toString());
                                        userProfile.setKtp(urlgambar);
                                        mDatabase.getReference("user").child("profil").child(userData.getUser_id()).child("galang_dana").child(String.valueOf(id)).child("identitas").setValue(userProfile);*/
                                        Toast.makeText(IdentitasFragment.this.getContext(), "Berita Belum Dilengkapi", Toast.LENGTH_SHORT).show();
                                        progressdialog.dismiss();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    progressdialog.dismiss();
                                }
                            });
                        }
                    }).addOnFailureListener(IdentitasFragment.this.getActivity(), new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressdialog.dismiss();
                    Toast.makeText(IdentitasFragment.this.getContext(), "Error: upload failed", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void setIdentitas() {
        nama.setText(g.getIdentitas().getNama());
        alamat.setText(g.getIdentitas().getAlamat());
        norek.setText(g.getIdentitas().getRekening());
        notel.setText(g.getIdentitas().getTelepon());
        if (g.getIdentitas().getBank().equals("BCA")) {
            sp.setSelection(0);
        } else if (g.getIdentitas().getBank().equals("BNI")) {
            sp.setSelection(1);
        } else if (g.getIdentitas().getBank().equals("BRI")) {
            sp.setSelection(2);
        } else if (g.getIdentitas().getBank().equals("MANDIRI")) {
            sp.setSelection(3);
        }
        mStorageRef = FirebaseStorage.getInstance().getReference(g.getIdentitas().getKtp());
        mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.with(IdentitasFragment.this.getContext()).load(uri).resize(imfoto.getWidth(), 500).centerCrop().into(imfoto);
            }
        });
        g.setIdentitasTrue(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (userChoosenTask.equals("Ambil Foto"))
                        cameraIntent();
                    else if (userChoosenTask.equals("Pilih Dari Galeri"))
                        galleryIntent();
                } else {
//code for deny
                    Toast.makeText(this.getContext(), "Akses Tidak Diizinkan", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
