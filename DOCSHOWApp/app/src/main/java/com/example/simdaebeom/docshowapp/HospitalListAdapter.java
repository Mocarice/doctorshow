package com.example.simdaebeom.docshowapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class HospitalListAdapter extends BaseAdapter {
    private Context context;
    private List<Hospital> hospitalList;
    private List<Hospital> saveList;

    public HospitalListAdapter(Context context, List<Hospital> hospitalList,List<Hospital> saveList) {
        this.context = context;
        this.hospitalList = hospitalList;
        this.saveList = saveList;
    }

    @Override
    public int getCount() {
        return hospitalList.size();
    }

    @Override
    public Object getItem(int position) {
        return hospitalList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View v = View.inflate(context,R.layout.hospital,null);
        TextView hospitalID =(TextView) v.findViewById(R.id.hospitalID);
        TextView hospitalName =(TextView) v.findViewById(R.id.hospitalName);
        TextView hospitalAddress =(TextView) v.findViewById(R.id.hospitalAddress);
        TextView Telephone =(TextView) v.findViewById(R.id.Telephone);

        hospitalID.setText(hospitalList.get(position).getHospitalID());
        hospitalAddress.setText(hospitalList.get(position).getHospitalAddress());
        hospitalName.setText(hospitalList.get(position).getHospitalName());
        Telephone.setText(hospitalList.get(position).getTelephone());


        v.setTag(hospitalList.get(position).getHospitalName());
        return v;


    }
}
