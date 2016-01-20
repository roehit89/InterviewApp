package com.example.rohit.interviewapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

/**
 * Created by Rohit on 1/20/2016.
 */
public class DialogActions {

    Dialog toDoDialog;
    Context context;
    Activity activity;

    DialogActions(Context context){
        //this.activity = activity;
        this.context = context;
    }

    public void showDialog(Context context){
        toDoDialog = new Dialog(context);
        toDoDialog.setContentView(R.layout.tododialog);

        toDoDialog.show();
    }

}
