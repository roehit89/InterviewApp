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
import com.example.rohit.interviewapp.NetworkOperations.FetchApiData;
import com.example.rohit.interviewapp.R;
import com.example.rohit.interviewapp.ToDoActivity;

import java.util.Calendar;
import java.util.List;


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
    private Switch switch_id;
    private TextView todo_user_id;
    private TextView todo_title;
    Integer user_id_value;
    private Boolean completed;
    ImageButton addUser;
    ImageButton editUser;
    ToDoModel tempTodo = new ToDoModel();
    Integer toDoMaxId;

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

      //  todo_user_id = (TextView) view.findViewById(R.id.todo_user_id);
        todo_title = (TextView)view.findViewById(R.id.todo_title);

        switch_id = (Switch) view.findViewById(R.id.switch_id);
        date_id = (TextView) view.findViewById(R.id.date_id);
        time_id = (TextView) view.findViewById(R.id.time_id);

        add_Button = (TextView) view.findViewById(R.id.add_Button_todo);
        cancel_Button = (TextView) view.findViewById(R.id.cancel_Button_todo);

        addUser = (ImageButton) getActivity().findViewById(R.id.addButtonId);
        editUser = (ImageButton) getActivity().findViewById(R.id.editButtonId);

        addUser.setVisibility(View.INVISIBLE);
        editUser.setVisibility(View.INVISIBLE);

        if(getArguments()!=null) {
            if (getArguments().get("toDoMaxId") != null) {
                String temp = (String) getArguments().get("toDoMaxId");
                toDoMaxId = Integer.parseInt(temp);
                Log.i("userModelListLength", String.valueOf(toDoMaxId));
            }
        }

        switch_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(switch_id.isChecked()){
                    completed = true;
                    Log.i("switch value",completed.toString());
                    switch_id.setText("Yes   ");
                }
                else {
                    completed = false;
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
                            time_id.setError("Invalid date fdjska;fj sa;fjdka;fjdksa;f jdksa; fjdksa"); /// doesn't work for some reason.
                        }
                        else {
                            time_id.setText(selectedHour + ":" + selectedMinute + ":"+ "00");
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
//                addUser.setVisibility(View.VISIBLE);
            }
        });
        add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addUser.setVisibility(View.VISIBLE);

                final String title_value = String.valueOf(todo_title.getText());
               // String temp = todo_user_id.getText().toString();
               // if(!temp.isEmpty()) {
//                    user_id_value = Integer.parseInt(String.valueOf(todo_user_id.getText()));
                user_id_value = getActivity().getIntent().getIntExtra("userId",-1);
               // }
                // final Integer todo_id_value = Integer.parseInt(String.valueOf(todo_id.getText()));
                final String todo_date = String.valueOf(date_id.getText());
                final String todo_time = String.valueOf(time_id.getText());

                //   Log.i("clicked event fragmnt title", String.valueOf(title_value));
                //   Log.i("clicked event fragmnt user id", String.valueOf(user_id_value));
                //     Log.i("clicked event fragmnt todo id", String.valueOf(todo_id_value));

                //Log.i("clicked event fragmnt date", String.valueOf(date_id.getText()));
                //Log.i("clicked event fragmnt time", String.valueOf(time_id.getText()));

                if(title_value.isEmpty())
                {
                    todo_title.setError("field cannot be empyt");
                }
//                if(user_id_value.toString().isEmpty())
//                {
//                    todo_user_id.setError("field cannot be empyt");
//                }
                else {

                    //final List<ToDoModel> toDoModelList = getActivity().getIntent().getParcelableArrayListExtra("toDoModelList");
                    // Log.i("post request", String.valueOf(fetchApiData.postToDo(tempTodo).getTitle()));
                    tempTodo = new ToDoModel();

                    //  tempTodo.setId(todo_id_value);

                    tempTodo.setId(toDoMaxId);
                    Log.i("obj id set", String.valueOf(tempTodo.getId()));
                    tempTodo.setTitle(title_value);
                    //tempTodo.setCompleted(true);
                    tempTodo.setCompleted(completed);
                    // tempTodo.setDueDate("2016-03-21T12:22:45.000Z");
                    tempTodo.setDueDate(todo_date + todo_time);
                    tempTodo.setUserId(user_id_value);
                    ToDoActivity toDoActivity = new ToDoActivity();

                    toDoActivity.addTotoDoModelList(tempTodo);

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            fetchApiData.postToDo(tempTodo);

                            //toDoModelList.add(tempTodo);

//                        CustomAdapterForToDo customAdapter = new CustomAdapterForToDo(toDoModelList,getActivity().getWindow().getContext());
//                        ListView listView = (ListView) view.findViewById(R.id.fullListViewToDo);
//                        listView.setAdapter(customAdapter);
                            // notifyAll();

                        }
                    }).start();

                    listView.setVisibility(View.VISIBLE);
                  //  Log.i("clicked event fragmnt", String.valueOf(toDoModelList.size()));
                    //Toast.makeText(getActivity().getWindow().getContext(), "Added to todo list", Toast.LENGTH_LONG).show();
                    Toast.makeText(getActivity(), "Added to todo list", Toast.LENGTH_LONG).show();
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
