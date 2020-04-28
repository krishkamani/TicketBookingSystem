package com.example.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper {
    public static final String DATABASE_NAME = "ticketdetails";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "candidatedetails";
    public static final String KEY_ID = "_id";
    public static final String COL_NAME = "Name";
    public static final String COL_NAME1 = "Age";
    public static final String COL_NAME2 = "Mobile_No";
    public static final String COL_NAME3 = "Ticket_Type";
    public static final String COL_NAME4 = "Service_Type";
    public static final String COL_NAME5 = "Departure";
    public static final String COL_NAME6 = "Arrival";
    public static final String COL_NAME7 = "Date";
    public static final String COL_NAME8 = "Time";
    public static final int COL_INDEX = 1;

    private static final String DB_CREATE;

    static {
        DB_CREATE = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY autoincrement, "
                + COL_NAME + " TEXT NOT NULL,"
                + COL_NAME1 + " INTEGER NOT NULL,"
                + COL_NAME2 + " INTEGER NOT NULL,"
                + COL_NAME3 + " TEXT NOT NULL,"
                + COL_NAME4 + " TEXT NOT NULL,"
                + COL_NAME5 + " TEXT NOT NULL,"
                + COL_NAME6 + " TEXT NOT NULL,"
                + COL_NAME7 + " TEXT NOT NULL,"
                + COL_NAME8 + " TEXT NOT NULL" + ")";
    }

    private SQLiteDatabase database;
    private final Context context;
    private MyDBAdapter helper;

    public DBHelper(Context context)
    {
        this.context = context;
        helper = new MyDBAdapter(context,DATABASE_NAME,
                null, DATABASE_VERSION);
    }
    public DBHelper open()
    {
        database = helper.getWritableDatabase();
        //Create and/or open a database that will be used for
        // reading and writing.
        return this;
    }

    public void close()
    {
        database.close();
    }

    public long insertEntry(String Name, Integer Age, Long MobileNo, String TicketType, String ServiceType, String Depart, String Arrive, String Date, String Time) {

        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_NAME,Name);
        contentValues.put(COL_NAME1,Age);
        contentValues.put(COL_NAME2,MobileNo);
        contentValues.put(COL_NAME3,TicketType);
        contentValues.put(COL_NAME4,ServiceType);
        contentValues.put(COL_NAME5,Depart);
        contentValues.put(COL_NAME6,Arrive);
        contentValues.put(COL_NAME7,Date);
        contentValues.put(COL_NAME8,Time);
        return database.insert(TABLE_NAME,
                null, contentValues);
        /*nullColumnHack:
        nullColumnHack optional; may be null.
         SQL doesn't allow inserting a completely
         empty row without naming at least one column name.
         If your provided values is empty,
         no column names are known and
         an empty row can't be inserted.
         If not set to null, the nullColumnHack parameter
         provides the name of nullable column name
         to explicitly insert a NULL into in the case
         where your values is empty.
         For example, you want to insert an empty row into
         a table student(id, name), which id is auto generated
         and name is null. You could invoke like this:
            ContentValues cv = new ContentValues();
            db.insert("student", "name", cv);
         */
    }
    public boolean removeEntry(long rowIndex) {
        System.out.print(rowIndex);
        return database.delete(TABLE_NAME,
                KEY_ID+" = "+rowIndex,
                null)>0;
    }

    public Cursor getAllEntries() {
        return database.query(TABLE_NAME,
                new String[]{KEY_ID,COL_NAME,COL_NAME1,COL_NAME2,COL_NAME3,COL_NAME4,COL_NAME5,COL_NAME6,COL_NAME7,COL_NAME8},
                null, null,
                null, null, null);
    }


    public static class MyDBAdapter extends SQLiteOpenHelper
    {

        public MyDBAdapter(Context context, String name,
                           SQLiteDatabase.CursorFactory factory,
                           int version) {
            super(context, name, factory, version);
            //Create a helper object to create,
            // open, and/or manage a database.
            // The reason of passing null is you want the standard SQLiteCursor behaviour.
            // If you want to implement a specialized Cursor you can get it by by extending the Cursor class( this is for doing additional operations on the query results).
            // And in these cases, you can use the CursorFactory class to return an instance of your Cursor implementation.
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);
            //Called when the database is created
            // for the first time.
        }
        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion,
                              int newVersion) {
            Log.w("Updation", "Data base version is being updates");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}


