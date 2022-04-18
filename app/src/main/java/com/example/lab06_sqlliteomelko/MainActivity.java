package com.example.lab06_sqlliteomelko;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText etKey;//text boxes
    EditText etContent;
    DB mydb;//object of database class
    String Errormes;
    Context con;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//acquire text boxes
        etKey = findViewById(R.id.etKey);
        etContent = findViewById(R.id.etContent);
        //create new or open database file "mybase.db" with version 1
        mydb = new DB (this, "mybase.db", null, 1);
        Errormes = getResources().getString(R.string.Errormes);
        con=this;
    }
    public void btnInsertOnClick(View v){
        String key = etKey.getText().toString();//get key and value strings
        String value = etContent.getText().toString();
        if (value.equals(Errormes))
        {
            Toast.makeText(con,"This value: "+value+" is illegal", Toast.LENGTH_SHORT).show();
            return;
        }
        else {
            if (!(mydb.DoInsert(key,value))){
                Toast.makeText(con,"Records with key "+ key +" is already exist", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }
    public void btnUpdateOnClick(View v){
        String key = etKey.getText().toString();//get key and value strings
        String value = etContent.getText().toString();
        if (!(mydb.DoUpdate(key,value))){
            Toast.makeText(con,"Records isn't exist", Toast.LENGTH_SHORT).show();
            return;
        }
    }
    public void btnSelectOnClick(View v){
        String key = etKey.getText().toString();//get key and value strings
        String value = mydb .DoSelect(key);//find value for that key in table
        etContent.setText(value);
    }
    public void btnDeleteOnClick(View v){
        DialogWindowDelete();
    }
    public void DialogWindowDelete(){ //created by Igor Omelko
        LayoutInflater myLayout = LayoutInflater.from(this);
        View dialogView = myLayout.inflate(R.layout.dialogwindow, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog dlg = builder.create();
        dlg.show();
        Button btnOK =dlg.findViewById(R.id.btnOk);
        Button btnCancel =dlg.findViewById(R.id.btnCancel);
        btnOK.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                String key = etKey.getText().toString();//get key and value strings
                if (!(mydb.DoDelete(key))){
                    Toast.makeText(con,"Records isn't exist", Toast.LENGTH_SHORT).show();
                    return;
                }
                etKey.setText("");
                etContent.setText("");
                dlg.cancel ();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                dlg.cancel ();
            }
        });
    }
}