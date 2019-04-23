package ua.lpnu.android.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ua.lpnu.android.pojo.Contact;

public class Helper extends SQLiteOpenHelper {

    public static final String DB_NAME = "fefderline.db"; // Імя бази даних
    private static final int DB_VERSION = 33; // Версія бази даних

    public Helper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Person.table + " (" + Person.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Person.PHONE_ID + " INTEGER, "
                + Person.FIRST_NAME + " TEXT, "
                + Person.SECOND_NAME + " TEXT); ");
        db.execSQL("CREATE TABLE " + Phone.table + " (" + Phone.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Phone.TYPE + " INTEGER, "
                + Phone.NUMBER + " TEXT); ");

    }

    public long insertPerson(SQLiteDatabase db,int idPhone ,String nameFirst, String nameSecond) {
        ContentValues values = new ContentValues();
        values.put(Person.PHONE_ID, idPhone);
        values.put(Person.FIRST_NAME, nameFirst);
        values.put(Person.SECOND_NAME, nameSecond);
        return db.insert(Person.table, null, values);
    }

    public long insertPhone(SQLiteDatabase db, String number, @Contact.TypePhone int type) {
        ContentValues values = new ContentValues();
        values.put(Phone.NUMBER, number);
        values.put(Phone.TYPE, type);
        return db.insert(Phone.table, null, values);
    }



    public boolean deletePerson(SQLiteDatabase db, int id) {
        return db.delete(Person.table, Person.ID + "=" + id, null) > 0;
    }

    public boolean deletePhone(SQLiteDatabase db, int id) {
        return db.delete(Phone.table, Phone.ID + "=" + id, null) > 0;
    }


    public void updatePhone(SQLiteDatabase db, int id, String newNumber, @Contact.TypePhone int newType) {
        ContentValues cv = new ContentValues();
        cv.put(Phone.NUMBER, newNumber);
        cv.put(Phone.TYPE, newType);
        db.update(Phone.table, cv,  Phone.ID + "=" + id,null) ;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
