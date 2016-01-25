package com.example.rohit.interviewapp;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rohit.interviewapp.Adapters.CustomAdapterForUsers;
import com.example.rohit.interviewapp.Fragments.UserFragment;
import com.example.rohit.interviewapp.Model.ToDoModel;
import com.example.rohit.interviewapp.Model.UserModel;
import com.example.rohit.interviewapp.NetworkOperations.FetchApiData;

import java.util.ArrayList;
import java.util.List;


import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements UserFragment.OnFragmentInteractionListener {

    FetchApiData fetchApiData = new FetchApiData();
    static List<ToDoModel> toDoModelList;
    List<ToDoModel> toDoModelListbyId;
    static List<UserModel>userModelList;

    private CustomAdapterForUsers customAdapter;
    String Tag = "MainActivity";
    Context context = null;
    private ListView listView;
    ToDoModel tempTodo = new ToDoModel();
    CustomActionBar customActionBar = new CustomActionBar();
    ImageButton addUser;
    ImageButton deleteUser;
    ImageButton editUser;
    ImageButton navButton;
    UserModel userModelToDelete = new UserModel();

    TextView barTitle = null;

    int flag = 0;
    int longPressFlag = 0;

    FragmentTransaction fragmentTransaction;
    UserFragment userFragment;



    public CustomAdapterForUsers getCustomAdapter() {
        return customAdapter;
    }

    public void setCustomAdapter(CustomAdapterForUsers customAdapter) {
        this.customAdapter = customAdapter;
    }

    public List<UserModel> getUserModelList()
    {
        return this.userModelList;
    }

    public void setUserModelList(List<UserModel> userModelList)
    {
        this.userModelList = userModelList;
    }
    public void addToUserModelList(UserModel userModel)
    {
        getUserModelList().add(userModel);

//        getCustomAdapter().notifyDataSetChanged();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        customActionBar.customActionBar(getSupportActionBar(), context);

        barTitle = (TextView) findViewById(R.id.textViewTitle);
        addUser = (ImageButton) findViewById(R.id.addButtonId);
        deleteUser = (ImageButton) findViewById(R.id.deleteButtonId);
        editUser = (ImageButton) findViewById(R.id.editButtonId);
        navButton = (ImageButton) findViewById(R.id.navButtonId);

        listView = (ListView)findViewById(R.id.fullListViewUsers);

        navButton.setVisibility(View.INVISIBLE);
        editUser.setVisibility(View.INVISIBLE);
        deleteUser.setVisibility(View.INVISIBLE);

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("button clicked"," button clicked");
                userFragment = new UserFragment();
                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.user_fragmentContainer, userFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                listView.setVisibility(View.GONE); /// hide view on clicks made on fragment don't reflect on activity
                addUser.setVisibility(View.INVISIBLE);
                editUser.setVisibility(View.INVISIBLE);
                deleteUser.setVisibility(View.INVISIBLE);
            }
        });



        new Thread(new Runnable() {

            @Override
            public void run() {
                // operations to be performed on a background thread
                toDoModelList = fetchApiData.getToDoList();
                userModelList = fetchApiData.getUserList();

                setUserModelList(userModelList);

//                ToDoModel temp = toDoModelList.get(0);
//                temp.setTitle("testing for todooooo");
//
      //          Log.i("post request", String.valueOf(fetchApiData.postToDo(temp).getTitle()));

//
//                tempTodo.setId(4);
//                tempTodo.setTitle("Create a job once more edited");
//                tempTodo.setCompleted(true);
//                tempTodo.setDueDate("2016-03-21T12:22:45.000Z");
//                tempTodo.setUserId(4);

//                for(ToDoModel obj : toDoModelList){
//                    Log.i(Tag+" todo cha id", String.valueOf(obj.getId()));
//                    Log.i(Tag+" todo cha title", String.valueOf(obj.getTitle()));
//                    Log.i(Tag+" todo cha userId", String.valueOf(obj.getUserId()));
//                    Log.i(Tag+" todo cha complted", String.valueOf(obj.getCompleted()));
//                    Log.i(Tag+" todo cha dueDate", String.valueOf(obj.getDueDate()));
//                }
                setCustomAdapter(new CustomAdapterForUsers((ArrayList<UserModel>) getUserModelList(),context));

                customAdapter = getCustomAdapter();

                runOnUiThread(new Runnable() { // update adapter from main UI thread
                    @Override
                    public void run() {
                        listView.setAdapter(customAdapter);
                        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                            @Override
                            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                                longPressFlag = 1;
                                customActionBar.setActionBarColor("#29993d");
                                editUser.setVisibility(View.VISIBLE);
                                deleteUser.setVisibility(View.VISIBLE);
                                barTitle.setText(getUserModelList().get(position).getName());


                                deleteUser.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        userModelToDelete = new UserModel();
                                        Log.i("position", String.valueOf(position));
                                        userModelToDelete = userModelList.get(position);
                                        Log.i("to delete ", userModelToDelete.getName());


                                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                switch (which){
                                                    case DialogInterface.BUTTON_POSITIVE:
                                                        //Yes button clicked
                                                        userModelList.remove(userModelToDelete);
                                                        customAdapter.notifyDataSetChanged();
                                                        new Thread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                fetchApiData.deleteUser(userModelToDelete.getId());
                                                            }
                                                        }).start();
                                                        editUser.setVisibility(View.INVISIBLE);
                                                        deleteUser.setVisibility(View.INVISIBLE);
                                                        // barTitle.setText("ToDo List");
                                                        customActionBar.setActionBarColor("#831919");
                                                        barTitle.setText("Interview app");
                                                        break;

                                                    case DialogInterface.BUTTON_NEGATIVE:
                                                        //No button clicked
                                                        break;
                                                }
                                            }
                                        };
                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                                                .setNegativeButton("No", dialogClickListener).show();

                                    }
                                });





                                return true;
                            }
                        });

                        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(AbsListView view, int scrollState) {
                                editUser.setVisibility(View.INVISIBLE);
                                deleteUser.setVisibility(View.INVISIBLE);
                               // barTitle.setText("ToDo List");
                                customActionBar.setActionBarColor("#831919");
                                barTitle.setText("Interview app");

                                longPressFlag = 0;
                            }

                            @Override
                            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                            }
                        });

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                UserModel userModel = getUserModelList().get(position);

                                Integer userId = userModel.getId();
                                String userName = userModel.getName();
                                Log.i("fetched username",userName);
                                Log.i("fetched username", String.valueOf(userId));
