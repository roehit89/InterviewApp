package com.example.rohit.interviewapp.Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.rohit.interviewapp.Model.ToDoModel;
import com.example.rohit.interviewapp.Model.UserModel;
import com.example.rohit.interviewapp.NetworkOperations.FetchApiData;
import com.example.rohit.interviewapp.R;
import com.example.rohit.interviewapp.ToDoActivity;

import java.util.Calendar;
import java.util.List;

import butterknife.Bind;


public class ToDoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    Button fragmentButton;
    TextView title_label;
    private TextView todo_id;
    private TextView date_id;
    private TextView time_id;
    private TextView add_Button;
    private TextView cancel_Button;
   // @Bind(R.id.switch_id) Switch switch_id;
    private Switch switch_id;
    private TextView todo_user_id;
    private TextView todo_title;
    Integer user_id_value;
    private String completed = "false";
    ImageButton addUser;
    ImageButton editUser;
    ToDoModel tempTodo = new ToDoModel();
    Integer toDoMaxId;
    ToDoModel deleteObject = new ToDoModel();
    Integer flag_edit = 0;
    String todo_date;
    String todo_time;

    Activity activity;
    ListView listView;
    View view;

    FetchApiData fetchApiData = new FetchApiData();

    public static ToDoFragment newInstance(String param1, String param2) {
        ToDoFragment fragment = new ToDoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ToDoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_todo, container, false);

        listView = (ListView) getActivity().findViewById(R.id.fullListViewToDo);

        activity = getActivity();
      //  todo_user_id = (TextView) view.findViewById(R.id.todo_user_id);
        todo_title = (TextView)view.findViewById(R.id.todo_title);

        switch_id = (Switch) view.findViewById(R.id.switch_id);
        date_id = (TextView) view.findViewById(R.id.date_id);
        time_id = (TextView) view.findViewById(R.id.time_id);

        add_Button = (TextView) view.findViewById(R.id.add_Button_todo);
        cancel_Button = (TextView) view.findViewById(R.id.cancel_Button_todo);

        addUser = (ImageButton) getActivity().findViewById(R.id.fab);
        editUser = (ImageButton) getActivity().findViewById(R.id.editButtonId);

