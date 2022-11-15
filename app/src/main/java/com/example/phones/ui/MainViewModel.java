package com.example.phones.ui;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.phones.R;
import com.example.phones.model.Phones;

import java.util.ArrayList;


public class MainViewModel extends ViewModel {

Phones[] phones;


    private final MutableLiveData<Phones> phonesVM = new MutableLiveData<>();
    LiveData<Phones> phone1 = phonesVM;


    public LiveData<Phones> getPhone1() {
        return phone1;
    }

    public void updatePhones(Phones phone) {
        System.out.println("PhoneVM1 "+phone.getName());
        //phone1.getValue().setDate(phone.getDate());
        phonesVM.setValue(phone);



    }
}
