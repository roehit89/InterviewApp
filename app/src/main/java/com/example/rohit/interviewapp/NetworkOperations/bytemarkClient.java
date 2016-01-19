package com.example.rohit.interviewapp.NetworkOperations;

import com.example.rohit.interviewapp.Model.ToDoModel;
import com.example.rohit.interviewapp.Model.UserModel;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;


/**
 * Created by Rohit on 1/15/2016.
 */
public interface bytemarkClient {

   // String baseUrl = "http://69.119.215.250:3000";

    @GET("/users")
    Call<List<UserModel>> getAllUsers();
  //  public List<UserModel> getAllUsers (@Path("user") String user);

    @GET("/todos")
    Call <List<ToDoModel>> getAllTodos();

    @GET("/todos/{id}")
    Call <ToDoModel> getTodoById(@Path("id") Integer id);

}
