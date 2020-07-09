package com.example.taskmaster;

import android.widget.TextView;

public class TextViewFactory {

    public TextView getTextView(int i, String todo, TextView textView)
    {
        textView.setText(todo);
        textView.setId(i);

        return null;
    }
}
