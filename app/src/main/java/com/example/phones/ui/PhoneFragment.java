package com.example.phones.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.phones.R;
import com.example.phones.model.Phones;
import com.example.phones.model.Publisher;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;


public class PhoneFragment extends Fragment {

    private static final String ARG_PHONE_DATA = "Param_PhoneData";
    private Phones phones;
    private Publisher publisher;

    private TextInputEditText title;
    private TextInputEditText description;
    private DatePicker datePicker;

    public static PhoneFragment newInstance(Phones phones){
        PhoneFragment phoneFragment = new PhoneFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PHONE_DATA, phones);
        phoneFragment.setArguments(args);
        return  phoneFragment;
    }

    public static PhoneFragment newInstance(){
        PhoneFragment phoneFragment = new PhoneFragment();
        return  phoneFragment;
    }

    public PhoneFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            phones = getArguments().getParcelable(ARG_PHONE_DATA);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        publisher = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phone, container, false);
        initView(view);
        if (phones!=null){
            populateView();
        }
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        publisher.notifySingle(phones);
    }

    private void populateView() {
        title.setText(phones.getName());
        description.setText(phones.getDescriptions());
        initDatePicker(phones.getDate());
    }

    private Phones collectPhones(){
        String title = this.title.getText().toString();
        String description = this.description.getText().toString();
        Date date = getDateFromDatePicker();


        return new Phones(title, description, R.drawable.iphone, date);
    }

    @Override
    public void onStop() {
        super.onStop();
        phones = collectPhones();
    }

    private Date getDateFromDatePicker(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, datePicker.getYear());
        calendar.set(Calendar.MONTH, datePicker.getMonth());
        calendar.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
        return calendar.getTime();

    }

    private void initDatePicker(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null);

    }

    private void initView(View view) {
        title = view.findViewById(R.id.inputTitle);
        description = view.findViewById(R.id.inputDescription);
        datePicker = view.findViewById(R.id.inputDate);
    }


}