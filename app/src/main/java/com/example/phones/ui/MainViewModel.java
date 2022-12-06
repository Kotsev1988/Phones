package com.example.phones.ui;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.phones.R;
import com.example.phones.model.Phones;
import com.example.phones.repository.MainRepoitory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;


public class MainViewModel extends ViewModel {

    MainRepoitory repoitory ;

    public MainViewModel(MainRepoitory repoitory){
        this.repoitory =repoitory;
    }

    private final MutableLiveData<ArrayList<Phones>> phonesVM = new MutableLiveData<>();
    LiveData<ArrayList<Phones>> phone1 = phonesVM;
    public LiveData<ArrayList<Phones>> getPhone1() {
        return phone1;
    }

    public void setDate(Phones phone, String date) {
      /*  for (int i = 0; i < phonesArrayList.size(); i++) {
            if (phonesArrayList.get(i).equals(phone)) {

                System.out.println("Equals " + phonesArrayList.get(i).getName());
                phonesArrayList.get(i).setDate(date);
                phonesVM.setValue(phonesArrayList.get(i));
                break;
            }
        }*/
    }

    public void update(int pos, Phones phones){
        repoitory.update(pos, phones);
        phonesVM.postValue(repoitory.getData());
    }

    public void delete(int pos){
        repoitory.delete(pos);
        phonesVM.postValue(repoitory.getData());
    }

    public Phones getPhone(int pos){
        return repoitory.getPhone(pos);
    }

    public void add(Phones phones){
        repoitory.add(phones);
    }

    public ArrayList<Phones> getPhonesArrayList() {return repoitory.getData();}
}
