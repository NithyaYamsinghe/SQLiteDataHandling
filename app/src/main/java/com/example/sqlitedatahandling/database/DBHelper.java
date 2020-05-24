package com.example.sqlitedatahandling.database;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserInfo.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_ENTRIES = "CREATE TABLE " + UserMaster.Users.TABLE_NAME + " (" +
                UserMaster.Users._ID + " INTEGER PRIMARY KEY," +
                UserMaster.Users.COLUMN_NAME_USERNAME + " TEXT," +
                UserMaster.Users.COLUMN_NAME_PASSWORD + " TEXT)";


        // execute the content of sql create entries
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UserMaster.Users.TABLE_NAME);
    }

    // add info method
    public boolean addInfo(String username, String password) {

        // get the data repository in write mode
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        // create new map of values where column names are keys
        ContentValues values = new ContentValues();
        values.put(UserMaster.Users.COLUMN_NAME_USERNAME, username);
        values.put(UserMaster.Users.COLUMN_NAME_PASSWORD, password);

        // insert the new row and returning the primary key value of new row
        long newRowId = sqLiteDatabase.insert(UserMaster.Users.TABLE_NAME, null, values);

        if (newRowId == -1)
            return false;
        else
            return true;

    }

    // read all info method
    public List readAllInfo() {

        // get data repository in readable mode
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        String projection[] = {
                UserMaster.Users._ID,
                UserMaster.Users.COLUMN_NAME_USERNAME,
                UserMaster.Users.COLUMN_NAME_PASSWORD
        };

        // sort the result in descending order
        String sortOrder = UserMaster.Users.COLUMN_NAME_USERNAME + " DESC";

        Cursor cursor = sqLiteDatabase.query(
                UserMaster.Users.TABLE_NAME, // table to query
                projection, // columns to return
                null, // column for where clause
                null, // values for where clause
                null, // group by
                null, // filter by row group
                sortOrder // sort by
        );

        List userNames = new ArrayList<>();
        List passwords = new ArrayList<>();


        while (cursor.moveToNext()) {

            String username = cursor.getString(cursor.getColumnIndexOrThrow(UserMaster.Users.COLUMN_NAME_USERNAME));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(UserMaster.Users.COLUMN_NAME_PASSWORD));

            userNames.add(username);
            passwords.add(password);

        }
        cursor.close();
        return userNames;
    }

    // read one particular info
    public boolean readInfo(String username, String password) {

        // get data repository in readable mode
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        boolean result = false;

        String projection[] = {
                UserMaster.Users._ID,
                UserMaster.Users.COLUMN_NAME_USERNAME,
                UserMaster.Users.COLUMN_NAME_PASSWORD
        };

        Cursor cursor = sqLiteDatabase.query(
                UserMaster.Users.TABLE_NAME,
                projection,
              null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String u = cursor.getString(cursor.getColumnIndexOrThrow(UserMaster.Users.COLUMN_NAME_USERNAME));
            String p = cursor.getString(cursor.getColumnIndexOrThrow(UserMaster.Users.COLUMN_NAME_PASSWORD));

            if (u.equals(username) && p.equals(password))
                result = true;
        }
        cursor.close();
        return result;
    }

    // delete one particular info
    public boolean deleteInfo(String username) {

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();

        String selection = UserMaster.Users.COLUMN_NAME_USERNAME + " LIKE ?";
        String selectionArgs[] = {username};
        int result = sqLiteDatabase.delete(UserMaster.Users.TABLE_NAME, selection, selectionArgs);
        if (result == 1)
            return true;
        else
            return false;
    }

    // update one particular info
    public boolean updateInfo(String username, String password) {

        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserMaster.Users.COLUMN_NAME_PASSWORD, password);


        // which row to update
        String selection = UserMaster.Users.COLUMN_NAME_USERNAME + " LIKE ?";
        String selectionArgs[] = {username};


        int result = sqLiteDatabase.update(
                UserMaster.Users.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );

        if (result == 1)
            return true;
        else
            return false;
    }

}
