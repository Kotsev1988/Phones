package com.example.phones.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.ContextMenu;
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
import com.example.phones.model.Navigation;
import com.example.phones.model.OnItemClickListener;
import com.example.phones.model.PhoneAdapter;
import com.example.phones.model.Phones;
import com.example.phones.model.Publisher;
import com.example.phones.repository.MainRepoitory;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;

public class PhonesFragment extends Fragment {

    MainViewModel vm;
    ModelViewFactory modelViewFactory;
    MainRepoitory repository = new MainRepoitory();
    private ArrayList<Phones> phonesArrayList = new ArrayList<>();
    Phones phone;
    EditText search;
    LinearLayout searchLinearLayot;
    Button searchButton;
    RecyclerView recyclerView;
    PhoneAdapter phoneAdapter;
    private static final String CURRENT_PHONE = "phone";
    private static final int DURATION = 5000;

    private Navigation navigation;
    private Publisher publisher;
    private boolean moveToLastPosition;
    MenuItem menuItemSearchOver, menuItemSearch;

    private SharedPreferences sharedPreferences = null;
    public static final String KEY = "KEY";

    public static PhonesFragment newInstance() {
        return new PhonesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View rootView = inflater.inflate(R.layout.fragment_phones, container, false);
        modelViewFactory = new ModelViewFactory(repository);
        vm = new ViewModelProvider(requireActivity(), modelViewFactory).get(MainViewModel.class);

        phonesArrayList = repository.getData();
       // phone = phonesArrayList.get(0);
        /*if (savedInstanceState == null) {
            if (phonesArrayList.size() == 0) {
                phonesArrayList = repository.getData();
                phone = phonesArrayList.get(0);
            }
        } else if (savedInstanceState.getString("true") != null) {
            //phonesArrayList = vm.getPhonesArrayList();
        }*/
        recyclerView = rootView.findViewById(R.id.recyclerView);
        initRecyclerView();
        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }

    private void initRecyclerView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        phoneAdapter = new PhoneAdapter(phonesArrayList, this);

        recyclerView.setAdapter(phoneAdapter);

        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(DURATION);
        defaultItemAnimator.setRemoveDuration(DURATION);
        recyclerView.setItemAnimator(defaultItemAnimator);

        if (moveToLastPosition) {
            recyclerView.smoothScrollToPosition(phonesArrayList.size() - 1);
            moveToLastPosition = false;
        }
        String saved = sharedPreferences.getString(KEY, null);
        if (saved == null){
            Toast.makeText(getContext(), "Empty", Toast.LENGTH_SHORT).show();
        }else{

            try{
                Type type = new TypeToken<ArrayList<Phones>>(){}.getType();
               ArrayList<Phones> phones = new GsonBuilder().create().fromJson(saved, type);

                System.out.println("Phones "+phones.size());
                vm.addNewPhones(phones);
                phoneAdapter.setNewDataPhone(phones);
            }catch(Exception e){
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        }


        phoneAdapter.setClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                phone = phonesArrayList.get(position);
                showPhones(repository.getPhone(position));
            }

