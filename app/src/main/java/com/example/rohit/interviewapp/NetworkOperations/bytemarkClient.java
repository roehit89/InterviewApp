package com.example.rohit.interviewapp.NetworkOperations;

import com.example.rohit.interviewapp.Model.ToDoModel;
import com.example.rohit.interviewapp.Model.UserModel;
import com.squareup.okhttp.RequestBody;

import java.util.List;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
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

    @POST("/todos")
    Call <ToDoModel> postToDo(@Body ToDoModel toDoModel);


    @PUT("/todos/{id}")
    Call <ToDoModel> putToDo(@Path("id") Integer id, @Body ToDoModel toDoModel);

    @DELETE("/todos/{id}")
    Call <ToDoModel> deleteToDo(@Path("id")Integer id);

    @DELETE("/users/{id}")
    Call <UserModel> deleteUser(@Path("id")Integer id);


    @POST("/users")
    Call <UserModel> postUser(@Body UserModel userModel);

}
