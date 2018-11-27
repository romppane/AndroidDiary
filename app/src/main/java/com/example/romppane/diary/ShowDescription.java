package com.example.romppane.diary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowDescription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_description);
        TextView desc = (TextView) findViewById(R.id.descView);
        String s = getIntent().getStringExtra("EXTRA_SESSION_ID");
        desc.setText(s);
    }
}
