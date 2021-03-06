package com.example.simdaebeom.docshowapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class UserListAdapter extends BaseAdapter {
    private Context context;
    private List<User> userList;
    private List<User> saveList;

    public UserListAdapter(Context context, List<User> userList,List<User> saveList) {
        this.context = context;
        this.userList = userList;
        this.saveList = saveList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View v = View.inflate(context,R.layout.user,null);
        TextView userID =(TextView) v.findViewById(R.id.userID);
        TextView userPassword =(TextView) v.findViewById(R.id.userPassword);
        TextView userName =(TextView) v.findViewById(R.id.userName);
        TextView userBirth =(TextView) v.findViewById(R.id.userBirth);

        userID.setText(userList.get(position).getUserID());
        userPassword.setText(userList.get(position).getUserPassword());
        userName.setText(userList.get(position).getUserName());
        userBirth.setText(userList.get(position).getUserBirth());

        v.setTag(userList.get(position).getUserID());
        return v;


    }
}
