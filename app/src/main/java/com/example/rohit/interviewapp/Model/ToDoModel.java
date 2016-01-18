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
    private Boolean completed;
    private String dueDate;
    private Integer id;

    protected ToDoModel(Parcel in) {
        title = in.readString();
        dueDate = in.readString();
        userId = in.readInt();
        id = in.readInt();
        completed = in.readByte()!=0; //myBoolean == true if byte != 0

    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(dueDate);
        dest.writeInt(userId);
        dest.writeInt(id);
        dest.writeByte((byte) (completed?1:0));
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

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
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
