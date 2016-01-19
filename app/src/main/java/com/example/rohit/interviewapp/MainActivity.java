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
import android.widget.Toast;

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
    List<ToDoModel> toDoModelListbyId;
    List<UserModel>userModelList;
    CustomAdapterForUsers customAdapter;
    String Tag = "MainActivity";
    Context context = null;
    ListView listView;
    ToDoModel tempTodo = new ToDoModel();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;


        new Thread(new Runnable() {

            @Override
            public void run() {
                // operations to be performed on a background thread
                toDoModelList = fetchApiData.getToDoList();
                userModelList = fetchApiData.getUserList();

                ToDoModel temp = toDoModelList.get(0);
                temp.setTitle("testing for todooooo");
               // temp.setId();
                Log.i("post request", String.valueOf(fetchApiData.postToDo(temp).getTitle()));


                tempTodo.setId(4);
                tempTodo.setTitle("Create a job once more edited");
                tempTodo.setCompleted(true);
                tempTodo.setDueDate("2016-03-21T12:22:45.000Z");
                tempTodo.setUserId(4);

//                fetchApiData.putToDo(tempTodo, 1).getTitle();
          //      Log.i("put request", String.valueOf(fetchApiData.putToDo(tempTodo, 4).getTitle())); // put works perfectly
         //       Log.i("post request", String.valueOf(fetchApiData.postToDo(tempTodo).getTitle())); // post works perfectly

                fetchApiData.deleteToDo(4).getTitle(); // delete works perfectly

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
                                toDoModelListbyId = new ArrayList<ToDoModel>();
                                for(ToDoModel toDoModel : toDoModelList){ // ToDoModel object matching with clicked userId extracted and added to toDoModelListbyId
                                    if(toDoModel.getUserId().equals(userId)){
                                        Log.i("user id matched", toDoModel.getUserId().toString());
                                        toDoModelListbyId.add(toDoModel);

                                    }
                                }
                               // toDoModelListbyId = fetchApiData.getTodoById(1); // call toDoList based on user clicked // not used to avoid making another thread to fetch data from api


                                if(!toDoModelListbyId.isEmpty()) {
                                    Intent intent = new Intent(context, ToDoActivity.class);
                                    intent.putExtra("userId", userId);
                                    // intent.putExtra("toDoModelList", (Serializable) toDoModelList);
                                    //  intent.putParcelableArrayListExtra("toDoModelList", (ArrayList<? extends Parcelable>) toDoModelList);
                                    intent.putParcelableArrayListExtra("toDoModelList", (ArrayList<? extends Parcelable>) toDoModelListbyId);
                                    startActivity(intent);
                                }
                                else
                                    Toast.makeText(context,"No to do list for "+userModel.getName(),Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });

            }

        }).start();
//        toDoModelList = fetchApiData.getToDoList();
//        userModelList = fetchApiData.getUserList();

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
