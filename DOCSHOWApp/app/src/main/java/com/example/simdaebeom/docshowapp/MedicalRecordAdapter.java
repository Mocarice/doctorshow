package com.example.simdaebeom.docshowapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MedicalRecordAdapter extends BaseAdapter {
    private Context context;
    private List<MedicalRecord> medicalRecords;
    private List<MedicalRecord> saveList;

    public MedicalRecordAdapter(Context context, List<MedicalRecord> hospitalList,List<MedicalRecord> saveList) {
        this.context = context;
        this.medicalRecords = hospitalList;
        this.saveList = saveList;
    }

    @Override
    public int getCount() {
        return medicalRecords.size();
    }

    @Override
    public Object getItem(int position) {
        return medicalRecords.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context,R.layout.medicalrecord,null);
        TextView medicalRecordID =(TextView) v.findViewById(R.id.medicalRecordID);
        TextView disease =(TextView) v.findViewById(R.id.disease);
        TextView medicalContents =(TextView) v.findViewById(R.id.medicalContents);
        TextView patientID =(TextView) v.findViewById(R.id.patientID);
        TextView doctorID =(TextView) v.findViewById(R.id.doctorID);
        TextView date =(TextView) v.findViewById(R.id.date);


        medicalRecordID.setText(medicalRecords.get(position).getMedicalRecordID());
        disease.setText(medicalRecords.get(position).getDisease());
        medicalContents.setText(medicalRecords.get(position).getMedicalContents());
        patientID.setText(medicalRecords.get(position).getPatientID());
        doctorID.setText(medicalRecords.get(position).getDoctorID());
        patientID.setText(medicalRecords.get(position).getPatientID());
        date.setText(medicalRecords.get(position).getDate());



        v.setTag(medicalRecords.get(position).getMedicalRecordID());
        return v;


    }
}
