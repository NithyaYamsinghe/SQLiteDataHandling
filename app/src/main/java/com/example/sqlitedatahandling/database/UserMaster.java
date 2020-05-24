package com.example.sqlitedatahandling.database;

import android.provider.BaseColumns;

public final class UserMaster {
    private UserMaster() {
    }


    // inner class that defines the table content
    public class Users implements BaseColumns {

        public static final String TABLE_NAME = "users";
        public static final String COLUMN_NAME_USERNAME = "username";
        public static final String COLUMN_NAME_PASSWORD = "password";


    }
}
