package com.example.romppane.diary;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateNote extends AppCompatActivity implements View.OnClickListener {

    private final Context _context = this;
    private DatabaseAdapter db = new DatabaseAdapter(_context);
    private Date date = new Date();
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private EditText description;
    private EditText title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        Button submit = (Button) findViewById(R.id.submit);
        title = (EditText) findViewById(R.id.Title);
        description = (EditText) findViewById(R.id.Description);


        submit.setOnClickListener(CreateNote.this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit: {
                db.open();
                db.insertDiary(dateFormat.format(date), title.getText().toString(), description.getText().toString(), dateFormat.format(date));
                db.close();
                finish();
                break;
            }



            }
        }

}



