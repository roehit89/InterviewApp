package com.example.rohit.interviewapp;

import android.util.Log;

import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Rohit on 1/15/2016.
 */
public class FetchApiData {


    UserModel userModel = new UserModel();
    String Tag = "FetchedData";
    List<ToDoModel> todoResultList;

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
            Log.i(Tag+" result title", eachObject.getToDoTitle());
        }
        return todoResultList;
    }
}
