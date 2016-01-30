package com.example.rohit.interviewapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Rohit on 1/19/2016.
 */
public class CustomActionBar {

    public android.support.v7.app.ActionBar actionBar;
    String color;

    public void customActionBar(ActionBar supportActionBar, Context context) {

        actionBar = supportActionBar;
        //   actionBar.setBackgroundDrawable(new ColorDrawable(Color.argb(255, 255, 255, 255)));
        //actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#831919")));
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false); /// used to get back button on actionbar
        actionBar.setDisplayShowTitleEnabled(false);// to show name of app as title
        actionBar.setDisplayShowCustomEnabled(true);


        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View customView = layoutInflater.inflate(R.layout.custom_action_bar, null);
        actionBar.setCustomView(customView);

    }

    public void setActionBarColor(String color)
    {
        this.color = color;
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(color)));
    }


}
