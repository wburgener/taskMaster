package com.example.taskmaster;

public class Todo {

    public String task;
    public long length;
    private int importance;
    public Boolean complete;

    public String getTask() {
        return task;
    }

    public long getLength()
    {
        return length;
    }

    public int getImportance()
    {
        return importance;
    }

    public Boolean getComplete()
    {
        return complete;
    }
}
