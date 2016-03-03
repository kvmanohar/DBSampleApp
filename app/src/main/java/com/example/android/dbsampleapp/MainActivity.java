package com.example.android.dbsampleapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public DBHelper dbHelper;

    public EditText etID;
    public EditText etFirstName;
    public EditText etLastName;
    public EditText etAddress;
    public EditText etSalary;

    public Button btnInsert;
    public Button btnDelete;
    public Button btnUpdate;
    public Button btnLoad;

    public ListView lvDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);
        dbHelper.OpenDB();
        initialiseLayout();
        populateListView();
    }

    public void initialiseLayout(){
        etID = (EditText) findViewById(R.id.etID);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etSalary = (EditText) findViewById(R.id.etSalary);

        btnInsert = (Button) findViewById(R.id.bt_insert);
        btnDelete = (Button) findViewById(R.id.bt_delete);
        btnUpdate = (Button) findViewById(R.id.bt_update);
        btnLoad = (Button) findViewById(R.id.bt_load);

        lvDisplay = (ListView) findViewById(R.id.lv_display);

        btnInsert.setOnClickListener(dbButtonListener);
        btnDelete.setOnClickListener(dbButtonListener);
        btnUpdate.setOnClickListener(dbButtonListener);
        btnLoad.setOnClickListener(dbButtonListener);

    }

    public View.OnClickListener dbButtonListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_insert:
                    long result = dbHelper.insertTableRow(-1,getValue(etFirstName),getValue(etLastName),getValue(etAddress),Double.valueOf(getValue(etSalary)));
                    if (result == -1 ){
                        Toast.makeText(getApplicationContext(),"Error inserting row",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Record inserted successfully",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.bt_update:
                    break;
                case R.id.bt_delete:
                    break;
                case R.id.bt_load:
                    break;
            }
        }
    };

    private void populateListView(){
        Cursor cursor;
        cursor = dbHelper.getAllRecords();

        /* The desired Columns to display */
        String[] columns = new String[]{
            dbHelper.FULL_NAME,dbHelper.ADDRESS,dbHelper.SALARY};

        /*XML Defined view with the data will be bound to*/
        int to[] = new int[]{
                R.id.tv_fname_lname,
                R.id.tv_address,
                R.id.tv_salary};

        SimpleCursorAdapter dataAdapter = new SimpleCursorAdapter(getApplicationContext(),R.layout.db_record_row,cursor,columns,to,0);
        lvDisplay.setAdapter(dataAdapter);
    }

    private String getValue (EditText et){
         return et.getText().toString().trim();

    }

    protected void onStart() {
        super.onStart();
        dbHelper.OpenDB();
    }

    @Override
    protected void onStop() {
        super.onStop();
        dbHelper.CloseDB();
    }
}
