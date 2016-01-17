package com.example.rohit.interviewapp;

import android.util.Log;

import com.example.rohit.interviewapp.Model.ToDoModel;
import com.example.rohit.interviewapp.Model.UserModel;

import java.io.IOException;
import java.util.List;

import retrofit.Call;

/**
 * Created by Rohit on 1/15/2016.
 */
public class FetchApiData {


    UserModel userModel = new UserModel();
    String Tag = "FetchedData";
    List<ToDoModel> todoResultList;
    List<UserModel>userResultList;

    List<ToDoModel> getToDoList() {
        bytemarkClient client = ServiceGenerator.createService(bytemarkClient.class);

       // final String[] result = {null};
//        Call<UserModel> call1 = service.getAllUsers();
        Call <List<ToDoModel>> call = client.getAllTodos();

        try {
            todoResultList = call.execute().body();
        } catch (IOException e) {
            Log.i(Tag+" myError",e.toString());
        }

        for(ToDoModel eachObject : todoResultList){ // check if object is correctly received.
            Log.i(Tag+" result title", String.valueOf(eachObject.getTitle()));
        }
        return todoResultList;
    }

    List<UserModel> getUserList() {
        bytemarkClient client = ServiceGenerator.createService(bytemarkClient.class);

        // final String[] result = {null};
//        Call<UserModel> call1 = service.getAllUsers();
        Call <List<UserModel>> call = client.getAllUsers();

        try {
            userResultList = call.execute().body();
        } catch (IOException e) {
            Log.i(Tag+" myError",e.toString());
        }

        for(UserModel eachObject : userResultList){ // check if object is correctly received.
            Log.i(Tag+" user result title", String.valueOf(eachObject.getName()));
            Log.i(Tag+" user result city", String.valueOf(eachObject.getAddress().getCity()));
            Log.i(Tag+" user result company", String.valueOf(eachObject.getCompany().getCompanyName()));
            Log.i(Tag+" user result latitute", String.valueOf(eachObject.getAddress().getGeo().getLat()));
        }
        return userResultList;
    }
}
