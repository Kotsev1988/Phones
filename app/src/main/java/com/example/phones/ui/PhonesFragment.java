package com.example.phones.ui;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.phones.R;
import com.example.phones.model.OnItemClickListener;
import com.example.phones.model.PhoneAdapter;
import com.example.phones.model.Phones;

import java.util.ArrayList;

public class PhonesFragment extends Fragment {

    TextView nameText;
    TextView descriptionText;
    TextView dateText;
    MainViewModel vm;
    View dataConteiner;
    private ArrayList<Phones> phonesArrayList = new ArrayList<>();

    private static final String CURRENT_PHONE = "phone";

    Phones phone;
    View viewSeparate;


    EditText search;
    LinearLayout searchLinearLayot;
    Button searchButton;
    RecyclerView recyclerView;
    int pos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View rootView = inflater.inflate(R.layout.fragment_phones, container, false);
        vm = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        if (savedInstanceState == null) {
            if (phonesArrayList.size() == 0) {
                phonesArrayList = vm.getPhonesArrayList(true);
            }
        }else if (savedInstanceState.getString("true")!=null){
            phonesArrayList = vm.getPhonesArrayList(false);
        }
        recyclerView = rootView.findViewById(R.id.recyclerView);
        initRecyclerView(recyclerView, phonesArrayList);
        return rootView;
    }

    private void initRecyclerView(RecyclerView recyclerView, ArrayList<Phones> phonesArrayList) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        PhoneAdapter phoneAdapter = new PhoneAdapter(phonesArrayList);
        recyclerView.setAdapter(phoneAdapter);


        phoneAdapter.setClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                System.out.println("Click On Card " + position + " " + view);
                phone = phonesArrayList.get(position);
                pos = position;
                System.out.println("Position "+pos);
                showPhones(phonesArrayList.get(position));
            }

            @Override
            public void onEditIconClick(View v, int position) {
                phone = phonesArrayList.get(position);
                EditFragment editFragment = EditFragment.newInstance(position);

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment, editFragment)
                        .addToBackStack("")
                        .commit();


            }

            @Override
            public void onLingClick(View v, int pos) {

                initPopupMenu(v, pos);
            }
        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        phone = phonesArrayList.get(0);

        vm.getPhone1().observe(getViewLifecycleOwner(), new Observer<Phones>() {
            @Override
            public void onChanged(Phones phones) {
                dateText = (TextView) view.findViewWithTag(phones.getName());
                dateText.setText(phones.getDate());
            }
        });

        if (savedInstanceState != null) {
            phone = savedInstanceState.getParcelable(CURRENT_PHONE);
        }

        if (isLand()) {

            DetailsFragment detailsFragment = DetailsFragment.newInstance(phone);
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentDetails, detailsFragment)
                    .addToBackStack("")
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_search:
                item.setVisible(false);
                searchLinearLayot = requireActivity().findViewById(R.id.searchLinearLayot);
                searchLinearLayot.setVisibility(View.VISIBLE);
                search = requireActivity().findViewById(R.id.searchText);
                search.setVisibility(View.VISIBLE);
                searchButton = requireActivity().findViewById(R.id.searchButton);
                searchButton.setVisibility(View.VISIBLE);

                searchButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        searchPhones(search.getText().toString());
                    }
                });
                return true;
            case R.id.action_exit:
                MyDialogFragmentClose myDialogFragmentClose = new MyDialogFragmentClose();
                myDialogFragmentClose.show(requireActivity().getSupportFragmentManager(), "MyDialog");
                myDialogFragmentClose.setListener(new MyDialogFragmentClose.setListener() {

                    @Override
                    public void actionPositive(String s) {
                        requireActivity().finish();
                    }

                    @Override
                    public void actionNegative(String s) {
                        return;
                    }
                });

                return true;

            case R.id.searchOver:
                if (search != null && searchButton != null && searchLinearLayot != null) {
                    search.setVisibility(View.GONE);
                    searchButton.setVisibility(View.GONE);
                    item.setVisible(true);
                    searchLinearLayot.setVisibility(View.GONE);
                    // init(dataConteiner);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initPopupMenu(View view, int index) {
        view.setOnLongClickListener(v -> {
            Activity activity = requireActivity();
            PopupMenu popupMenu = new PopupMenu(activity, v);
            activity.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(menuItem -> {

                MyDialogFragmentDelete myDialogFragment = new MyDialogFragmentDelete();
                myDialogFragment.show(requireActivity().getSupportFragmentManager(), "MyDialog");
                myDialogFragment.setListener(new MyDialogFragmentDelete.setListener() {

                    @Override
                    public void actionPositive(String s) {
                        if (menuItem.getItemId() == R.id.popup_delete) {
                            String name = phonesArrayList.get(index).getName();
                            phonesArrayList.remove(index);
                            init();
                            phone = phonesArrayList.get(index);
                            Toast.makeText(requireActivity(), "Заметка " + name + " удалена", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void actionNegative(String s) {
                        return;
                    }
                });


                return true;
            });
            popupMenu.show();
            return true;
        });


    }

    public void init(){
        recyclerView.getAdapter().notifyDataSetChanged();
        phone = phonesArrayList.get(0);
    }

    private void searchPhones(String phone) {
        for (int i = 0; i < phonesArrayList.size(); i++) {
            if (phonesArrayList.get(i).getName().equals(phone)) {
                System.out.println("hasMatches " + phonesArrayList.get(i).getName() + " = " + phone);

                //initAfterSearch(dataConteiner,phonesArrayList.get(i));
                break;
            }
        }
    }

    private void showPhones(Phones phones) {

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            DetailsFragment detailsFragment = DetailsFragment.newInstance(phones);

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment, detailsFragment)
                    .addToBackStack("")
                    .commit();
        } else {
            DetailsFragment detailsFragment = DetailsFragment.newInstance(phones);

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentDetails, detailsFragment)
                    .addToBackStack("")
                    .commit();
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        // outState.putInt(CURRENT_PHONE, currentPhone);
        outState.putString("true", "true");
        outState.putParcelable(CURRENT_PHONE, phone);
        super.onSaveInstanceState(outState);

    }

    private boolean isLand() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
}

