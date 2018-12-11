package com.database.sqliteapp;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    Button btn_insert_data, btn_view_data, btn_update_data, btn_delete_data;
    EditText text_name, text_address, text_age, text_type, text_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        btn_insert_data =(Button) findViewById(R.id.btn_add);
        btn_view_data = (Button) findViewById(R.id.btn_view);
        btn_update_data = (Button) findViewById(R.id.btn_update);
        btn_delete_data = (Button) findViewById(R.id.btn_delete);

        text_name = (EditText) findViewById(R.id.editTextName);
        text_address = (EditText) findViewById(R.id.editTextAddress);
        text_age = (EditText) findViewById(R.id.editTextAge);
        text_type = (EditText) findViewById(R.id.editTextType);
        text_id = (EditText) findViewById(R.id.editTextID);




        btn_insert_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             boolean isinserted = myDb.insertData(text_name.getText().toString(), text_address.getText().toString(),
                        text_age.getText().toString(),text_type.getText().toString());

             if(isinserted == true) {
                 Toast.makeText(MainActivity.this, "Data inserted", Toast.LENGTH_SHORT).show();
             }
             else{
                 Toast.makeText(MainActivity.this, "Data not inserted", Toast.LENGTH_SHORT).show();
             }
            }
        });

        btn_view_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cur = myDb.getAllData();

                if(cur.getCount() == 0){

                    showMessage("Error", "No Data Found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();

                while(cur.moveToNext()){

                    buffer.append("ID: "+cur.getString(0)+"\n");
                    buffer.append("NAME: "+cur.getString(1)+"\n");
                    buffer.append("ADDRESS: "+cur.getString(2)+"\n");
                    buffer.append("AGE: "+cur.getString(3)+"\n");
                    buffer.append("TYPE: "+cur.getString(4)+"\n");
                }

                showMessage("Data", buffer.toString());
            }
        });

        btn_update_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               boolean isUpdate = myDb.updateData(text_id.getText().toString(), text_name.getText().toString(),
                                 text_address.getText().toString(), text_age.getText().toString(), text_type.getText().toString());


               if(isUpdate == true){

                   Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_SHORT).show();
               }
               else{
                   Toast.makeText(MainActivity.this, "Data Not Updated", Toast.LENGTH_SHORT).show();
               }
            }
        });

        btn_delete_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer isDelet = myDb.deleteData(text_id.getText().toString());
                if(isDelet>0){
                    Log.d("hi", "hello");
                    Toast.makeText(MainActivity.this, "Data Deleted", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Data not Deleted", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    public void showMessage(String title, String Message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