            @Override
            public void onEditIconClick(View v, int position) {
                phone = vm.getPhonesArrayList().get(position);
                EditFragment editFragment = EditFragment.newInstance(position, phone.getImage());

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment, editFragment)
                        .addToBackStack("")
                        .commit();
            }

            @Override
            public void onLingClick(View v, int pos) {
                //initPopupMenu(v, pos);
            }
        });
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vm.getPhone1().observe(getViewLifecycleOwner(), new Observer<ArrayList<Phones>>() {

            @Override
            public void onChanged(ArrayList<Phones> phones) {
                phonesArrayList = phones;
                phoneAdapter.notifyDataSetChanged();

            }
        });

       /* if (savedInstanceState != null) {
            phone = savedInstanceState.getParcelable(CURRENT_PHONE);
            System.out.println("getSavedPhone "+phone.getName());
        }*/

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
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = requireActivity().getMenuInflater();
        menuInflater.inflate(R.menu.card_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = phoneAdapter.getMenuPosition();
        System.out.println("Position " + position);
        switch (item.getItemId()) {
            case R.id.action_update:

                vm.update(position, new Phones("Some Phone", "Not bad phone", R.drawable.samsung, new Date()));
                phonesArrayList =vm.getPhonesArrayList();
                String phoneForUpdate = new GsonBuilder().create().toJson(phonesArrayList);
                sharedPreferences.edit().putString(KEY, phoneForUpdate).apply();

                vm.addNewPhones(vm.getPhonesArrayList());
               // phoneAdapter.notifyItemChanged(position);
                return true;
            case R.id.action_delet:
                vm.delete(position);

                phonesArrayList =vm.getPhonesArrayList();
                String phoneForSaving = new GsonBuilder().create().toJson(phonesArrayList);
                sharedPreferences.edit().putString(KEY, phoneForSaving).apply();

                vm.addNewPhones(vm.getPhonesArrayList());
              //  phoneAdapter.notifyItemInserted(phonesArrayList.size() - 1);
               // phoneAdapter.notifyItemRemoved(position);
                recyclerView.scrollToPosition(position);
                recyclerView.smoothScrollToPosition(position);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        for (int i = 0; i < menu.size(); i++) {
            if (menu.getItem(i).getItemId() == R.id.searchOver){
                menuItemSearchOver = menu.getItem(i);
                menuItemSearchOver.setVisible(false);
            }
            if (menu.getItem(i).getItemId() == R.id.action_search){
                menuItemSearch = menu.getItem(i);
            }
        }
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();


        switch (id) {
            case R.id.action_search:
                menuItemSearch.setVisible(false);
                menuItemSearchOver.setVisible(true);
                searchLinearLayot = requireActivity().findViewById(R.id.searchLinearLayot);
                searchLinearLayot.setVisibility(View.VISIBLE);
                search = requireActivity().findViewById(R.id.searchText);
                search.setVisibility(View.VISIBLE);


                searchButton = requireActivity().findViewById(R.id.searchButton);
                searchButton.setVisibility(View.VISIBLE);
                searchButton.setOnClickListener(view -> searchPhones(search.getText().toString()));
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
                menuItemSearchOver.setVisible(false);
                menuItemSearch.setVisible(true);
                if (search != null && searchButton != null && searchLinearLayot != null) {
                    search.setVisibility(View.VISIBLE);
                    searchButton.setVisibility(View.GONE);
                    searchLinearLayot.setVisibility(View.INVISIBLE);
                    phoneAdapter.filterList(phonesArrayList);
                }
                return true;

            case R.id.addItem:

              //  Phones phones = new Phones("Telephone", "Nice phone", R.id.imagePhone, new Date());
                phonesArrayList= vm.getPhonesArrayList();
               // phonesArrayList.add(phones);
                String phoneForSaving = new GsonBuilder().create().toJson(phonesArrayList);
                sharedPreferences.edit().putString(KEY, phoneForSaving).apply();

                //vm.add(phones);
               // vm.addNewPhones(vm.getPhonesArrayList());
               // phoneAdapter.notifyItemInserted(phonesArrayList.size() - 1);

                navigation.addFragment(PhoneFragment.newInstance(), true);
                publisher.subscribe(new com.example.phones.model.Observer() {
                    @Override
                    public void updatePhoneCard(Phones phones) {
                        vm.add(phones);
                        phonesArrayList= vm.getPhonesArrayList();
                        // phonesArrayList.add(phones);
                        String phoneForSaving = new GsonBuilder().create().toJson(phonesArrayList);
                        sharedPreferences.edit().putString(KEY, phoneForSaving).apply();
                        if (phonesArrayList.size()==0){
                            phoneAdapter.notifyItemInserted(phonesArrayList.size());
                        }else {
                            phoneAdapter.notifyItemInserted(phonesArrayList.size() - 1);
                        }
                        moveToLastPosition = true;
                    }
                });

                return true;
            case R.id.clear:
                vm.getPhonesArrayList().clear();
                String phoneForClear = new GsonBuilder().create().toJson(phonesArrayList);
                vm.addNewPhones(vm.getPhonesArrayList());
                sharedPreferences.edit().putString(KEY, phoneForClear).apply();
                phoneAdapter.notifyDataSetChanged();
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
                            vm.delete(index);
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

    public void init() {
        recyclerView.getAdapter().notifyDataSetChanged();
        phone = phonesArrayList.get(0);
    }

    private void searchPhones(String phone) {
        ArrayList<Phones> newList = new ArrayList<>();
        for (int i = 0; i < phonesArrayList.size(); i++) {
            if (phonesArrayList.get(i).getName().equals(phone)) {
                System.out.println("hasMatches " + phonesArrayList.get(i).getName() + " = " + phone);
                newList.add(phonesArrayList.get(i));

            }
            phoneAdapter.filterList(newList);
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
      //  System.out.println("savedPhone "+phone.getName());
        //  outState.putString("true", "true");
       // outState.putParcelable(CURRENT_PHONE, phone);
        super.onSaveInstanceState(outState);

    }

    private boolean isLand() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
}