//        addUser.setVisibility(View.INVISIBLE);
        editUser.setVisibility(View.INVISIBLE);

        if(getArguments()!=null) {
            Log.i("some arguments set"," ");
            if (getArguments().get("toDoMaxId") != null) {
                String temp = (String) getArguments().get("toDoMaxId");
                toDoMaxId = Integer.parseInt(temp);
                Log.i("userModelListLength", String.valueOf(toDoMaxId));
            }else{
//                //deleteObject = (ToDoModel) getArguments().getSerializable("object to delete");
                deleteObject = getArguments().getParcelable("object to delete");
                Log.i("object received", deleteObject.getTitle());
                Log.i("object received", String.valueOf(deleteObject.getId())); ///////////////////// working fine.. object is passed here with id

                flag_edit = 1;
                todo_title.setText(deleteObject.getTitle());
                String temp_date_time[] = deleteObject.getDueDate().split("T");
                todo_title.setEnabled(false);

                date_id.setText(temp_date_time[0]);
                time_id.setText(temp_date_time[1]);
                Log.i("complete date/time",deleteObject.getDueDate());
                Log.i("date fetched =", temp_date_time[0]);
                Log.i("time fetched =", temp_date_time[1]);

                Log.i("completed or not",deleteObject.getCompleted().toString());

                if(deleteObject.getCompleted().equals("true")){
                    switch_id.setChecked(true);
                    switch_id.setText("Yes   ");
                }
                else{
                    switch_id.setChecked(false);
                    switch_id.setText("No   ");
                }
            }
        }

        switch_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switch_id.isChecked()){
                    completed = "true";
                    Log.i("switch value",completed.toString());
                    switch_id.setText("Yes   ");
                }
                else {
                    completed = "false";
                    Log.i("switch value",completed.toString());
                    switch_id.setText("No   ");
                }
            }
        });
        time_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(time_id.getWindowToken(), 0);

                Calendar mcurrentTime = Calendar.getInstance();
                final int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity().getWindow().getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        Log.i("current hour", String.valueOf(hour));
                        if(selectedHour<hour)
                        {
                            time_id.setError("Invalid date"); /// doesn't work for some reason.
                        }
                        else {
                            time_id.setText(selectedHour + ":" + selectedMinute + ":"+ "00Z");
                            time_id.setError(null);
                        }
                    }
                }, hour, minute,true);


                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        date_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(date_id.getWindowToken(), 0);
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                // Log.i(" testFragment "," date picker clicked");

                DatePickerDialog dialog = new DatePickerDialog(getActivity().getWindow().getContext(),new mDateSetListener(), mYear, mMonth, mDay);
                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dialog.setTitle("Select Date");
                dialog.show();
            }
        });

        cancel_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().popBackStack();
                ListView listView = (ListView)getActivity().findViewById(R.id.fullListViewToDo);
                listView.setVisibility(View.VISIBLE);
                addUser.setVisibility(View.VISIBLE);
            }
        });
        add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addUser.setVisibility(View.VISIBLE);

                final String title_value = String.valueOf(todo_title.getText());
                user_id_value = getActivity().getIntent().getIntExtra("userId",-1);
                todo_date = String.valueOf(date_id.getText());
                todo_time = String.valueOf(time_id.getText());

                if(title_value.isEmpty() || todo_date.isEmpty() || todo_time.isEmpty())
                {
                    if(title_value.isEmpty())
                        todo_title.setError("Title cannot be empty");
                    if(todo_date.isEmpty())
                        date_id.setError("Date cannot be empty");
                    if(todo_time.isEmpty())
                        time_id.setError("Time cannot be empty");
                }
                else {
                    tempTodo = new ToDoModel();
                    tempTodo.setId(toDoMaxId);
                    Log.i("obj id set", String.valueOf(tempTodo.getId()));
                    tempTodo.setTitle(title_value);
                    tempTodo.setCompleted(String.valueOf(completed));
                    tempTodo.setDueDate(todo_date +"T"+todo_time);
                    tempTodo.setUserId(user_id_value);


                    if(flag_edit == 0) {
                        ToDoActivity toDoActivity = new ToDoActivity();
                        toDoActivity.addTotoDoModelList(tempTodo);

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                fetchApiData.postToDo(tempTodo);
                            }
                        }).start();

                        listView.setVisibility(View.VISIBLE);
                        Toast.makeText(getActivity(), "Added to todo list", Toast.LENGTH_LONG).show();

                    }
                    else{

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                flag_edit = 0;
                                Log.i("delete obj id", String.valueOf(deleteObject.getId()));

                                fetchApiData.putToDo(tempTodo, deleteObject.getId());
                                Log.i("todo edited", " edited");

                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToDoActivity toDoActivity = new ToDoActivity();
                                    toDoActivity.updateUserModelList(tempTodo, deleteObject.getId());

                                    listView.setVisibility(View.VISIBLE);
                                    Toast.makeText(activity.getApplicationContext(), "User updated ", Toast.LENGTH_SHORT).show();
                                }
                            });
                            }
                        }).start();
                    }
                    getActivity().getFragmentManager().popBackStack();
                }
            }
        });
        return view;

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }



    class mDateSetListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            // getCalender();
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;
            date_id.setText(new StringBuilder()
                            // Month is 0 based so add 1
                            .append(mYear)
                            .append("-")
                            .append(mMonth + 1)
                            .append("-")
                            .append(mDay)
                            .append(" ")

            );
            System.out.println(date_id.getText().toString());


        }
    }

    class mTimeSetListener implements TimePickerDialog.OnTimeSetListener {


        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            int mHourOfDay = hourOfDay;
            int mMinute = minute;

            time_id.setText(new StringBuilder().append(mHourOfDay).append(":").append(minute));
        }
    }

}
