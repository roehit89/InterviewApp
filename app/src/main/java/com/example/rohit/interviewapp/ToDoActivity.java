package com.example.rohit.interviewapp;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rohit.interviewapp.Adapters.CustomAdapterForToDo;
import com.example.rohit.interviewapp.Fragments.ToDoFragment;
import com.example.rohit.interviewapp.Model.ToDoModel;
import com.example.rohit.interviewapp.NetworkOperations.FetchApiData;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

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
    //ImageButton addUser1;
    @Bind(R.id.fab) ImageButton addUser1;
    ImageButton deleteUser;
    ImageButton editUser;
    Integer buttonsVisible = 0;
    Integer maxId = 0;
    Integer backPressCnt = 0;

    Context context;
    String userName;
    TextView barTitle = null;
    public android.support.v7.app.ActionBar actionBar;
    FragmentTransaction fragmentTransaction;
    ToDoFragment toDoFragment;
    ToDoFragment toDoFragmentEdit;
    String splitString[];

    FetchApiData fetchApiData = new FetchApiData();
    ToDoModel todoModelToDelete = new ToDoModel();

    public ArrayList<ToDoModel> getToDoModelListbyId() {
        return this.toDoModelListbyId;
    }

    public void setToDoModelListbyId(ArrayList<ToDoModel> toDoModelListbyId) {
        ToDoActivity.toDoModelListbyId = toDoModelListbyId;
    }

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
     //   toDoModelListbyId.clear();
        toDoModelListbyId = getToDoModelList();
        toDoModelListbyId.add(toDoModel);
        customAdapter = getCustomAdapter();
        customAdapter.notifyDataSetChanged();
    }

    public void updateUserModelList(ToDoModel toDoModel, Integer id){
        toDoModelListbyId = getToDoModelListbyId();

        ToDoModel updateModel = toDoModel;
        updateModel.setId(id);

       // this.addUser.setVisibility(View.VISIBLE);

        for(ToDoModel obj  : toDoModelListbyId){
            if(obj.getId() == id){
                toDoModelListbyId.remove(obj);
                toDoModelListbyId.add(updateModel);
                setToDoModelListbyId(toDoModelListbyId);

                customAdapter = getCustomAdapter();
                customAdapter.notifyDataSetChanged();

                break;
            }
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todoactivity);
        context = this;
        customActionBar.customActionBar(getSupportActionBar(), this);

        ButterKnife.bind(this);

        backPressCnt = 1;
       // addUser1 = (ImageButton) findViewById(R.id.fab);

        deleteUser = (ImageButton) findViewById(R.id.deleteButtonId);
        editUser = (ImageButton) findViewById(R.id.editButtonId);
        //backButton = (ImageButton) findViewById(R.id.backButtonId);

//        backButton.setVisibility(View.VISIBLE);

        Resources resources = this.getResources();

 //       addUser.setImageDrawable(resources.getDrawable(R.drawable.ic_plus_circle_outline));


//        addUser.setVisibility(View.VISIBLE);
        deleteUser.setVisibility(View.INVISIBLE);
        editUser.setVisibility(View.INVISIBLE);


        userId = getIntent().getIntExtra("userId",-1);
        Log.i("received userid", String.valueOf(userId));

        userName = getIntent().getExtras().getString("userName");

        splitString = userName.split("\\s+");
        Log.i("fetched usrname in todo",userName);

        barTitle = (TextView) findViewById(R.id.actionBarTitle);
        barTitle.setText(splitString[0] + "'s to do list");

        new Thread(new Runnable() {
            @Override
            public void run() {
                toDoModelList = (ArrayList<ToDoModel>) fetchApiData.getToDoList();
                if(!toDoModelListbyId.isEmpty()) {
                    toDoModelListbyId.clear();
                }
                for(ToDoModel toDoModel : toDoModelList){ // ToDoModel object matching with clicked userId extracted and added to toDoModelListbyId
                    if(toDoModel.getUserId() == userId){
                        Log.i("user id matched", toDoModel.getUserId().toString());
                        toDoModelListbyId.add(toDoModel);
                    }
                }

                setToDoModelListbyId(toDoModelListbyId);

                setCustomAdapter(new CustomAdapterForToDo(toDoModelListbyId, context));
                customAdapter = getCustomAdapter();
                customAdapter.notifyDataSetChanged();

               // setToDoModelList(toDoModelListbyId);

                listView = (ListView) findViewById(R.id.fullListViewToDo);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    listView.setAdapter(customAdapter);

                    listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                        backPressCnt = 0;
                        barTitle.setText(toDoModelListbyId.get(position).getTitle());
                        deleteUser.setVisibility(View.VISIBLE);
                        editUser.setVisibility(View.VISIBLE);
                        buttonsVisible = 1;
                        Log.i("obj title selcted", String.valueOf(toDoModelListbyId.get(position).getTitle()));
                        Log.i("obj id", String.valueOf(toDoModelListbyId.get(position).getId()));
                        todoModelToDelete = toDoModelListbyId.get(position); // fetch object to be deleted or edited

                        editUser.setOnClickListener(new View.OnClickListener() {
                            @Override
                                public void onClick(View v) {
                                toDoFragmentEdit = new ToDoFragment();
                                Bundle bundle = new Bundle();

                                bundle.putParcelable("object to delete",todoModelToDelete);

                                toDoFragmentEdit.setArguments(bundle);

                                fragmentTransaction = getFragmentManager().beginTransaction();
                                fragmentTransaction.add(R.id.todo_fragmentContainer, toDoFragmentEdit);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();

                                listView.setVisibility(View.GONE); /// hide view on clicks made on fragment don't reflect on activity
                                addUser1.setVisibility(View.INVISIBLE);
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
                                                        fetchApiData.deleteToDo(toDoModelListbyId.get(position).getId());

                                                        toDoModelList = (ArrayList<ToDoModel>) fetchApiData.getToDoList();
                                                        maxId = 0;
                                                        for(ToDoModel toDoModel : toDoModelList){
                                                            if(toDoModel.getId()>maxId){
                                                                maxId = toDoModel.getId();
                                                            }
                                                        }

                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                toDoModelListbyId.remove(toDoModelListbyId.get(position));
                                                                customAdapter.notifyDataSetChanged();
                                                                //  addUser.setVisibility(View.VISIBLE);

                                                            }
                                                        });
                                                    }
                                                }).start();


                                            barTitle.setText(splitString[0] + "'s to do list");
                                            Toast.makeText(context, "Todo deleted", Toast.LENGTH_SHORT).show();
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
                        return false;
                    }
                });
            }
        });}
        }).start();


       // addUser.setEnabled(true);

        addUser1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("add button clicked", " ");
                maxId = 0;
                backPressCnt = 0;
                //addUser.setVisibility(View.INVISIBLE);
                for (ToDoModel toDoModel : toDoModelList) {
                    if (toDoModel.getId() > maxId) {
                        maxId = toDoModel.getId();
                    }
                }
                Bundle bundle = new Bundle();
                bundle.putString("toDoMaxId", String.valueOf(maxId + 1));

                toDoFragment = new ToDoFragment();
                toDoFragment.setArguments(bundle);

                fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.add(R.id.todo_fragmentContainer, toDoFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                addUser1.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);

            }
        });

    }

    @Override
    public void onBackPressed() {
        backPressCnt++;
        if (getFragmentManager().getBackStackEntryCount() > 0 ){
            getFragmentManager().popBackStack();
            listView.setVisibility(View.VISIBLE);
            addUser1.setVisibility(View.VISIBLE);
            Log.i("came in 1st if"," ");
        }
        if(buttonsVisible == 1){
            deleteUser.setVisibility(View.INVISIBLE);
            editUser.setVisibility(View.INVISIBLE);
            buttonsVisible = 0;
            barTitle.setText(splitString[0] + "'s to do list");
            Log.i("came in 1st if", " ");
        }
      //  else {
            //backPressCnt++;
            Log.i("back count ",backPressCnt.toString());
            if(backPressCnt == 2){
               // backPressCnt = 0;
                super.onBackPressed();
        //    }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
