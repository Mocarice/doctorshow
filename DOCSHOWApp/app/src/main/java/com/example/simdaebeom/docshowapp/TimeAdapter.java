package com.example.simdaebeom.docshowapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class TimeAdapter extends ArrayAdapter<String>{

    TimeAdapter(Context context, ArrayList<String> items){
        super(context,R.layout.time,items);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater timeInflater =  LayoutInflater.from(getContext());
        View view = timeInflater.inflate(R.layout.time,parent,false);
        String item = getItem(position);
        TextView textView = (TextView) view.findViewById(R.id.time);
        textView.setText(item);
        return view;

    }
}
