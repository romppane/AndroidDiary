package com.example.romppane.diary;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditNote extends AppCompatActivity implements View.OnClickListener {

    private final Context _context = this;
    private DatabaseAdapter db = new DatabaseAdapter(_context);
    private Date date = new Date();
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private EditText description;
    private EditText title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        Button submit = (Button) findViewById(R.id.editButton);
        title = (EditText) findViewById(R.id.editTitle);
        description = (EditText) findViewById(R.id.editDesc);

        submit.setOnClickListener(EditNote.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.editButton: {
                db.open();
                //Tarkista että on inputteja!!
                String id = getIntent().getStringExtra("EXTRA_MESSAGE");
                db.updateDiary(Long.parseLong(id), title.getText().toString(), description.getText().toString(), dateFormat.format(date));
                db.close();
                finish();
                break;
            }



        }
    }

}



