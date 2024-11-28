package com.example.yogoapp1132;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ManageInstancesActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editTextDate, editTextTeacher, editTextComments;
    ListView listViewInstances;
    Button buttonAddInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_instances);

        myDb = new DatabaseHelper(this);
        editTextDate = findViewById(R.id.editTextDate);
        editTextTeacher = findViewById(R.id.editTextTeacher);
        editTextComments = findViewById(R.id.editTextComments);
        listViewInstances = findViewById(R.id.listViewInstances);
        buttonAddInstance = findViewById(R.id.buttonAddInstance);

        int courseId = getIntent().getIntExtra("COURSE_ID", -1);

        buttonAddInstance.setOnClickListener(v -> addInstance(courseId));
        listViewInstances.setOnItemClickListener((parent, view, position, id) -> editInstance(id));

        displayInstances(courseId);
    }

    private void displayInstances(int courseId) {
        Cursor cursor = myDb.getInstancesByCourseId(courseId);
        String[] from = new String[]{DatabaseHelper.COL_INSTANCE_DATE, DatabaseHelper.COL_INSTANCE_TEACHER, DatabaseHelper.COL_INSTANCE_COMMENTS};
        int[] to = new int[]{R.id.textViewInstanceDate, R.id.textViewInstanceTeacher, R.id.textViewInstanceComments};

        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.instance_list_item, cursor, from, to, 0);
        listViewInstances.setAdapter(adapter);
    }

    private void addInstance(int courseId) {
        String date = editTextDate.getText().toString();
        String teacher = editTextTeacher.getText().toString();
        String comments = editTextComments.getText().toString();

        if (date.isEmpty() || teacher.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isInserted = myDb.insertInstance(courseId, date, teacher, comments);
        if (isInserted) {
            Toast.makeText(this, "Instance Added", Toast.LENGTH_LONG).show();
            displayInstances(courseId); // Refresh the list
        } else {
            Toast.makeText(this, "Failed to add instance", Toast.LENGTH_LONG).show();
        }
    }

    private void editInstance(long id) {
        // Implement the edit functionality here
    }
}