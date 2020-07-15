package com.example.taskmaster;

import java.time.LocalTime;
import java.util.ArrayList;

public class Schedule {

    public ArrayList<Todo> list;
    private String type;
    public LocalTime startTime;
    public LocalTime endTime;

    public ArrayList<Todo> getList()
    {
        return list;
    }

    public String getType()
    {
        return type;
    }
    public LocalTime getStartTime()
    {
        return startTime;
    }

    public LocalTime getEndTime()
    {
        return endTime;
    }
}
