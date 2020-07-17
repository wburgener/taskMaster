package com.example.taskmaster;

import java.time.LocalTime;
import java.util.ArrayList;

public class Schedule {

    public ArrayList<Todo> list;
    public String date;

    public ArrayList<Todo> getList() {
        return list;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String newDate)
    {
        this.date = newDate;
    }

}

