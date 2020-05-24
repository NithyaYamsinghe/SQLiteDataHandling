package com.example.sqlitedatahandling;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sqlitedatahandling.database.DBHelper;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    Button selectAll, add, signIn, delete, update;
    EditText editUsername, editPassword;
    DBHelper userDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userDb = new DBHelper(this);
        SQLiteDatabase db = userDb.getReadableDatabase();

        // hooks
        selectAll = findViewById(R.id.buttonSelectAll);
        add = findViewById(R.id.buttonAdd);
        signIn = findViewById(R.id.buttonSignIn);
        delete = findViewById(R.id.buttonDelete);
        update = findViewById(R.id.buttonUpdate);
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);


        // select all
        selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List userInfo = userDb.readAllInfo();

                if (!userInfo.isEmpty()) {
                    Toast.makeText(MainActivity.this, "users are displayed as a log message", Toast.LENGTH_SHORT).show();

                    for (Object name : userDb.readAllInfo()) {

                        String value = (String) name;
                        Log.i("Username", value);
                    }
                } else
                    Toast.makeText(MainActivity.this, "no users in the database ", Toast.LENGTH_SHORT).show();
            }
        });

        // add
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editUsername.getText().toString();
                String password = editPassword.getText().toString();

                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                    boolean status = userDb.addInfo(username, password);

                    if (status) {
                        Toast.makeText(MainActivity.this, "data inserted successfully", Toast.LENGTH_SHORT).show();
                        editUsername.setText("");
                        editPassword.setText("");
                    } else {
                        Toast.makeText(MainActivity.this, "error occurred while data inserting", Toast.LENGTH_SHORT).show();
                        editUsername.setText("");
                        editPassword.setText("");
                    }
                } else
                    Toast.makeText(MainActivity.this, "all fields are required", Toast.LENGTH_SHORT).show();
            }
        });

        // sign in
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editUsername.getText().toString();
                String password = editPassword.getText().toString();

                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {

                    boolean status = userDb.readInfo(username, password);
                    if (status) {
                        Toast.makeText(MainActivity.this, "sign in successfully", Toast.LENGTH_SHORT).show();
                        editUsername.setText("");
                        editPassword.setText("");
                    } else {
                        Toast.makeText(MainActivity.this, "error occurred while sign in", Toast.LENGTH_SHORT).show();
                        editUsername.setText("");
                        editPassword.setText("");
                    }
                } else {
                    Toast.makeText(MainActivity.this, "all fields are required", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // delete
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editUsername.getText().toString();


                if (!TextUtils.isEmpty(username)) {
                    boolean status = userDb.deleteInfo(username);

                    if (status) {
                        Toast.makeText(MainActivity.this, "data deleted successfully", Toast.LENGTH_SHORT).show();
                        editUsername.setText("");

                    } else {
                        Toast.makeText(MainActivity.this, "error occurred while data deleting", Toast.LENGTH_SHORT).show();
                        editUsername.setText("");
                    }
                } else
                    Toast.makeText(MainActivity.this, "username field is required", Toast.LENGTH_SHORT).show();

            }
        });

        // update
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editUsername.getText().toString();
                String password = editPassword.getText().toString();

                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                    boolean status = userDb.updateInfo(username, password);

                    if (status) {
                        Toast.makeText(MainActivity.this, "data updated successfully", Toast.LENGTH_SHORT).show();
                        editUsername.setText("");
                        editPassword.setText("");
                    } else {
                        Toast.makeText(MainActivity.this, "error occurred while data updating", Toast.LENGTH_SHORT).show();
                        editUsername.setText("");
                        editPassword.setText("");
                    }
                } else
                    Toast.makeText(MainActivity.this, "all fields are required", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
