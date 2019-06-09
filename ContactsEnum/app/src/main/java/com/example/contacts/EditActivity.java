package com.example.contacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class EditActivity extends AppCompatActivity {

    public Button saveBtn;
    public Button returnBtn;
    public EditText nameEA;
    public EditText phoneEA;
    public EditText descriptionEA;
    public Spinner spinnerEA;

    ArrayList<String> categoriesList = new ArrayList<>();
    int categoryPosition;
    DatabaseHelper dbHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        saveBtn = findViewById(R.id.saveEA);
        returnBtn = findViewById(R.id.cancelEA);
        nameEA = findViewById(R.id.nameFieldEA);
        phoneEA = findViewById(R.id.phoneFieldEA);
        descriptionEA = findViewById(R.id.descriptionFieldEA);
        spinnerEA = findViewById(R.id.spinnerEA);

        for (Category item : Category.values()) {
            categoriesList.add(item.name());
        }

        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categoriesList);
        spinnerEA.setAdapter(categoriesAdapter);

        nameEA.setText(MainActivity.contactToEdit.getName());
        phoneEA.setText(MainActivity.contactToEdit.getPhoneNumber());
        descriptionEA.setText(MainActivity.contactToEdit.getDescription());
        for (int i = 0; i < categoriesList.size(); i++) {
            if (categoriesList.get(i) == MainActivity.contactToEdit.getCategory().name()) {
                spinnerEA.setSelection(i);
                break;
            }
        }

        returnBtn.setOnClickListener(onClick);
        saveBtn.setOnClickListener(onClick);
        spinnerEA.setOnItemSelectedListener(onItemSelectedListener);
    }

    AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            categoryPosition = position;
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
            if (v.getId() == R.id.cancelEA) {
                intent = new Intent(getApplicationContext(), MainActivity.class);
            } if (v.getId() == R.id.saveEA) {
                Contact editedContact = new Contact();
                editedContact.setId(MainActivity.contactToEdit.getId());
                editedContact.setName(nameEA.getText().toString());
                editedContact.setPhoneNumber(phoneEA.getText().toString());
                editedContact.setDescription(descriptionEA.getText().toString());
                editedContact.setCategory(Enum.valueOf(Category.class, spinnerEA.getSelectedItem().toString()));
                dbHelper.updateContact(editedContact);
                intent = new Intent(getApplicationContext(), MainActivity.class);
            }
            if (intent != null) {
                startActivity(intent);
            }
        }
    };
}
