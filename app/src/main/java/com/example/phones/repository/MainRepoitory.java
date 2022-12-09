package com.example.phones.repository;

import com.example.phones.R;
import com.example.phones.model.PhoneAdapter;
import com.example.phones.model.Phones;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class MainRepoitory implements Repository{
   /* public static ArrayList<Phones> phonesArrayList =new ArrayList<Phones>( Arrays.asList(

            new Phones("Samsung", "Samsung is cool phone", R.drawable.samsung, new Date()),
            new Phones("Iphone", "Iphone is popular phone", R.drawable.iphone, new Date()),
            new Phones("Xiaomi", "Xiamoi  it is the second largest manufacturer of smartphones in the world, most of which run the MIUI operating system.", R.drawable.xiaomi, new Date()),
            new Phones("Nokia", "Nokia is a Finnish multinational telecommunications, information technology, and consumer electronics corporation, established in 1865", R.drawable.nokia, new Date()),
            new Phones("Honor", "Honor is is a smartphone brand majority owned by a state-owned enterprise controlled by the municipal government of Shenzhe", R.drawable.honor, new Date())

    )
    );*/

    public ArrayList<Phones> phonesArrayList =new ArrayList<Phones>();
    @Override
    public ArrayList<Phones> getData() {
        return phonesArrayList;
    }

    @Override
    public void delete(int pos) {
        phonesArrayList.remove(pos);
    }

    @Override
    public void update(int pos, Phones phones) {
        phonesArrayList.set(pos, phones);
    }

    @Override
    public void add(Phones phones) {
        phonesArrayList.add(phones);
    }

    @Override
    public Phones getPhone(int pos) {
        return phonesArrayList.get(pos);
    }

    @Override
    public void addNewPhones(ArrayList<Phones> phones) {
        phonesArrayList = phones;
    }





}
