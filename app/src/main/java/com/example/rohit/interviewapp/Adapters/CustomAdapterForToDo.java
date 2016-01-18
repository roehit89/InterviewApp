package com.example.rohit.interviewapp.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rohit.interviewapp.Model.ToDoModel;
import com.example.rohit.interviewapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Rohit on 1/18/2016.
 */
public class CustomAdapterForToDo extends BaseAdapter {

    List<ToDoModel> toDoModelList;
    Context context;
    public CustomAdapterForToDo(List<ToDoModel> toDoModelList,Context context)
    {
        this.toDoModelList = toDoModelList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return toDoModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return toDoModelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.singlelistfortodos, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.toDoTitle.setText(toDoModelList.get(position).getTitle());
        viewHolder.toDoId.setText(toDoModelList.get(position).getId().toString());
        Log.i("Adapter madhe userId", String.valueOf(toDoModelList.get(position).getUserId()));
        Log.i("Adapter madhe due date", String.valueOf(toDoModelList.get(position).getDueDate()));
        Log.i("Adapter madhe contents", String.valueOf(toDoModelList.get(position).describeContents()));
        Log.i("Adapter madhe id", String.valueOf(toDoModelList.get(position).getId()));
        return convertView;

      //  return null;
    }

    public class ViewHolder{

        @Bind(R.id.toDoId)TextView toDoId;
        @Bind(R.id.toDoTitle)TextView toDoTitle;

        ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }

}
