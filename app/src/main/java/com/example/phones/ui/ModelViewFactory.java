package com.example.phones.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.phones.repository.MainRepoitory;

public class ModelViewFactory implements ViewModelProvider.Factory {
MainRepoitory repository ;

public ModelViewFactory(MainRepoitory mainRepoitory){
    this.repository = mainRepoitory;
}

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MainViewModel.class)){
            return (T) new MainViewModel(repository);
        } else{
            return ViewModelProvider.Factory.super.create(modelClass);
        }

    }
}
