package com.example.uploadvideo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

public class StressData extends AppCompatActivity {

    TextView t1,t2,t3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stress);

        t1=(TextView)findViewById(R.id.instructions1);
        t2=(TextView)findViewById(R.id.instructions2);
        t3=(TextView)findViewById(R.id.instructions3);
    }
}
