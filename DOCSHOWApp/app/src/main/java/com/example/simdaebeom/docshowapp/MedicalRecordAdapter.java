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
        TextView recordNumber =(TextView) v.findViewById(R.id.recordNumber);
        TextView doctorIDinRecord =(TextView) v.findViewById(R.id.doctorIDinRecord);
        TextView specialNote =(TextView) v.findViewById(R.id.specialNote);
        TextView disease =(TextView) v.findViewById(R.id.disease);
        TextView prescription =(TextView) v.findViewById(R.id.prescription);
        TextView smoking =(TextView) v.findViewById(R.id.smoking);
        TextView patientID =(TextView) v.findViewById(R.id.patientID);
        TextView dateOfExamination =(TextView) v.findViewById(R.id.dateOfExamination);

        recordNumber.setText(medicalRecords.get(position).getRecordNumber());
        doctorIDinRecord.setText(medicalRecords.get(position).getDoctorID());
        specialNote.setText(medicalRecords.get(position).getSpecialNote());
        disease.setText(medicalRecords.get(position).getDisease());
        prescription.setText(medicalRecords.get(position).getPrescription());
        smoking.setText(medicalRecords.get(position).getSmoking());
        patientID.setText(medicalRecords.get(position).getPatientID());
        dateOfExamination.setText(medicalRecords.get(position).getDateOfExamination());



        v.setTag(medicalRecords.get(position).getRecordNumber());
        return v;


    }
}
