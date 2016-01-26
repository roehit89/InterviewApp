package com.example.rohit.interviewapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rohit on 1/15/2016.
 */

public class ToDoModel implements Parcelable{

    private Integer userId;
    private String title;
    private String dueDate;
    private Integer id;
   // private Boolean completed;
    private String completed;

    public ToDoModel(){}

    protected ToDoModel(Parcel in) {
        title = in.readString();
        dueDate = in.readString();
        userId = in.readInt();
        id = in.readInt();
      //  completed = in.readByte()!=0; //myBoolean == true if byte != 0
    //    completed =(Boolean) in.readValue(null);
        completed = in.readString();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(dueDate);
        dest.writeInt(userId);
        dest.writeInt(id);
        dest.writeString(completed);
      //  dest.writeValue(completed);
       // dest.writeByte((byte) (completed ? 1 : 0));

    }

    public static final Creator<ToDoModel> CREATOR = new Creator<ToDoModel>() {
        @Override
        public ToDoModel createFromParcel(Parcel in) {
            return new ToDoModel(in);
        }

        @Override
        public ToDoModel[] newArray(int size) {
            return new ToDoModel[size];
        }
    };

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }


}
