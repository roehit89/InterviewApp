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

    List<ToDoModel> toDoModelListbyId;
    Context context;

    public CustomAdapterForToDo(List<ToDoModel> toDoModelListbyId,Context context)
    {
        this.toDoModelListbyId = toDoModelListbyId;
        this.context = context;
    }

    @Override
    public int getCount() {
        return toDoModelListbyId.size();
    }

    @Override
    public Object getItem(int position) {
        return toDoModelListbyId.get(position);
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

        viewHolder.toDoTitle.setText(toDoModelListbyId.get(position).getTitle());
        viewHolder.toDoDueDate.setText(toDoModelListbyId.get(position).getDueDate().toString());
        Log.i("Adapter userId", String.valueOf(toDoModelListbyId.get(position).getUserId()));
        Log.i("Adapter due date", String.valueOf(toDoModelListbyId.get(position).getDueDate()));
        Log.i("Adapter contents", String.valueOf(toDoModelListbyId.get(position).describeContents()));
        Log.i("Adapter id", String.valueOf(toDoModelListbyId.get(position).getId()));
        return convertView;
    }

    public class ViewHolder{

        @Bind(R.id.toDoDueDate)TextView toDoDueDate;
        @Bind(R.id.toDoTitle)TextView toDoTitle;

        ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }

}
