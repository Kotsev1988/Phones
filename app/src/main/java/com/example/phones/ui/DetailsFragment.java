package com.example.phones.ui;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phones.R;
import com.example.phones.model.Phones;
import com.example.phones.repository.MainRepoitory;

public class DetailsFragment extends Fragment {

static final String ARGS = "index";
    private MenuItem menuItem;
    Phones phones;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            requireActivity().getSupportFragmentManager().popBackStack();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments!= null){
            System.out.println("arguments "+arguments.toString());
            phones = arguments.getParcelable(ARGS);
            System.out.println("arguments "+phones.getName());
            ImageView imageView = view.findViewById(R.id.imagePhone);
            TextView textView = view.findViewById(R.id.detailsOfPhone);
            TypedArray typedArray = getResources().obtainTypedArray(R.array.phoneImages);
            imageView.setImageResource(phones.getImage());
            TypedArray typedArray1 = getResources().obtainTypedArray(R.array.descriptions);
            textView.setText(phones.getDescriptions());
            typedArray.recycle();
            typedArray1.recycle();
        }

        view.findViewById(R.id.backButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        if (!isLand()) {

            inflater.inflate(R.menu.menu_fragment_details, menu);
            menuItem = menu.findItem(R.id.action_search);
            MenuItem menuItemSearchOver = menu.findItem(R.id.searchOver);
            if (menuItem != null) {
                menuItem.setVisible(false);
            }

            if (menuItemSearchOver != null) {
                menuItemSearchOver.setVisible(false);
            }
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete){
            String name = phones.getName();
            MainRepoitory.phonesArrayList.remove(phones);
            phones = null;
            for (Fragment fragment: requireActivity().getSupportFragmentManager().getFragments()){
                if (fragment instanceof PhonesFragment){
                    ((PhonesFragment)fragment).init();
                    break;
                }
            }
            Toast.makeText(requireActivity(), "Заметка "+name+" удалена", Toast.LENGTH_LONG).show();
            requireActivity().getSupportFragmentManager().popBackStack();
        }
        return super.onOptionsItemSelected(item);
    }

    public static DetailsFragment newInstance(Phones phones){
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGS, phones);
        detailsFragment.setArguments(args);
        return detailsFragment;
    }

    private boolean isLand() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
}