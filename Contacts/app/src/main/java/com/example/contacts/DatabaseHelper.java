package com.example.contacts;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "contacts.db";

    SQLiteDatabase db;

    private static final String TABLE_CONTACTS = "Contacts";

    private static final String CONTACT_ID = "id";
    private static final String NAME = "name";
    private static final String PHONENUMBER = "phoneNumber";
    private static final String DESCRIPTION = "description";
    private static final String CATEGORY = "category";

    private static final String CREATE_TABLE_CONTACTS =
            "CREATE TABLE " + TABLE_CONTACTS +
                    "(" + CONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    NAME + " VARCHAR NOT NULL, " + PHONENUMBER + " VARCHAR NOT NULL, " +
                    DESCRIPTION + " VARCHAR(255) " + ");";




    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_CONTACTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_CONTACTS);
        onCreate(sqLiteDatabase);
    }

    /*public ArrayList<Contact> addMockDataOnStartup() {
        Contact c1 = new Contact("Evelyn Harlow", "0896645120", "description 1", Category.FAMILY);
        Contact c2 = new Contact("James Bond", "007", "i have many guns", Category.WORK);
        Contact c3 = new Contact("Harry Potter", "031151562", "expelliarmus", Category.FRIENDS);

        ArrayList<Contact> list = new ArrayList<>();
        list.add(c1);
        list.add(c2);
        list.add(c3);

        for (Contact item : list) {
            addContact(item);
        }
        return list;
    }*/

    public void addContact(Contact contact) {
        try {
            db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(NAME, contact.getName());
            cv.put(DESCRIPTION, contact.getDescription());
            cv.put(PHONENUMBER, contact.getPhoneNumber());
            cv.put(CATEGORY, contact.getCategory());
            //cv.put(CATEGORY, contact.getCategory().name());

            db.insertOrThrow(TABLE_CONTACTS, null, cv);
        } catch(SQLiteException e){
            Log.e("SQL Insert Error", e.getMessage());
        } finally {
            if (db != null){
                db.close();
            }
        }
    }

    public void updateContact(Contact contact) {
        try {
            db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(NAME, contact.getName());
            cv.put(DESCRIPTION, contact.getDescription());
            cv.put(PHONENUMBER, contact.getPhoneNumber());
            cv.put(CATEGORY, contact.getCategory());
            //cv.put(CATEGORY, contact.getCategory().name());

            db.update(TABLE_CONTACTS, cv, CONTACT_ID + " = " + contact.getId(), null);

        } catch(SQLiteException e) {
            Log.e("SQL Update Error", e.getMessage());
        } finally {
            if (db != null){
                db.close();
            }
        }
    }

    public ArrayList<Contact> getAllContacts() {
        ArrayList<Contact> contacts = new ArrayList<>();
        try {
            db = getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_CONTACTS;
            Cursor c = db.rawQuery(query, null);

            while (c.moveToNext()) {
                Contact contact = new Contact();
                contact.setId(c.getInt(c.getColumnIndex(CONTACT_ID)));
                contact.setName(c.getString(c.getColumnIndex(NAME)));
                contact.setPhoneNumber(c.getString(c.getColumnIndex(PHONENUMBER)));
                contact.setDescription(c.getString(c.getColumnIndex(DESCRIPTION)));
                contact.setCategory(c.getString(c.getColumnIndex(CATEGORY)));
                //contact.setCategory(Enum.valueOf(Category.class, c.getString(c.getColumnIndex(CATEGORY))));

                contacts.add(contact);
            }

        } catch (SQLException e) {
            Log.e("SQL Retrieve-All Error", e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return contacts;
    }

    public Contact getContact(int id) {
        Contact contact = new Contact();
        try {
            db = getReadableDatabase();
            String query = "SELECT * FROM " + TABLE_CONTACTS +
                    " WHERE " + CONTACT_ID + " = " + id;

            Cursor c = db.rawQuery(query, null);

            if (c != null) {
                c.moveToFirst();
                contact.setId(c.getInt(c.getColumnIndex(CONTACT_ID)));
                contact.setName(c.getString(c.getColumnIndex(NAME)));
                contact.setPhoneNumber(c.getString(c.getColumnIndex(PHONENUMBER)));
                contact.setDescription(c.getString(c.getColumnIndex(DESCRIPTION)));
                contact.setCategory(c.getString(c.getColumnIndex(CATEGORY)));
                //contact.setCategory(Enum.valueOf(Category.class, c.getString(c.getColumnIndex(CATEGORY))));
            }
        } catch(SQLiteException e) {
            Log.e("SQL Retrieve Error", e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return contact;
    }

    public void deleteContact(Contact contact) {
        try {
            db = getWritableDatabase();
            db.delete(TABLE_CONTACTS, CONTACT_ID + " = " + contact.getId(), null);
        } catch(SQLiteException e) {
            Log.e("SQL Delete Error", e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }
}
