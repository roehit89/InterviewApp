package com.example.rohit.interviewapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rohit.interviewapp.Model.UserModel;
import com.example.rohit.interviewapp.R;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rohit on 1/17/2016.
 */
public class CustomAdapterForUsers extends BaseAdapter {

    Context context;
    ArrayList<UserModel> userModelArrayList;


    public CustomAdapterForUsers(ArrayList<UserModel> userModelList, Context context){
        this.userModelArrayList = userModelList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return userModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return userModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.singlelistforusers, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.userName.setText(userModelArrayList.get(position).getName());
        viewHolder.userEmail.setText(userModelArrayList.get(position).getEmail());
        return convertView;
    }

public class ViewHolder{

    @Bind(R.id.userName)TextView userName;
    @Bind(R.id.userEmail)TextView userEmail;

    ViewHolder(View view){
        ButterKnife.bind(this,view);
    }
}

}
