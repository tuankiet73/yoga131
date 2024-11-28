package com.example.yogoapp1132;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class ViewClassesActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    ListView listViewClasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_classes); // Ensure this matches your XML file name

        myDb = new DatabaseHelper(this);
        listViewClasses = findViewById(R.id.listViewClasses);

        displayClasses();
    }

    private void displayClasses() {
        Cursor cursor = myDb.getAllData(); // Assuming this method fetches all classes
        String[] from = new String[]{DatabaseHelper.COL_1, DatabaseHelper.COL_2, DatabaseHelper.COL_3, DatabaseHelper.COL_4}; // Adjust based on your columns
        int[] to = new int[]{R.id.textViewDay, R.id.textViewTime, R.id.textViewCapacity, R.id.textViewDuration, R.id.textViewPrice, R.id.textViewType};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.class_list_item, cursor, from, to, 0);
        listViewClasses.setAdapter(adapter);
    }
}