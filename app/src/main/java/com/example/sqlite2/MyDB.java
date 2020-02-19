package com.example.sqlite2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MyDB{

    private MyDatabaseHelper dbHelper;

    private SQLiteDatabase database;

    //TABLE Categoria
    //INTEGER idCategoria PRIMARY KEY
    //TEXT nom
    public final static String EMP_TABLE="MyEmployees"; // name of table

    public final static String EMP_ID="_id"; // id value for employee
    public final static String EMP_NAME="name";  // name of employee

    /**
     *
     * @param context
     */
    public MyDB(Context context){
        dbHelper = new MyDatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }


    public long createRecords(int id, String name){
        ContentValues values = new ContentValues();
        values.put(EMP_ID, id);
        values.put(EMP_NAME, name);
        return database.insert(EMP_TABLE, null, values);
    }

    public Cursor selectRecords() {
        String[] cols = new String[] {EMP_ID, EMP_NAME};
        Cursor mCursor = database.query(true, EMP_TABLE,cols,null
                , null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor; // iterate to get each value.
    }

    public void insertarDades(){
        String[] items = {"lorem", "ipsum", "dolor", "sit", "amet", "consectetuer", "adipiscing", "elit", "morbi", "vel", "ligula", "vitae", "arcu", "aliquet", "mollis", "etiam", "vel", "erat", "placerat", "ante", "porttitor", "sodales", "pellentesque", "augue", "purus"};
        for(int i = 0;i<items.length;i++){
            createRecords(i,items[i]);
        }
    }
}