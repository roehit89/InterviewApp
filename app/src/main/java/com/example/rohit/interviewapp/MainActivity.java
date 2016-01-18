package com.example.rohit.interviewapp;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.rohit.interviewapp.Adapters.CustomAdapterForToDo;
import com.example.rohit.interviewapp.Adapters.CustomAdapterForUsers;
import com.example.rohit.interviewapp.Model.ToDoModel;
import com.example.rohit.interviewapp.Model.UserModel;
import com.example.rohit.interviewapp.NetworkOperations.FetchApiData;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    FetchApiData fetchApiData = new FetchApiData();
    List<ToDoModel> toDoModelList;
    List<UserModel>userModelList;
    CustomAdapterForUsers customAdapter;
    String Tag = "MainActivity";
    Context context = null;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
    //    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); // change this to work on a separate thread.
    //    StrictMode.setThreadPolicy(policy);

        new Thread(new Runnable() {

            @Override
            public void run() {
                // operations to be performed on a background thread
                toDoModelList = fetchApiData.getToDoList();
                userModelList = fetchApiData.getUserList();

                for(ToDoModel obj : toDoModelList){
                    Log.i(Tag+" todo cha id", String.valueOf(obj.getId()));
                    Log.i(Tag+" todo cha title", String.valueOf(obj.getTitle()));
                    Log.i(Tag+" todo cha userId", String.valueOf(obj.getUserId()));
                    Log.i(Tag+" todo cha complted", String.valueOf(obj.getCompleted()));
                    Log.i(Tag+" todo cha dueDate", String.valueOf(obj.getDueDate()));
                }

                customAdapter = new CustomAdapterForUsers((ArrayList<UserModel>) userModelList,context);
                listView = (ListView)findViewById(R.id.fullListViewUsers);
                runOnUiThread(new Runnable() { // update adapter from main UI thread
                    @Override
                    public void run() {
                        listView.setAdapter(customAdapter);
                        //@Bind(R.id.fullListView);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                UserModel userModel = userModelList.get(position);
                                Integer userId = userModel.getId();


                                Intent intent = new Intent(context,ToDoActivity.class);
                                intent.putExtra("userId",userId);
                               // intent.putExtra("toDoModelList", (Serializable) toDoModelList);
                                intent.putParcelableArrayListExtra("toDoModelList", (ArrayList<? extends Parcelable>) toDoModelList);
                                startActivity(intent);

                            }
                        });
                    }
                });

            }

        }).start();
//        toDoModelList = fetchApiData.getToDoList();
//        userModelList = fetchApiData.getUserList();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
