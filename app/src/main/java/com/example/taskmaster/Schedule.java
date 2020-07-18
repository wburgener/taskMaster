package com.example.taskmaster;

import java.time.LocalTime;
import java.util.ArrayList;

public class Schedule {

    public ArrayList<Todo> list = new ArrayList<Todo>(15);
    public String date = new String();

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

