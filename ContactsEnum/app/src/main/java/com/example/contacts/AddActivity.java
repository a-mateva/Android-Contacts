package com.example.contacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    public Button saveAA;
    public Button cancelAA;
    public EditText nameAA;
    public EditText descriptionAA;
    public EditText phoneAA;
    public Spinner spinnerAA;

    int itemPosition;
    ArrayList<String> categoriesList = new ArrayList<>();
    //public EditText categoryAA;

    DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        saveAA = findViewById(R.id.saveAA);
        cancelAA = findViewById(R.id.cancelAA);
        nameAA = findViewById(R.id.nameFieldAA);
        descriptionAA = findViewById(R.id.descriptionFieldAA);
        phoneAA = findViewById(R.id.phoneFieldAA);
        spinnerAA = findViewById(R.id.spinnerAA);

        for (Category item : Category.values()) {
            categoriesList.add(item.name());
        }

        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoriesList);
        spinnerAA.setAdapter(categoriesAdapter);

        saveAA.setOnClickListener(onClick);
        cancelAA.setOnClickListener(onClick);
        spinnerAA.setOnItemSelectedListener(onItemSelectedListener);
    }

    AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            itemPosition = position;
            spinnerAA.getItemAtPosition(itemPosition);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            Toast.makeText(getApplicationContext(), "Nothing selected", Toast.LENGTH_LONG);
        }
    };

    View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            if (v.getId() == R.id.cancelAA) {
                intent = new Intent(getApplicationContext(), MainActivity.class);
            } if (v.getId() == R.id.saveAA) {
                Contact c = new Contact();
                c.setName(nameAA.getText().toString());
                c.setPhoneNumber(phoneAA.getText().toString());
                c.setDescription(descriptionAA.getText().toString());
                c.setCategory(Enum.valueOf(Category.class, spinnerAA.getSelectedItem().toString()));
                
                db.addContact(c);
                Toast.makeText(AddActivity.this, "Contact added successfully!", Toast.LENGTH_LONG);
                intent = new Intent(getApplicationContext(), MainActivity.class);
            } if (intent != null) {
                startActivity(intent);
            }
        }
    };
}
