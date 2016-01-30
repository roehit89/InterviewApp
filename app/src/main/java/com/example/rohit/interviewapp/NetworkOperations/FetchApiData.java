package com.example.rohit.interviewapp.NetworkOperations;


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

    String Tag = "FetchedData";
    List<ToDoModel> todoResultList;
    List<UserModel>userResultList;


    public List<ToDoModel> getToDoList() {
        bytemarkClient client = ServiceGenerator.createService(bytemarkClient.class);

        Call <List<ToDoModel>> call = client.getAllTodos();

        try {
            todoResultList = call.execute().body();
        } catch (IOException e) {
            Log.i(Tag+" myError",e.toString());
        }

        if(todoResultList == null)
            return null;
        else
        return todoResultList;
    }

    public List<UserModel> getUserList() {
        bytemarkClient client = ServiceGenerator.createService(bytemarkClient.class);

        Call <List<UserModel>> call = client.getAllUsers();

        try {
            userResultList = call.execute().body();
        } catch (IOException e) {
            Log.i(Tag+" myError",e.toString());
        }

        for(UserModel eachObject : userResultList){ // check if object is correctly received.
//            Log.i(Tag+" user result title", String.valueOf(eachObject.getName()));
//            Log.i(Tag+" user result city", String.valueOf(eachObject.getAddress().getCity()));
//            Log.i(Tag+" user result company", String.valueOf(eachObject.getCompany().getCompanyName()));
//            Log.i(Tag+" user result latitute", String.valueOf(eachObject.getAddress().getGeo().getLat()));
       //     Log.i(Tag+" user result id", String.valueOf(eachObject.getId()));
            Log.i(Tag+" user result name", String.valueOf(eachObject.getName()));
            Log.i(Tag+" user result usrname", String.valueOf(eachObject.getUserName()));
        }
        if(userResultList == null)
            return null;
        else
        return userResultList;
    }

    public List<ToDoModel> getTodoById(Integer id){
        bytemarkClient client = ServiceGenerator.createService(bytemarkClient.class);

         List<ToDoModel> toDoModelList = null;

        Call <ToDoModel> call = client.getTodoById(id);

        try {

            ToDoModel toDoModel = call.execute().body();
            todoResultList.add(toDoModel);
        } catch (IOException e) {
            Log.i(Tag+" myError",e.toString());
        }

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

    public UserModel putUser(UserModel userModel,Integer id){
        bytemarkClient client = ServiceGenerator.createService(bytemarkClient.class);
        UserModel returnValue = null;
        Call<UserModel> call = client.putUser(id, userModel);
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

    public UserModel deleteUser(Integer id){
        bytemarkClient client = ServiceGenerator.createService(bytemarkClient.class);
        UserModel returnValue = null;
        Log.i("delete user called","object id "+id);
        Call <UserModel> call = client.deleteUser(id);
        try{
            returnValue = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnValue;
    }


    public UserModel postUser(UserModel userModel){
        bytemarkClient client = ServiceGenerator.createService(bytemarkClient.class);

        Call<UserModel> call = client.postUser(userModel);
        UserModel returnValue = null;
        try{
            returnValue = call.execute().body();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnValue;
    }
}
