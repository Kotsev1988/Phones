package com.example.phones.repository;

import com.example.phones.model.PhoneAdapter;
import com.example.phones.model.Phones;

import java.util.ArrayList;

public interface Repository {
    ArrayList<Phones> getData();
    void delete(int pos);
    void update(int pos, Phones phones);
    void add(Phones phones);
    Phones getPhone(int pos);
    void addNewPhones(ArrayList<Phones> phones);

}
