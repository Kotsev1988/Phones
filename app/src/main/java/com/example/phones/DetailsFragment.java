package com.example.phones;

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
            int index = arguments.getInt(ARGS);
            ImageView imageView = view.findViewById(R.id.imagePhone);
            TextView textView = view.findViewById(R.id.detailsOfPhone);
            TypedArray typedArray = getResources().obtainTypedArray(R.array.phoneImages);
            imageView.setImageResource(typedArray.getResourceId(index, 0));
            TypedArray typedArray1 = getResources().obtainTypedArray(R.array.descriptions);
            textView.setText(typedArray1.getText(index));
            typedArray.recycle();
            typedArray1.recycle();
        }

    }

    public static DetailsFragment newInstance(int index){
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS, index);
        detailsFragment.setArguments(args);
        return detailsFragment;
    }
}