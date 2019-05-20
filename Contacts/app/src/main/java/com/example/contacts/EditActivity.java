package com.example.contacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class EditActivity extends AppCompatActivity {

    public Button saveBtn;
    public Button returnBtn;
    public EditText nameEA;
    public EditText phoneEA;
    public EditText descriptionEA;
    public EditText categoryEA;

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
        categoryEA = findViewById(R.id.categoryFieldEA);

        nameEA.setText(MainActivity.contactToEdit.getName());
        phoneEA.setText(MainActivity.contactToEdit.getPhoneNumber());
        descriptionEA.setText(MainActivity.contactToEdit.getDescription());
        categoryEA.setText(MainActivity.contactToEdit.getCategory());

        returnBtn.setOnClickListener(onClick);
        saveBtn.setOnClickListener(onClick);
    }

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
                editedContact.setCategory(categoryEA.getText().toString());
                dbHelper.updateContact(editedContact);
                intent = new Intent(getApplicationContext(), MainActivity.class);
            }
            if (intent != null) {
                startActivity(intent);
            }
        }
    };
}
