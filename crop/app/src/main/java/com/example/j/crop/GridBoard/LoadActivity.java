package com.example.j.crop.GridBoard;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.j.crop.R;

public class LoadActivity extends AppCompatActivity {

    boolean checkPlotExists()
    {
        return false;
    }

    private void toNewPlot()
    {
        Intent intent = new Intent(this, NewPlot.class);
        startActivity(intent);
    }

    private void toCurrentPlot()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        if(checkPlotExists())
        {
            toCurrentPlot();
        }
        else
        {
            toNewPlot();
        }
    }
}
