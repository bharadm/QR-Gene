package com.lotus.ieee_client;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.io.IOException;

public class ListActivity extends AppCompatActivity {
    EditText et_pass;
    GridLayout gridLayout;
    DatabaseHelper databaseHelper;
    LinearLayout.LayoutParams layoutParams;
    String name,pass_number;
    String phone_number;
    Button buttons[][]=new Button[12][2];
    ActionBar action;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_list);
        et_pass=(EditText)findViewById(R.id.et_password);
        name=getIntent().getStringExtra("Name");
        pass_number=getIntent().getStringExtra("Pass Number");
        phone_number=getIntent().getStringExtra("Phone Number");
        databaseHelper=new DatabaseHelper(this);
        Toast.makeText(ListActivity.this, "1"+name, Toast.LENGTH_SHORT).show();
        Toast.makeText(ListActivity.this, "2"+pass_number, Toast.LENGTH_SHORT).show();

//        action.setTitle("Welcome "+name);
        layoutParams=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        gridLayout=(GridLayout)findViewById(R.id.gridlayout_list);
        //String event[]=new String[]{"readyaimfire","something"};
        assert gridLayout != null;
        gridLayout.setColumnCount(2);
        try{
            databaseHelper.createdatabase();
        }catch (IOException ioe){
            throw new Error("Unable to create database");
        }
        try {
            databaseHelper.openDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        int rowcount=Integer.parseInt(databaseHelper.eventCount_password(0,"nothing",100));
        String event[]=new String[rowcount];
        gridLayout.setRowCount(rowcount);
        for(int i=0;i<rowcount;i++){
            event[i]=databaseHelper.eventCount_password(0,"nothing",i);
        }
        for(int i=0,k=0;i<rowcount;i++,k++){
                buttons[i][0]=new Button(this);
                String du=event[i];
                buttons[i][0].setText(""+du);
                buttons[i][0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button) v;
                    b.setBackgroundColor(R.color.black);
                    Onclick_button(b.getText().toString());
                    }
                });
                buttons[i][0].setLayoutParams(layoutParams);
                gridLayout.addView(buttons[i][0]);
        }
    }
    public void Onclick_button(String Event_Name1){
        String password=et_pass.getText().toString();
        password=password.toLowerCase();
        if(!password.isEmpty()){
            if(password.equals(databaseHelper.eventCount_password(1,Event_Name1,0))){
                startActivity(new Intent(this,Main2Activity.class)
                        .putExtra("Name",name)
                        .putExtra("Pass Number",pass_number)
                        .putExtra("Phone Number",phone_number)
                        .putExtra("Event Name",Event_Name1));
                Toast.makeText(ListActivity.this, ""+Event_Name1, Toast.LENGTH_SHORT).show();
                finish();
            }else{
                Toast.makeText(ListActivity.this, "Check your password dude", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(ListActivity.this, "Enter the password :", Toast.LENGTH_SHORT).show();
        }
    }
    /*
    public void credits(){
    Retro badges collection with yellow ribbons Free Vector By yuriy_nako / Freepik
    Ribbons and badges Free Vector in BannersRibbons  By freepik

    */
}
