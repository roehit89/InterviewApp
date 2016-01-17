package com.example.rohit.interviewapp;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;


/**
 * Created by Rohit on 1/15/2016.
 */
public interface bytemarkClient {

   // String baseUrl = "http://69.119.215.250:3000";

    @GET("/users")
    Call<UserModel> getAllUsers();
  //  public List<UserModel> getAllUsers (@Path("user") String user);

    @GET("/todos")
    Call <List<ToDoModel>> getAllTodos();

}
