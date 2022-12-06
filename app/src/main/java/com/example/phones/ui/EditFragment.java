package com.example.phones.ui;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.phones.R;
import com.example.phones.model.Phones;

import java.util.Calendar;
import java.util.Date;


public class EditFragment extends Fragment {

    public static final String EDIT = "Edit";
    public static final String IMG = "img";
    int position;
    static Date date ;
    MainViewModel vm;
    Phones phones;
    int img;


    public EditFragment() {
        // Required empty public constructor
    }



    public static EditFragment newInstance(int pos, int image) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putInt(EDIT, pos);
        args.putInt(IMG, image);
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

        vm = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        Bundle arguments = getArguments();
        if (arguments!= null){
            position = arguments.getInt(EDIT);
            img = arguments.getInt(IMG);
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
                phones = new Phones();
                if (editName.getText().toString().trim().length()>0) {
                   // MainRepoitory.phonesArrayList.get(position).setName(editName.getText().toString());
                    phones.setName(editName.getText().toString());
                }
                if (editDescription.getText().toString().trim().length()>0) {

                    //MainRepoitory.phonesArrayList.get(position).setDescriptions(editDescription.getText().toString());
                    phones.setDescriptions(editDescription.getText().toString());
                }
                if (date!=null){
                    //MainRepoitory.phonesArrayList.get(position).setDate(date);
                    phones.setDate(date);
                }
                if (img>0) {
                    phones.setImage(img);
                }
                vm.update(position, phones);

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

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, datePicker.getYear());
            calendar.set(Calendar.MONTH, datePicker.getMonth());
            calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());

          date = calendar.getTime();;


        }


    }
}