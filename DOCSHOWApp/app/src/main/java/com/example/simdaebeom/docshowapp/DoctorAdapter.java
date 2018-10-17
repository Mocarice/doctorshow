package com.example.simdaebeom.docshowapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class DoctorAdapter extends BaseAdapter {
    private Context context;
    private List<Doctor> doctorList;
    private List<Doctor> saveList;


    public DoctorAdapter(Context context, List<Doctor> doctorList,List<Doctor> saveList) {
        this.context = context;
        this.doctorList = doctorList;
        this.saveList = saveList;
    }

    @Override
    public int getCount() {
        return doctorList.size();
    }

    @Override
    public Object getItem(int position) {
        return doctorList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context,R.layout.doctor,null);
        TextView doctorID =(TextView) v.findViewById(R.id.doctorID);
        TextView doctorName =(TextView) v.findViewById(R.id.doctorName);
        TextView hospitalID =(TextView) v.findViewById(R.id.hospitalID);
        TextView department =(TextView) v.findViewById(R.id.department);
        TextView telephone =(TextView) v.findViewById(R.id.telephone);


        doctorName.setText(doctorList.get(position).getDoctorName());
        department.setText(doctorList.get(position).getDepartment());
        telephone.setText(doctorList.get(position).getTelephone());


        v.setTag(doctorList.get(position).getDoctorID());
        return v;


    }
}
