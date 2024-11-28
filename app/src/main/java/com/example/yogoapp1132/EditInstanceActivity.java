package com.example.yogoapp1132; // Ensure this matches your package structure

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

public class EditInstanceActivity extends AppCompatActivity {
    private EditText courseIdEditText;
    private EditText dateEditText;
    private EditText teacherEditText;
    private EditText commentsEditText;
    private Button saveButton;
    private int instanceId; // ID of the instance to edit

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_instance);

        courseIdEditText = findViewById(R.id.courseIdEditText);
        dateEditText = findViewById(R.id.dateEditText);
        teacherEditText = findViewById(R.id.teacherEditText);
        commentsEditText = findViewById(R.id.commentsEditText);
        saveButton = findViewById(R.id.saveButton);

        // Get the instance data passed from the previous activity
        Intent intent = getIntent();
        instanceId = intent.getIntExtra("INSTANCE_ID", -1);
        String courseId = intent.getStringExtra("COURSE_ID");
        String date = intent.getStringExtra("DATE");
        String teacher = intent.getStringExtra("TEACHER");
        String comments = intent.getStringExtra("COMMENTS");

        // Populate the fields with the existing data
        courseIdEditText.setText(courseId);
        dateEditText.setText(date);
        teacherEditText.setText(teacher);
        commentsEditText.setText(comments);

        // Set up the save button
        saveButton.setOnClickListener(v -> saveChanges());
    }

    private void saveChanges() {
        String newCourseId = courseIdEditText.getText().toString();
        String newDate = dateEditText.getText().toString();
        String newTeacher = teacherEditText.getText().toString();
        String newComments = commentsEditText.getText().toString();

        // Update the database with the new values
        DatabaseHelper db = new DatabaseHelper(this);
        db.updateInstance(instanceId, newCourseId, newDate, newTeacher, newComments);

        // Return to the previous activity
        finish();
    }
}