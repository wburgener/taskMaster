package com.example.taskmaster;

import java.util.ArrayList;

public class Schedule {

    public ArrayList<Todo> list;
    private String type;
    public int startTime;
    public int endTime;

    public ArrayList<Todo> getList()
    {
        return list;
    }

    public String getType()
    {
        return type;
    }
    public int getStartTime()
    {
        return startTime;
    }

    public int getEndTime()
    {
        return endTime;
    }
}
