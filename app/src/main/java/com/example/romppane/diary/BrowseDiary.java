package com.example.romppane.diary;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class BrowseDiary extends AppCompatActivity{


    private ArrayList<String[]> marked = new ArrayList();
    private final Context _context = this;
    private DatabaseAdapter db = new DatabaseAdapter(_context);
    private ListAdapter adapter;
    private ListView lv;
    private String sort = "ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_diary);
        setListView(sort);

    }

    public void setListView(String sort) {
        db.open();
        switch (sort){
            case "ID":
                marked = db.get_all_from_table(db.getDIARY());
                break;
            case "TITLE":
                marked = db.getDiaryByTitle(db.getDIARY());
                break;
            case "DATE":
                marked = db.getDiaryByDate(db.getDIARY());
                break;
        }
        db.close();

        String[] apu;
        String[] contents = new String[marked.size()];


        for (int i = 0; i < marked.size(); i++) {
            apu = marked.get(i);
            contents[i] = apu[1] + ": " + apu[2];
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contents);
        lv = (ListView) findViewById(R.id.list);
        lv.setAdapter(adapter);
        registerForContextMenu(lv);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                long pos = adapter.getItemIdAtPosition(position);
                String value = marked.get((int)pos)[3];
                Intent intent = new Intent(getBaseContext(), ShowDescription.class);
                intent.putExtra("EXTRA_SESSION_ID", value);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        String[] contents = new String[marked.size()];
        String[] apu;
        for (int i = 0; i < marked.size(); i++) {
            apu = marked.get(i);
            contents[i] = apu[1] + ": " + apu[2];
        }
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        menu.setHeaderTitle(contents[info.position]);
        menu.add(0, v.getId(), 0,"UPDATE");
        menu.add(0, v.getId(), 0,"DELETE");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        if(item.getTitle() == "UPDATE"){
            edit(info.position);
            }else if(item.getTitle() ==  "DELETE"){
            delete(info.position);
            }else {return false;}
        return true;
        }

    public void edit(int i){
        Intent intent = new Intent(getBaseContext(), EditNote.class);
        String value = marked.get(i)[0];
        intent.putExtra("EXTRA_MESSAGE", value);
        startActivity(intent);
        }

    public void delete(int i){
        db.open();
        db.deleteDiary(Integer.parseInt(marked.get(i)[0]));
        db.close();
        }

    @Override
    protected void onResume() {
        super.onResume();
        setListView(sort);
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.byTitle:
                sort = "TITLE";
                setListView(sort);
                return true;
            case R.id.byDate:
                sort = "DATE";
                setListView(sort);
                return true;
            case R.id.byId:
                sort = "ID";
                setListView(sort);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
