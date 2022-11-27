package com.example.phones.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.phones.R;
import com.example.phones.model.Phones;

public class DatePickerFragment extends Fragment {

DatePicker datePicker;
 static final String ArgTime = "TIME";
String index;
private Phones phone;
MainViewModel vm;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_date_picker, container, false);
         datePicker = view.findViewById(R.id.dateSet);


        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments!= null) {

            phone = arguments.getParcelable(ArgTime);
            System.out.println("Phone "+phone.getName());

            vm = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

            datePicker.init(2020, 02, 01, new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {

                    vm.setDate(phone, datePicker.getDayOfMonth() + "." + datePicker.getMonth() + "." + datePicker.getYear());
                    requireActivity().getSupportFragmentManager().popBackStack();
                }
            });
        }
    }

    public static DatePickerFragment newInstance(Phones phones){
        DatePickerFragment datePickerFragment = new DatePickerFragment();
        Bundle args = new Bundle();
        args.putParcelable(ArgTime, phones);
        datePickerFragment.setArguments(args);
        return  datePickerFragment;
    }



}