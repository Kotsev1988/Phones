package com.example.phones.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyDialogFragmentClose extends androidx.fragment.app.DialogFragment {

    private setListener listener;
    public interface setListener{
        void actionPositive(String s);
        void actionNegative(String s);
    }

    public void setListener(setListener listener){
        this.listener = listener;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Activity activity = requireActivity();

        return new AlertDialog.Builder(activity)
                .setTitle("Вы действительно хотите закрыть приложение?")
                .setMessage("Закрытие")
                .setPositiveButton("Да", (dialogInterface, i) -> listener.actionPositive("yes")).setNegativeButton("Нет", (dialogInterface, i) -> {
                    listener.actionNegative("no");
                }).setNeutralButton("Отмена", null)
                .create();
    }
}
