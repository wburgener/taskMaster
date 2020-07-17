package com.example.taskmaster;

public class Todo {

    public String task;
    public long length;
    private String importance;

    public Boolean complete = false;

    public String getTask() {
        return task;
    }

    public String getImportance()
    {
        return importance;
    }



    public Boolean getComplete()
    {
        return complete;
    }

    public void setTask(String newTask)
    {
        this.task = newTask;
    }

    public void setImportance(String newImportance)
    {
        this.importance = newImportance;
    }


}
