package com.example.taskmaster;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ButtonFactory {

    public Button getButton(int i, Button button)
    {
        button.setText("Complete");
        button.setId(i);

        return null;

    }

}
