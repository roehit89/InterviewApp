package com.example.rohit.interviewapp;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rohit.interviewapp.Adapters.CustomAdapterForToDo;
import com.example.rohit.interviewapp.Fragments.ToDoFragment;
import com.example.rohit.interviewapp.Model.ToDoModel;
import com.example.rohit.interviewapp.NetworkOperations.FetchApiData;

import java.util.ArrayList;

/**
 * Created by Rohit on 1/17/2016.
 */
public class ToDoActivity extends AppCompatActivity implements ToDoFragment.OnFragmentInteractionListener{

    Integer userId;
    private static ArrayList<ToDoModel> toDoModelList;
    private static CustomAdapterForToDo customAdapter;
    private static ArrayList<ToDoModel> toDoModelListbyId = new ArrayList<>();
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
    FragmentTransaction fragmentTransaction;
    ToDoFragment toDoFragment;

    FetchApiData fetchApiData = new FetchApiData();


    public CustomAdapterForToDo getCustomAdapter() {
        return this.customAdapter;
    }

    public void setCustomAdapter(CustomAdapterForToDo customAdapter) {
        this.customAdapter = customAdapter;
    }

    public ArrayList<ToDoModel> getToDoModelList() {
        return this.toDoModelListbyId;
    }

    public void setToDoModelList(ArrayList<ToDoModel> toDoModelListbyId) {
        this.toDoModelListbyId = toDoModelListbyId;
    }

    public void addTotoDoModelList(ToDoModel toDoModel)
    {
        toDoModelListbyId = getToDoModelList();
        toDoModelListbyId.add(toDoModel);
        customAdapter = getCustomAdapter();
        customAdapter.notifyDataSetChanged();
    }

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


        addUser.setVisibility(View.VISIBLE);
        deleteUser.setVisibility(View.INVISIBLE);
        editUser.setVisibility(View.INVISIBLE);


        userId = getIntent().getIntExtra("userId",-1);
        Log.i("alela userid", String.valueOf(userId));

        userName = getIntent().getExtras().getString("userName");

        String splitString[] = userName.split("\\s+");
        Log.i("fetched username in todo",userName);

        barTitle = (TextView) findViewById(R.id.textViewTitle);
        barTitle.setText(splitString[0] + "'s to do list");

        new Thread(new Runnable() {
            @Override
            public void run() {
                toDoModelList = (ArrayList<ToDoModel>) fetchApiData.getToDoList();

                for(ToDoModel toDoModel : toDoModelList){ // ToDoModel object matching with clicked userId extracted and added to toDoModelListbyId
                    if(toDoModel.getUserId() == userId){
                        Log.i("user id matched", toDoModel.getUserId().toString());
                        toDoModelListbyId.add(toDoModel);
                    }
                }
                setCustomAdapter(new CustomAdapterForToDo(toDoModelListbyId, context));
                customAdapter = getCustomAdapter();
                customAdapter.notifyDataSetChanged();


        setToDoModelList(toDoModelListbyId);

        listView = (ListView) findViewById(R.id.fullListViewToDo);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listView.setAdapter(customAdapter);
            }
        });}
        }).start();


        dialogActions = new DialogActions(context);
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                toDoFragment = new ToDoFragment();
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.todo_fragmentContainer, toDoFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                addUser.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);

            }
        });

    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
            listView.setVisibility(View.VISIBLE);
            addUser.setVisibility(View.VISIBLE);

        } else {
            super.onBackPressed();
        }
    }
    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
