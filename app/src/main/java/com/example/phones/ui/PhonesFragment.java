package com.example.phones.ui;

import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.phones.R;
import com.example.phones.model.Phones;

public class PhonesFragment extends Fragment {

    TextView nameText;
    TextView descriptionText;
    TextView dateText;
    MainViewModel vm;

    private static final String CURRENT_PHONE = "phone";
    //private int currentPhone = 0;
    String neme, daty;
    Button aboutApp;
    Phones phone;

    Phones[] phones = new Phones[]{
            new Phones("Samsung", "Samsung is cool phone", R.drawable.samsung, "14.11.2022"),
            new Phones("Iphone", "Iphone is popular phone", R.drawable.iphone, "14.11.2022"),
            new Phones("Xiaomi", "Xiamoi  it is the second largest manufacturer of smartphones in the world, most of which run the MIUI operating system.", R.drawable.xiaomi, "14.11.2022"),
            new Phones("Nokia", "Nokia is a Finnish multinational telecommunications, information technology, and consumer electronics corporation, established in 1865", R.drawable.nokia, "14.11.2022"),
            new Phones("Honor", "Honor is is a smartphone brand majority owned by a state-owned enterprise controlled by the municipal government of Shenzhe", R.drawable.honor, "14.11.2022"),

    };

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

        View rootView = inflater.inflate(R.layout.fragment_phones, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         aboutApp = (Button)view.findViewById(R.id.buttonAboutApp);
        aboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AboutAppFragment aboutAppFragment = new AboutAppFragment();
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.dateAboutApp, aboutAppFragment)
                        .addToBackStack("")
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .commit();
            }
        });

        vm = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        //FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();

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
        init(view);
    }

    private void init(View view){
        LinearLayout layout = view.findViewById(R.id.linearLayout);
        FragmentManager fragmentManager1 = PhonesFragment.this.requireActivity().getSupportFragmentManager();

                for (int i = 0; i < phones.length; i++) {
                    nameText = new TextView(PhonesFragment.this.getContext());
                    nameText.setText(phones[i].getName());
                    nameText.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                    nameText.setTextColor(PhonesFragment.this.getResources().getColor(R.color.black));
                    descriptionText = new TextView(PhonesFragment.this.getContext());
                    descriptionText.setText(phones[i].getDescriptions());
                    descriptionText.setEllipsize(TextUtils.TruncateAt.END);
                    descriptionText.setLines(1);
                    dateText = new TextView(PhonesFragment.this.getContext());


                    dateText.setText(phones[i].getDate());
                    dateText.setTag(phones[i].getName());
                    dateText.setTextColor(PhonesFragment.this.getResources().getColor(R.color.black));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(10, 0, 0, 10);
                    dateText.setLayoutParams(params);
                    final int dateIndex = i;


                    dateText.setOnClickListener(view1 -> {


                        if (PhonesFragment.this.isLand()) {
                            DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(phones[dateIndex].getName());

                            fragmentManager1.beginTransaction()
                                    .add(R.id.fragmentDetails, datePickerFragment)
                                    .addToBackStack("")
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                    .commit();
                        } else {
                            DatePickerFragment datePickerFragment = DatePickerFragment.newInstance(phones[dateIndex].getName());
                            fragmentManager1.beginTransaction()
                                    .add(R.id.dateFragmnet, datePickerFragment)
                                    .addToBackStack("")
                                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                                    .commit();
                        }

                    });

                    layout.addView(nameText);
                    layout.addView(descriptionText);
                    layout.addView(dateText);
                    final int index = i;
                    nameText.setOnClickListener(view12 -> {
                       // currentPhone = index;

                        System.out.println("Phones id"+phones[index].getImage());
                        PhonesFragment.this.showPhones(phones[index]);
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
        outState.putParcelable(CURRENT_PHONE, phone);
        super.onSaveInstanceState(outState);

    }

    private boolean isLand() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
}

