package com.example.phones.ui;

import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.phones.R;
import com.example.phones.model.Phones;

public class DetailsFragment extends Fragment {

static final String ARGS = "index";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments!= null){
            System.out.println("arguments "+arguments.toString());
            Phones phones = arguments.getParcelable(ARGS);
            System.out.println("phoes data "+phones.getName());
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

    public static DetailsFragment newInstance(Phones phones){
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGS, phones);
        detailsFragment.setArguments(args);
        return detailsFragment;
    }
}