package com.example.phones.ui;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.phones.R;

import java.util.Calendar;


public class EditFragment extends Fragment {

    public static final String EDIT = "Edit";
    int position;
    static String date ;

    public EditFragment() {
        // Required empty public constructor
    }


    public static EditFragment newInstance(int pos) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putInt(EDIT, pos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments!= null){
            position = arguments.getInt(EDIT);
        }

        EditText editName = view.findViewById(R.id.editPhoneName);
        EditText editDescription = view.findViewById(R.id.editPhoneDescription);
        Button editDate = view.findViewById(R.id.editDate);
        Button saveEdit = view.findViewById(R.id.save);

        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerFragment datePickerFragment = new DatePickerFragment();
                datePickerFragment.show(getChildFragmentManager(), "datePicker");

            }
        });

        saveEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editName.getText().toString().trim().length()>0) {
                    MainViewModel.phonesArrayList.get(position).setName(editName.getText().toString());
                }
                if (editDescription.getText().toString().trim().length()>0) {

                    MainViewModel.phonesArrayList.get(position).setDescriptions(editDescription.getText().toString());
                }
                if (date!=null){
                    MainViewModel.phonesArrayList.get(position).setDate(date);
                }

                requireActivity().getSupportFragmentManager().popBackStack();
            }

        });

    }

    public static class DatePickerFragment extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener{

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(requireContext(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

          date = datePicker.getDayOfMonth() + "." + datePicker.getMonth() + "." + datePicker.getYear();


        }


    }
}