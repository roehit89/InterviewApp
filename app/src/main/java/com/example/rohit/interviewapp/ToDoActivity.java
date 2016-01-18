package com.example.rohit.interviewapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.example.rohit.interviewapp.Adapters.CustomAdapterForToDo;
import com.example.rohit.interviewapp.Model.ToDoModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * Created by Rohit on 1/17/2016.
 */
public class ToDoActivity extends AppCompatActivity{

    Integer userId;
    ArrayList<ToDoModel> toDoModelList;
    CustomAdapterForToDo customAdapter;
    ListView listView;
    String Tag = "ToDoActivity";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todoactivity);

        userId = getIntent().getIntExtra("userId",-1);
        Log.i("alela userid", String.valueOf(userId));
       //toDoModel = getIntent().getBundleExtra("toDoModelList");
        //toDoModelList = getIntent().getParcelableExtra("toDoModelList");
        toDoModelList = getIntent().getParcelableArrayListExtra("toDoModelList");

        for(ToDoModel toDoModel: toDoModelList){

            Log.i(Tag+" todo cha id", String.valueOf(toDoModel.getId()));
            Log.i(Tag+" todo cha title", String.valueOf(toDoModel.getTitle()));
            Log.i(Tag+" todo cha userId", String.valueOf(toDoModel.getUserId()));
            Log.i(Tag+" todo cha complted", String.valueOf(toDoModel.getCompleted()));
            Log.i(Tag+" todo cha dueDate", String.valueOf(toDoModel.getDueDate()));
        }
//
        customAdapter = new CustomAdapterForToDo(toDoModelList,this);
        listView = (ListView) findViewById(R.id.fullListViewToDo);
        listView.setAdapter(customAdapter);


    }
}
