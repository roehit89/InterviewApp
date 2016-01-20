package com.example.rohit.interviewapp.NetworkOperations;

import android.util.Log;

import com.example.rohit.interviewapp.Model.ToDoModel;
import com.example.rohit.interviewapp.Model.UserModel;
import com.example.rohit.interviewapp.NetworkOperations.ServiceGenerator;
import com.example.rohit.interviewapp.NetworkOperations.bytemarkClient;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Rohit on 1/15/2016.
 */
public class FetchApiData {


    UserModel userModel = new UserModel();
    String Tag = "FetchedData";
    List<ToDoModel> todoResultList;
    List<UserModel>userResultList;
    List<ToDoModel> toDoModelList;

    public List<ToDoModel> getToDoList() {
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
      //      Log.i(Tag+" result title", String.valueOf(eachObject.getTitle()));
      //      Log.i(Tag+" result userId", String.valueOf(eachObject.getUserId()));
        }
        return todoResultList;
    }

    public List<UserModel> getUserList() {
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
//            Log.i(Tag+" user result city", String.valueOf(eachObject.getAddress().getCity()));
//            Log.i(Tag+" user result company", String.valueOf(eachObject.getCompany().getCompanyName()));
//            Log.i(Tag+" user result latitute", String.valueOf(eachObject.getAddress().getGeo().getLat()));
        }
        return userResultList;
    }

    public List<ToDoModel> getTodoById(Integer id){
        bytemarkClient client = ServiceGenerator.createService(bytemarkClient.class);

         List<ToDoModel> toDoModelList = null;

        Call <ToDoModel> call = client.getTodoById(id);

        try {
            //call.execute().body()
            ToDoModel toDoModel = call.execute().body();
            todoResultList.add(toDoModel);
            //call.execute().body();
            //call.execute();
        } catch (IOException e) {
            Log.i(Tag+" myError",e.toString());
        }
        //todoResultList = getTodoById(id);
        for(ToDoModel toDoModel : todoResultList) {
            Log.i(Tag + " user result title10", toDoModel.getTitle());
        }

        return toDoModelList;
    }


    public ToDoModel postToDo(ToDoModel toDoModel){
        bytemarkClient client = ServiceGenerator.createService(bytemarkClient.class);

        Call<ToDoModel> call = client.postToDo(toDoModel);
        ToDoModel returnValue = null;
        try{
            returnValue = call.execute().body();

        } catch (IOException e) {
            e.printStackTrace();
        }
    return returnValue;
    }


    public ToDoModel putToDo(ToDoModel toDoModel,Integer id){
        bytemarkClient client = ServiceGenerator.createService(bytemarkClient.class);
        ToDoModel returnValue = null;
        Call<ToDoModel> call = client.putToDo(id, toDoModel);
        try{
         returnValue = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    public ToDoModel deleteToDo(Integer id){
        bytemarkClient client = ServiceGenerator.createService(bytemarkClient.class);
        ToDoModel returnValue = null;

        Call <ToDoModel> call = client.deleteToDo(id);
        try{
            returnValue = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnValue;
    }
}
