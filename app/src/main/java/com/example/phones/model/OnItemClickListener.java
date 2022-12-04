package com.example.phones.model;

import android.view.View;

public interface OnItemClickListener {
    void onItemClick(View view, int position);
    void onEditIconClick(View v, int pos);
    void onLingClick(View v, int pos);
}
