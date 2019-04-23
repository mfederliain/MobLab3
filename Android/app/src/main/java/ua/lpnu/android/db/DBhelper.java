package ua.lpnu.android.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.List;

import ua.lpnu.android.pojo.Contact;
import ua.lpnu.android.pojo.TPhone;

public class DBhelper {
    public static final String TAG = "DbAdapter";
    private static SQLiteDatabase db;
    private Helper helper;
    private static DBhelper adapter = null;

    public static synchronized DBhelper getInstance(Context contextApp) {


        if (adapter == null) {
            adapter = new DBhelper(contextApp.getApplicationContext());
        }
        return adapter;
    }

    private DBhelper(Context context) {
        try {
            helper = new Helper(context);
            db = helper.getWritableDatabase();


        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    public static SQLiteDatabase getDb() {
        return db;
    }

    public Helper getHelper() {
        return helper;
    }

    public static DBhelper getAdapter() {
        return adapter;
    }

    public void inserrUserPhone(int idPhone, String name, String surname) {
        helper.insertPerson(db, idPhone, name, surname);
    }

    public void inserrUserPhone(String number, int type, String name, String surname) {
        long id = helper.insertPhone(db, number, type);
        helper.insertPerson(db, (int) id, name, surname);
    }

    public ArrayList<TPhone> getUnicPhones() {

        Cursor cursor = db.query(Phone.table,
                new String[]{Phone.ID,
                        Phone.NUMBER,
                        Phone.TYPE},
                null,
                null,
                Phone.NUMBER, null, null);
        if (cursor == null) return null;
        ArrayList<TPhone> tPhones = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String number = cursor.getString(1);
                int type = cursor.getInt(2);

                tPhones.add(new TPhone(number, type, id));
            } while (cursor.moveToNext());

            cursor.close();
        }
        return tPhones;

    }

    public ArrayList<Contact> getContacts() {
        final String MY_QUERY = "SELECT Person.id, " +
                "Person.first, " +
                "Person.second, " +
                "Phone.number, " +
                "Phone.TYPE, " +
                "Phone.id " +
                "  FROM Person INNER JOIN Phone  ON Person.id_phone=Phone.id ";

        Cursor cursor = db.rawQuery(MY_QUERY, null);
        if (cursor == null) return null;
        ArrayList<Contact> contacts = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String surname = cursor.getString(2);
                String number = cursor.getString(3);
                int type = cursor.getInt(4);
                int idPhone = cursor.getInt(5);
                contacts.add(new Contact(id, idPhone, name, surname, number, type));

            } while (cursor.moveToNext());

            cursor.close();
        }
        return contacts;

    }

    public void deletePerson(int id){
        helper.deletePerson(db,id);
    }
}