//                                toDoModelListbyId = new ArrayList<ToDoModel>();
                                for(ToDoModel toDoModel : toDoModelList){ // ToDoModel object matching with clicked userId extracted and added to toDoModelListbyId
                                    if(toDoModel.getUserId().equals(userId)){
//                                    if(toDoModel.getUserId() == userId){
//                                        Log.i("user id matched", toDoModel.getUserId().toString());
//                                        toDoModelListbyId.add(toDoModel);
                                    flag = 1;
                                    longPressFlag = 0;
                                    }
                                }

                                if(flag == 1) {
                                    Intent intent = new Intent(context, ToDoActivity.class);
                                    intent.putExtra("userId", userId);
                                    intent.putExtra("userName",userName);
                                    flag = 0;
                                //    intent.putParcelableArrayListExtra("toDoModelList", (ArrayList<? extends Parcelable>) toDoModelListbyId);
                                    startActivity(intent);
                                }
                                else
                                    Toast.makeText(context,"No to do list for "+userModel.getName(),Toast.LENGTH_LONG).show();
                            }
                        });
                    } // end of run(UI thread
                }); // end of run on UI thread
            }
        }).start();
    }
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
            listView.setVisibility(View.VISIBLE);
            addUser.setVisibility(View.VISIBLE);

        }
        if(longPressFlag == 1){
            editUser.setVisibility(View.INVISIBLE);
            deleteUser.setVisibility(View.INVISIBLE);
            // barTitle.setText("ToDo List");
            customActionBar.setActionBarColor("#831919");
            barTitle.setText("Interview app");

        }
        else {
            super.onBackPressed();
        }
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
