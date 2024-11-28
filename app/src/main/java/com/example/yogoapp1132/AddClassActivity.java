package com.example.yogoapp1132;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddClassActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editTextDay, editTextTime, editTextCapacity, editTextDuration, editTextPrice, editTextType, editTextDescription;
    Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        myDb = new DatabaseHelper(this);
        editTextDay = findViewById(R.id.editTextDay);
        editTextTime = findViewById(R.id.editTextTime);
        editTextCapacity = findViewById(R.id.editTextCapacity);
        editTextDuration = findViewById(R.id.editTextDuration);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextType = findViewById(R.id.editTextType);
        editTextDescription = findViewById(R.id.editTextDescription);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        buttonSubmit.setOnClickListener(v -> insertData());
    }

    private void insertData() {
        String day = editTextDay.getText().toString();
        String time = editTextTime.getText().toString();
        String capacityStr = editTextCapacity.getText().toString();
        String duration = editTextDuration.getText().toString();
        String price = editTextPrice.getText().toString();
        String type = editTextType.getText().toString();
        String description = editTextDescription.getText().toString();

        if (day.isEmpty() || time.isEmpty() || capacityStr.isEmpty() || duration.isEmpty() || price.isEmpty() || type.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        int capacity = Integer.parseInt(capacityStr);
        boolean isInserted = myDb.insertData(day, time, capacity, duration, price, type, description);
        if (isInserted) {
            Toast.makeText(this, "Data Inserted", Toast.LENGTH_LONG).show();
            finish(); // Close the activity and return to the main activity
        } else {
            Toast.makeText(this, "Data not Inserted", Toast.LENGTH_LONG).show();
        }
    }
}