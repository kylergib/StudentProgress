package com.kgibs87.studentprogress;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String debugTag = "Debug";

    private EditText nameEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //assign widgets to fields
        nameEditText = findViewById(R.id.nameEditText);
    }

    public void submitName(View view) {
        //get text in name edit text
        String nameEntered = nameEditText.getText().toString();
        if (nameEntered.equals("")) Toast.makeText(MainActivity.this, "Name cannot be blank.", Toast.LENGTH_LONG).show();
        Log.d(debugTag, nameEntered);
    }
}