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

        viewHolder.user_name.setText(userModelArrayList.get(position).getName());
        viewHolder.user_userName.setText(userModelArrayList.get(position).getUserName());
        viewHolder.user_phone.setText(userModelArrayList.get(position).getPhone());
        viewHolder.user_email.setText(userModelArrayList.get(position).getEmail());
        viewHolder.user_company_name.setText(userModelArrayList.get(position).getCompany().getCompanyName());
        viewHolder.user_street.setText(userModelArrayList.get(position).getAddress().getStreet());
        viewHolder.user_suit.setText(userModelArrayList.get(position).getAddress().getSuite());
        viewHolder.user_zipcode.setText(userModelArrayList.get(position).getAddress().getZipCode());


        return convertView;
    }

public class ViewHolder{

    @Bind(R.id.user_name)TextView user_name;
    @Bind(R.id.user_userName)TextView user_userName;
    @Bind(R.id.user_phone)TextView user_phone;
    @Bind(R.id.user_email)TextView user_email;
    @Bind(R.id.user_company_name)TextView user_company_name;
    @Bind(R.id.user_street)TextView user_street;
    @Bind(R.id.user_suit)TextView user_suit;
    @Bind(R.id.user_zipcode)TextView user_zipcode;





    ViewHolder(View view){
        ButterKnife.bind(this,view);
    }
}

}
