package com.example.phones.ui;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.phones.R;
import com.example.phones.model.Phones;

import java.util.ArrayList;


public class MainViewModel extends ViewModel {


    public static ArrayList<Phones> phonesArrayList = new ArrayList<>();


    private final MutableLiveData<Phones> phonesVM = new MutableLiveData<>();
    LiveData<Phones> phone1 = phonesVM;


    public LiveData<Phones> getPhone1() {
        return phone1;
    }




    public void setDate(Phones phone, String date) {

        for (int i = 0; i < phonesArrayList.size(); i++) {
            if (phonesArrayList.get(i).equals(phone)) {

                System.out.println("Equals " + phonesArrayList.get(i).getName());
                phonesArrayList.get(i).setDate(date);
                phonesVM.setValue(phonesArrayList.get(i));
                break;
            }
        }

    }


    public void delete(int pos){
        phonesArrayList.remove(pos);
        for (int i = 0; i < phonesArrayList.size(); i++) {
                phonesVM.setValue(phonesArrayList.get(i));
        }
    }

    public ArrayList<Phones> getPhonesArrayList(boolean isSetup) {
        if (isSetup) {
            phonesArrayList.clear();
            phonesArrayList.add(new Phones("Samsung", "Samsung is cool phone", R.drawable.samsung, "14.11.2022"));
            phonesArrayList.add(new Phones("Iphone", "Iphone is popular phone", R.drawable.iphone, "14.11.2022"));
            phonesArrayList.add(new Phones("Xiaomi", "Xiamoi  it is the second largest manufacturer of smartphones in the world, most of which run the MIUI operating system.", R.drawable.xiaomi, "14.11.2022"));
            phonesArrayList.add(new Phones("Nokia", "Nokia is a Finnish multinational telecommunications, information technology, and consumer electronics corporation, established in 1865", R.drawable.nokia, "14.11.2022"));
            phonesArrayList.add(new Phones("Honor", "Honor is is a smartphone brand majority owned by a state-owned enterprise controlled by the municipal government of Shenzhe", R.drawable.honor, "14.11.2022"));
        }
        return phonesArrayList;
    }
}
