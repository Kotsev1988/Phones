package com.example.phones.ui;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.example.phones.R;
import com.example.phones.model.Phones;

import java.util.ArrayList;
import java.util.Arrays;

public class PhonesFragment extends Fragment {

    TextView nameText;
    TextView descriptionText;
    TextView dateText;
    MainViewModel vm;
    View dataConteiner;
    private ArrayList<Phones> phonesArrayList =new ArrayList<>();

    private static final String CURRENT_PHONE = "phone";

    Phones phone;
    View viewSeparate;


    EditText search;
    LinearLayout searchLinearLayot;
    Button searchButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_phones, container, false);
        return rootView;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /*aboutApp = (Button)view.findViewById(R.id.buttonAboutApp);
        aboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutAppFragment aboutAppFragment = new AboutAppFragment();
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.dateFragmnet, aboutAppFragment)
                        .addToBackStack("")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });*/

        vm = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        if (savedInstanceState != null && savedInstanceState.getString("true").matches("true")) {
            phonesArrayList = vm.getPhonesArrayList(false);
        } else {
            phonesArrayList = vm.getPhonesArrayList(true);
        }

        vm.getPhone1().observe(getViewLifecycleOwner(), new Observer<Phones>() {
            @Override
            public void onChanged(Phones phones) {
               dateText =(TextView) view.findViewWithTag(phones.getName());
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
        //use this for FragmentResultListener
        //initList(view);

        // use this init for modelView
        dataConteiner = view.findViewById(R.id.linearLayout);
        init(view);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
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
                requireActivity().finish();
                return true;

            case R.id.searchOver:
                if (search!=null && searchButton!=null && searchLinearLayot!=null) {
                    search.setVisibility(View.GONE);
                    searchButton.setVisibility(View.GONE);
                    item.setVisible(true);
                    searchLinearLayot.setVisibility(View.GONE);
                    init(dataConteiner);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initPopupMenu( TextView view, int index){
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Activity activity = requireActivity();
                PopupMenu popupMenu = new PopupMenu(activity, v);
                activity.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        if (menuItem.getItemId() == R.id.popup_delete){
                            phonesArrayList.remove(index);
                            init();
                        }
                        return true;
                    }
                });
                popupMenu.show();
                return true;
            }
        });



    }

    private void searchPhones(String phone) {
        for (int i = 0; i < phonesArrayList.size() ; i++) {
            if (phonesArrayList.get(i).getName().equals(phone)){
                System.out.println("hasMatches "+phonesArrayList.get(i).getName() + " = "+phone);

                initAfterSearch(dataConteiner,phonesArrayList.get(i));
                break;
            }
        }
    }

    public void init(){

        init(dataConteiner);
    }



    private void initAfterSearch(View view, Phones phones){
        LinearLayout layout = view.findViewById(R.id.linearLayout);
        layout.removeAllViews();
        FragmentManager fragmentManager1 = PhonesFragment.this.requireActivity().getSupportFragmentManager();

        phone = phonesArrayList.get(0);
            nameText = new TextView(PhonesFragment.this.getContext());
            nameText.setText(phones.getName());
            nameText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            nameText.setTextColor(PhonesFragment.this.getResources().getColor(R.color.black));
            descriptionText = new TextView(PhonesFragment.this.getContext());
            descriptionText.setText(phones.getDescriptions());
            descriptionText.setEllipsize(TextUtils.TruncateAt.END);
            descriptionText.setLines(1);
            dateText = new TextView(PhonesFragment.this.getContext());


            dateText.setText(phones.getDate());
            dateText.setTag(phones.getName());
            dateText.setTextColor(PhonesFragment.this.getResources().getColor(R.color.black));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 0, 0, 10);
            dateText.setLayoutParams(params);
            dateText.setOnClickListener(view1 -> {


                if (PhonesFragment.this.isLand()) {
                    DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(phones);

                    fragmentManager1.beginTransaction()
                            .replace(R.id.fragmentDetails, datePickerFragment)
                            .addToBackStack("")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                } else {
                    DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(phones);
                    fragmentManager1.beginTransaction()
                            .replace(R.id.dateFragmnet, datePickerFragment)
                            .addToBackStack("")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                }

            });

            viewSeparate = new View(PhonesFragment.this.getContext());
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 1);
            viewSeparate.setLayoutParams(param);
            viewSeparate.setBackgroundColor(getResources().getColor(android.R.color.black));
            layout.addView(nameText);
            layout.addView(descriptionText);
            layout.addView(dateText);
            layout.addView(viewSeparate);
            nameText.setOnClickListener(view12 -> {
                System.out.println("Phones id"+phones.getImage());
                PhonesFragment.this.showPhones(phones);
            });

    }
    private void init(View view){

        LinearLayout layout = view.findViewById(R.id.linearLayout);
        layout.removeAllViews();
        FragmentManager fragmentManager1 = PhonesFragment.this.requireActivity().getSupportFragmentManager();

        phone = phonesArrayList.get(0);
                for (int i = 0; i < phonesArrayList.size(); i++) {
                    nameText = new TextView(PhonesFragment.this.getContext());
                    nameText.setText(phonesArrayList.get(i).getName());
                    nameText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    nameText.setTextColor(PhonesFragment.this.getResources().getColor(R.color.black));
                    descriptionText = new TextView(PhonesFragment.this.getContext());
                    descriptionText.setText(phonesArrayList.get(i).getDescriptions());
                    descriptionText.setEllipsize(TextUtils.TruncateAt.END);
                    descriptionText.setLines(1);
                    dateText = new TextView(PhonesFragment.this.getContext());


                    dateText.setText(phonesArrayList.get(i).getDate());
                    dateText.setTag(phonesArrayList.get(i).getName());
                    dateText.setTextColor(PhonesFragment.this.getResources().getColor(R.color.black));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(10, 0, 0, 10);
                    dateText.setLayoutParams(params);
                    final int dateIndex = i;


                    dateText.setOnClickListener(view1 -> {


                        if (PhonesFragment.this.isLand()) {
                            DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(phonesArrayList.get(dateIndex));

                            fragmentManager1.beginTransaction()
                                    .replace(R.id.fragmentDetails, datePickerFragment)
                                    .addToBackStack("")
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                    .commit();
                        } else {
                            DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(phonesArrayList.get(dateIndex));
                            fragmentManager1.beginTransaction()
                                    .replace(R.id.dateFragmnet, datePickerFragment)
                                    .addToBackStack("")
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                    .commit();
                        }

                    });

                    viewSeparate = new View(PhonesFragment.this.getContext());
                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 1);
                    viewSeparate.setLayoutParams(param);
                    viewSeparate.setBackgroundColor(getResources().getColor(android.R.color.black));
                    layout.addView(nameText);
                    layout.addView(descriptionText);
                    layout.addView(dateText);
                    layout.addView(viewSeparate);
                    final int index = i;
                    initPopupMenu(nameText, index);
                    nameText.setOnClickListener(view12 -> {
                       // currentPhone = index;

                        phone = phonesArrayList.get(index);
                        System.out.println("Phones id"+phonesArrayList.get(index).getImage());
                        PhonesFragment.this.showPhones(phonesArrayList.get(index));
                    });
                }
    }

  /*  private void initList(View view) {
        LinearLayout layout = (LinearLayout) view;


     FragmentResultListener fragmentResultListener= new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {

                neme = result.getString("bundleKeyPhone");
                daty = result.getString("bundleKey");
                System.out.println("bundles " + result);
                for (int i = 0; i < phones.length; i++) {
                    if (phones[i].getName().matches(neme)){
                        dateText = (TextView)view.findViewWithTag(i);
                        dateText.setText(daty);
                    }
                }
            }
        };

        for (int i = 0; i < phones.length; i++) {

            nameText = new TextView(getContext());
            nameText.setText(phones[i].getName());
            nameText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            nameText.setTextColor(getResources().getColor(R.color.black));
            descriptionText = new TextView(getContext());
            descriptionText.setText(phones[i].getDescriptions());
            descriptionText.setEllipsize(TextUtils.TruncateAt.END);
            descriptionText.setLines(1);
            dateText = new TextView(getContext());
            dateText.setTag(i);
            dateText.setText(phones[i].getDate());
            dateText.setTextColor(getResources().getColor(R.color.black));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(10, 0, 0, 10);
            dateText.setLayoutParams(params);
            final int dateIndex = i;

            dateText.setOnClickListener(view1 -> {

                requireActivity().getSupportFragmentManager().setFragmentResultListener("key", this , fragmentResultListener);
                if (PhonesFragment.this.isLand()) {
                    DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(phones[dateIndex].getName());
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .add(R.id.fragmentDetails, datePickerFragment)
                            .addToBackStack("")
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                            .commit();
                } else {
                    DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(phones[dateIndex].getName());
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.dateFragmnet, datePickerFragment)
                            .addToBackStack("")
                            .commit();
                }


            });
            layout.addView(nameText);
            layout.addView(descriptionText);
            layout.addView(dateText);
            final int index = i;
            nameText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    currentPhone = index;
                    showPhones(index);
                }
            });

        }
    }*/



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

