package com.studio.pattimura.bukaamal.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.studio.pattimura.bukaamal.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class IdentitasFragment extends Fragment implements DatePickerDialog.OnDateSetListener{
    private EditText tglLahir;
    public IdentitasFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_identitas, container, false);
        tglLahir = (EditText) view.findViewById(R.id.edTanggalLahir);

        tglLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        IdentitasFragment.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.setAccentColor(ContextCompat.getColor(IdentitasFragment.this.getContext(), R.color.merahBukaAmal));
                dpd.dismissOnPause(true);
                dpd.showYearPickerFirst(true);
                dpd.setVersion(DatePickerDialog.Version.VERSION_2);
                dpd.show(IdentitasFragment.this.getActivity().getFragmentManager(), "Tanggal");
            }
        });

        return view;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        tglLahir.setText(dayOfMonth + "/" + (++monthOfYear) + "/" + year);
    }
}
