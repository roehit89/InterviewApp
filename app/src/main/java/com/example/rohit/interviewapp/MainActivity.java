package com.example.rohit.interviewapp;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
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


import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements UserFragment.OnFragmentInteractionListener {

    FetchApiData fetchApiData = new FetchApiData();
    static List<ToDoModel> toDoModelList;
    List<ToDoModel> toDoModelListbyId;
    static List<UserModel>userModelList;

    private static CustomAdapterForUsers customAdapter;
    String Tag = "MainActivity";
    Context context = null;
    private ListView listView;
    ToDoModel tempTodo = new ToDoModel();
    CustomActionBar customActionBar = new CustomActionBar();
    static ImageButton addUser;
    ImageButton deleteUser;
    ImageButton editUser;
    ImageButton backButton;
    UserModel userModelToDelete = new UserModel();

   // @Bind(R.id.textViewTitle) TextView barTitle;
    TextView barTitle = null;
    Toolbar toolbar = null;

    int flag = 0;
    int longPressFlag = 0;
    int backPressCnt = 0;

    FragmentTransaction fragmentTransaction;
    UserFragment userFragment;

    public void updateUserModelList(UserModel userModel, Integer id){
     userModelList = getUserModelList();
     UserModel updateModel = userModel;

     //   this.addUser.setVisibility(View.VISIBLE);

        for(UserModel obj  : userModelList){
            if(obj.getId() == id){
                userModelList.remove(obj);
                userModelList.add(updateModel);
                setUserModelList(userModelList);
                customAdapter = getCustomAdapter();
                customAdapter.notifyDataSetChanged();
                break;
            }
        }
    }

    public CustomAdapterForUsers getCustomAdapter() {
        return this.customAdapter;
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
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        customActionBar.customActionBar(getSupportActionBar(), context);

        backPressCnt = 1;
        Log.i("back count", String.valueOf(backPressCnt));
       // @Bind(R.id.textViewTitle) barTitle;
        barTitle = (TextView) findViewById(R.id.textViewTitle);
      //  addUser = (ImageButton) findViewById(R.id.addButtonId);
        addUser = (ImageButton) findViewById(R.id.floating_add_button);
        deleteUser = (ImageButton) findViewById(R.id.deleteButtonId);
        editUser = (ImageButton) findViewById(R.id.editButtonId);
    //    backButton = (ImageButton) findViewById(R.id.backButtonId);

        listView = (ListView)findViewById(R.id.fullListViewUsers);

//        backButton.setVisibility(View.INVISIBLE);
        editUser.setVisibility(View.INVISIBLE);
        deleteUser.setVisibility(View.INVISIBLE);


        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer max = 0;
                backPressCnt = 0;
                Log.i("back count", String.valueOf(backPressCnt));
                for(UserModel userModel : userModelList)
                {
                    if(userModel.getId()>max) max = userModel.getId();
                }
                Log.i("button clicked", " button clicked");
                Bundle bundle = new Bundle();
                bundle.putString("userModelListLength", String.valueOf(max + 1));

                userFragment = new UserFragment();
                userFragment.setArguments(bundle);

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

                if(toDoModelList == null)
                while (toDoModelList!= null)
                {
                    Log.i("fetching to do list"," found null");
                    toDoModelList = fetchApiData.getToDoList();
                }

                if(userModelList == null)
                    while (userModelList!= null)
                    {
                        Log.i("fetching user list"," found null");
                        userModelList = fetchApiData.getUserList();
                    }

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
//                    Log.i(Tag+" todo  id", String.valueOf(obj.getId()));
//                    Log.i(Tag+" todo  title", String.valueOf(obj.getTitle()));
//                    Log.i(Tag+" todo  userId", String.valueOf(obj.getUserId()));
//                    Log.i(Tag+" todo  complted", String.valueOf(obj.getCompleted()));
//                    Log.i(Tag+" todo  dueDate", String.valueOf(obj.getDueDate()));
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
                                backPressCnt = 0;
                                Log.i("back count", String.valueOf(backPressCnt));
                            //    customActionBar.setActionBarColor("#29993d");
                                editUser.setVisibility(View.VISIBLE);
                                deleteUser.setVisibility(View.VISIBLE);
                                barTitle.setText(getUserModelList().get(position).getName());

                                userModelToDelete = new UserModel();
                                Log.i("position", String.valueOf(position));
                                userModelToDelete = userModelList.get(position); // fetch object to be deleted or edited
                                Log.i("to delete ", userModelToDelete.getName() + " object id" + userModelToDelete.getId());


                                editUser.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        userFragment = new UserFragment();
                                        Bundle bundle = new Bundle();
                                        bundle.putSerializable("object to delete", userModelToDelete);

                                        userFragment.setArguments(bundle);

                                        fragmentTransaction = getFragmentManager().beginTransaction();
                                        fragmentTransaction.add(R.id.user_fragmentContainer, userFragment);
                                        fragmentTransaction.addToBackStack(null);
                                        fragmentTransaction.commit();

                                        listView.setVisibility(View.GONE); /// hide view on clicks made on fragment don't reflect on activity
                                     //   addUser.setVisibility(View.INVISIBLE);
                                        editUser.setVisibility(View.INVISIBLE);
                                        deleteUser.setVisibility(View.INVISIBLE);
                                    }
                                });

                                deleteUser.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                switch (which){
                                                    case DialogInterface.BUTTON_POSITIVE:
                                                        //Yes button clicked
                                                        new Thread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                fetchApiData.deleteUser(userModelToDelete.getId());

                                                                runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        userModelList.remove(userModelToDelete);
                                                                        customAdapter.notifyDataSetChanged();
                                                                  //      addUser.setVisibility(View.VISIBLE);

                                                                    }
                                                                });
                                                            }
                                                        }).start();


                                                        editUser.setVisibility(View.INVISIBLE);
                                                        deleteUser.setVisibility(View.INVISIBLE);

                                                    //    customActionBar.setActionBarColor("#831919");
                                                        barTitle.setText("Interview app");
                                                        Toast.makeText(context,"User deleted",Toast.LENGTH_SHORT).show();
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
                              //  customActionBar.setActionBarColor("#831919");
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
                                Log.i("fetched id", String.valueOf(userId));

                                for(ToDoModel toDoModel : toDoModelList){ // ToDoModel object matching with clicked userId extracted and added to toDoModelListbyId
                                    if(toDoModel.getUserId().equals(userId)){
//                                    if(toDoModel.getUserId() == userId){
//                                        Log.i("user id matched", toDoModel.getUserId().toString());
//                                        toDoModelListbyId.add(toDoModel);
                                    flag = 1;
                                    longPressFlag = 0;
                                    }
                                }

                                    Intent intent = new Intent(context, ToDoActivity.class);
                                    intent.putExtra("userId", userId);
                                    intent.putExtra("userName",userName);
                                    flag = 0;
                                    startActivity(intent);
                            }
                        });
                    } // end of run(UI thread
                }); // end of run on UI thread
            }
        }).start();
    }
    @Override
    public void onBackPressed() {
        backPressCnt++;
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
            listView.setVisibility(View.VISIBLE);
            barTitle.setText("Interview app");
            addUser.setVisibility(View.VISIBLE);
            Log.i("in 1st if","");
        }
        if(longPressFlag == 1){
            editUser.setVisibility(View.INVISIBLE);
            deleteUser.setVisibility(View.INVISIBLE);
            // barTitle.setText("ToDo List");
          //  customActionBar.setActionBarColor("#831919");
            barTitle.setText("Interview app");

            Log.i("in 2nd if", "");
        }
        //else {
            Log.i("back count", String.valueOf(backPressCnt));
          //  backPressCnt++;
            if(backPressCnt == 2){
                super.onBackPressed();
            }

      //  }
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
