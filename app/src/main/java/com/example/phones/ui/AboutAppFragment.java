package com.example.phones.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.phones.R;

public class AboutAppFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_about_app, container, false);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragment_details, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        MenuItem menuItemSearchOver = menu.findItem(R.id.searchOver);
        MenuItem menuItemDelete = menu.findItem(R.id.action_delete);
        MenuItem menuItemSend = menu.findItem(R.id.action_send);
        if (menuItem != null) {
            menuItem.setVisible(false);
        }

        if (menuItemSearchOver != null) {
            menuItemSearchOver.setVisible(false);
        }
        if (menuItemDelete != null) {
            menuItemDelete.setVisible(false);
        }
        if (menuItemSend != null) {
            menuItemSend.setVisible(false);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_exit){
            requireActivity().finish();
        }
        return super.onOptionsItemSelected(item);
    }
}