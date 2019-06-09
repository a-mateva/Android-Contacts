package com.example.contacts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public ListView list;
    public Button addBtn;
    public Button editBtn;
    public Button deleteBtn;

    ArrayList<Contact> allContacts;
    ArrayList<String> contactsToString;
    public static Contact contactToEdit;
    int itemPosition;

    DatabaseHelper dbHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemPosition = -1;
        addBtn = findViewById(R.id.addBtnMA);
        deleteBtn = findViewById(R.id.deleteBtnMA);
        editBtn = findViewById(R.id.editBtnMA);
        list = findViewById(R.id.listMA);

        contactsToString = new ArrayList<>();
        allContacts = new ArrayList<>();

        allContacts = dbHelper.getAllContacts();

        for (Contact c : allContacts) {
            contactsToString.add(c.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(list.getContext(),
                android.R.layout.simple_list_item_single_choice, contactsToString);

        list.setAdapter(adapter);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        list.setOnItemClickListener(onItemClicked);

        addBtn.setOnClickListener(onAdd);
        deleteBtn.setOnClickListener(onDelete);
        editBtn.setOnClickListener(onEdit);
    }

    AdapterView.OnItemClickListener onItemClicked = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            itemPosition = position;
            list.getItemAtPosition(itemPosition);
        }
    };

    View.OnClickListener onAdd = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), AddActivity.class);
            startActivity(intent);
        }
    };

    View.OnClickListener onDelete = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (itemPosition < 0) {
                Toast.makeText(MainActivity.this, "You need to select a contact.",
                        Toast.LENGTH_LONG).show();
            } else {
                dbHelper.deleteContact(allContacts.get(itemPosition));
                finish();
                startActivity(getIntent());
            }
        }
    };

    View.OnClickListener onEdit = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            if (v.getId() == R.id.editBtnMA) {
                if (itemPosition >= 0){
                    contactToEdit = allContacts.get(itemPosition);
                    intent = new Intent(MainActivity.this, EditActivity.class);
                } else {
                    Toast.makeText(getApplicationContext(), "You need to select a contact.",
                            Toast.LENGTH_LONG).show();
                }
            } if (intent != null) {
                startActivity(intent);
            }
        }
    };
}
