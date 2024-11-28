package com.example.yogoapp1132;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    ListView listViewClasses;
    Button buttonAddClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);
        listViewClasses = findViewById(R.id.listViewClasses);
        buttonAddClass = findViewById(R.id.buttonSubmit);

        buttonAddClass.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddClassActivity.class);
            startActivity(intent);
        });

        displayClasses();
    }

    private void displayClasses() {
        Cursor cursor = myDb.getAllData();
        String[] from = new String[]{DatabaseHelper.COL_2, DatabaseHelper.COL_3, DatabaseHelper.COL_4};
        int[] to = new int[]{R.id.textViewClassDay, R.id.textViewClassTime, R.id.textViewClassCapacity};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.class_list_item, cursor, from, to, 0);
        listViewClasses.setAdapter(adapter);

        listViewClasses.setOnItemClickListener((parent, view, position, id) -> {
            Cursor cursor1 = (Cursor) listViewClasses.getItemAtPosition(position);
            int courseId = cursor1.getInt(cursor1.getColumnIndex(DatabaseHelper.COL_1));
            Intent intent = new Intent(MainActivity.this, ManageInstancesActivity.class);
            intent.putExtra("COURSE_ID", courseId);
            startActivity(intent);
        });
    }
}