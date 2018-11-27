package com.example.romppane.diary;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private DatabaseAdapter db;
    private final Context _context = this;
    private Date date = new Date();
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView curdate = (TextView) findViewById(R.id.curDate);
        String message = dateFormat.format(date);
        curdate.setText(message);

        db = new DatabaseAdapter(_context);
        db.open();
        Button create = (Button) findViewById(R.id.Create);
        Button browse = (Button) findViewById(R.id.Browse);

        create.setOnClickListener(MainActivity.this);
        browse.setOnClickListener(MainActivity.this);

    }
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.Create: {
                    Intent intent = new Intent(this, CreateNote.class);
                    startActivity(intent);
                    break;
                }
                case R.id.Browse: {
                    if (db.countRows(db.getDIARY()) == 0) {
                        Toast.makeText(getApplicationContext(), "Your diary is empty!",
                                Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = new Intent(this, BrowseDiary.class);
                        startActivity(intent);
                        break;
                    }


                }
            }
            }

        }
