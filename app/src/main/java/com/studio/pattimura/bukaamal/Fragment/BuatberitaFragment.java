package com.studio.pattimura.bukaamal.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.studio.pattimura.bukaamal.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuatberitaFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    private Spinner sp;
    private EditText deadline;

    public BuatberitaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buatberita, container, false);
        sp = (Spinner) view.findViewById(R.id.spinKategori);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.Kategori, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        sp.setAdapter(adapter);

        deadline = (EditText) view.findViewById(R.id.edDeadline);

        deadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });

        return view;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        deadline.setText(dayOfMonth + "/" + (++monthOfYear) + "/" + year);
    }
}
