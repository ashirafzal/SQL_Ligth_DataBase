package com.example.ashirafzal.databaseexam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etID, etFName, etLName;
    Button btnCreate, btnUpdate, btnRead, btnDelete;
    ListView lvDBData;
    ArrayList<String> listDBData = new ArrayList<String>();
    ArrayAdapter arrayAdpt;

    DB_Institute instituteDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instituteDB = new DB_Institute(this);

        etID = (EditText) findViewById(R.id.etID);
        etFName = (EditText) findViewById(R.id.etFName);
        etLName = (EditText) findViewById(R.id.etLName);

        btnCreate = (Button) findViewById(R.id.btnCreate);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnRead = (Button) findViewById(R.id.btnView);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        lvDBData = (ListView) findViewById(R.id.lvDBData);
        arrayAdpt = new ArrayAdapter(this, android.R.layout.simple_list_item_1,
                listDBData);
        lvDBData.setAdapter(arrayAdpt);
    }

    public void RegisterNewStudent(View v){
        String FName = etFName.getText().toString();
        String LName = etLName.getText().toString();

        try {
            instituteDB.open();
            instituteDB.newStudent(FName, LName);
            Toast.makeText(getApplicationContext(), "Student registered",
                    Toast.LENGTH_LONG).show();
            etFName.setText("");
            etLName.setText("");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            instituteDB.close();
        }
    }

    public void showAllRecords(View v){
        showAllRecordsList();
    }
    private void showAllRecordsList(){
        arrayAdpt.clear();
        try{
            instituteDB.open();
            arrayAdpt.add(instituteDB.getAllRecords());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            instituteDB.close();
        }
    }

    public void UpdateRecords(View v){
        int ID = Integer.parseInt(etID.getText().toString());
        String FName = etFName.getText().toString();
        String LName = etLName.getText().toString();

        try{
            instituteDB.open();
            instituteDB.updateRecords(ID, FName, LName);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            instituteDB.close();
        }

        showAllRecordsList();
    }

    public void DeleteRecord(View v){
        int ID = Integer.parseInt(etID.getText().toString());
        try{
            instituteDB.open();
            instituteDB.deleteRecord(ID);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            instituteDB.close();
        }

        showAllRecordsList();
    }

}