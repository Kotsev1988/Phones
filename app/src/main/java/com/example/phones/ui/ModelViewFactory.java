package com.example.phones.ui;

import androidx.lifecycle.ViewModelProvider;

public class ModelViewFactory implements ViewModelProvider.Factory {

  /*  Phones[] phones = new Phones[]{
            new Phones("Samsung", "Samsung is cool phone", R.drawable.samsung, "14.11.2022"),
            new Phones("Iphone", "Iphone is popular phone", R.drawable.iphone, "14.11.2022"),
            new Phones("Xiaomi", "Xiamoi  it is the second largest manufacturer of smartphones in the world, most of which run the MIUI operating system.", R.drawable.xiaomi, "14.11.2022"),
            new Phones("Nokia", "Nokia is a Finnish multinational telecommunications, information technology, and consumer electronics corporation, established in 1865", R.drawable.nokia, "14.11.2022"),
            new Phones("Honor", "Honor is is a smartphone brand majority owned by a state-owned enterprise controlled by the municipal government of Shenzhe", R.drawable.honor, "14.11.2022"),

    };

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)){
            return (T) new MainViewModel(phones);
        } else{
            return ViewModelProvider.Factory.super.create(modelClass);
        }

    }*/
}
