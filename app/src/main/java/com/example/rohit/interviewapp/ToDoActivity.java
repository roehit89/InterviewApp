package com.example.rohit.interviewapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

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
    CustomActionBar customActionBar = new CustomActionBar();
    ImageButton addUser;
    ImageButton deleteUser;
    ImageButton editUser;
    DialogActions dialogActions;
    Context context;
    String userName;
    TextView barTitle = null;
    ImageButton navButton;
    public android.support.v7.app.ActionBar actionBar;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todoactivity);
        context = this;
        customActionBar.customActionBar(getSupportActionBar(), this);

        addUser = (ImageButton) findViewById(R.id.addButtonId);
        deleteUser = (ImageButton) findViewById(R.id.deleteButtonId);
        editUser = (ImageButton) findViewById(R.id.editButtonId);
        navButton = (ImageButton) findViewById(R.id.navButtonId);

        navButton.setVisibility(View.VISIBLE);

        Resources resources = this.getResources();

        addUser.setImageDrawable(resources.getDrawable(R.drawable.ic_plus_circle_outline));

        //addUser.setImageDrawable(Drawable.);
        addUser.setVisibility(View.VISIBLE);
        deleteUser.setVisibility(View.INVISIBLE);
        editUser.setVisibility(View.INVISIBLE);

        userId = getIntent().getIntExtra("userId",-1);
        Log.i("alela userid", String.valueOf(userId));
       //toDoModel = getIntent().getBundleExtra("toDoModelList");
        //toDoModelList = getIntent().getParcelableExtra("toDoModelList");
        toDoModelList = getIntent().getParcelableArrayListExtra("toDoModelList");
        userName = getIntent().getExtras().getString("userName");

        String splitString[] = userName.split("\\s+");
        Log.i("fetched username in todo",userName);
        //actionBar.setTitle(userName);
        //actionBar.setTitle("hello");
        barTitle = (TextView) findViewById(R.id.textViewTitle);
        barTitle.setText(splitString[0]+"'s to do list");
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

        dialogActions = new DialogActions(context);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogActions.showDialog(context);
            }
        });


    }
}
