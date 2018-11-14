package com.example.simdaebeom.docshowapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ReservationListAdapter extends BaseAdapter {
    private Context context;
    private List<Reservation> reservationList;
    private List<Reservation> saveList;

    public ReservationListAdapter(Context context, List<Reservation> reservationList,List<Reservation> saveList) {
        this.context = context;
        this.reservationList = reservationList;
        this.saveList = saveList;
    }

    @Override
    public int getCount() {
        return reservationList.size();
    }

    @Override
    public Object getItem(int position) {
        return reservationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context,R.layout.reservation,null);

        TextView reservationNumber =(TextView) v.findViewById(R.id.reservationNumber);
        TextView doctorID =(TextView) v.findViewById(R.id.doctorID);
        TextView userID =(TextView) v.findViewById(R.id.userID);
        TextView date =(TextView) v.findViewById(R.id.date);
        TextView time =(TextView) v.findViewById(R.id.time);

        reservationNumber.setText(reservationList.get(position).getReservationNumber());
        doctorID.setText(reservationList.get(position).getDoctorID());
        userID.setText(reservationList.get(position).getUserID());
        date.setText(reservationList.get(position).getDate());
        time.setText(reservationList.get(position).getTime());


        v.setTag(reservationList.get(position).reservationNumber);
        return v;


    }
}
