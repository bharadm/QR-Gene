package com.lotus.ieee_client;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.IOException;import java.lang.Error;import java.lang.Exception;import java.lang.Override;import java.lang.String;

public class Main2Activity extends AppCompatActivity{
    DatabaseHelper databaseHelper;
    GridLayout linearLayout;
    RelativeLayout getLinearLayout;
    GridLayout gridLayout;
    String ques[]=new String[5];
    char answers[]=new char[]{'a','b','c','d'};
    LinearLayout.LayoutParams layot;
    Button button[]=new Button[5];
    String Event_Name1;
    String name,pass_number,phone_number;
    Button button2;
    Button button1[][]=new Button[5][4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main2);
        getLinearLayout=(RelativeLayout)findViewById(R.id.relative_layout_co);
        gridLayout=(GridLayout)findViewById(R.id.relative_layout_grid);
        name = getIntent().getStringExtra("Name");
        pass_number=getIntent().getStringExtra("Pass Number");
        phone_number=getIntent().getStringExtra("Phone Number");
        Event_Name1=getIntent().getStringExtra("Event Name");
        Toast.makeText(Main2Activity.this, "In Next Activity "+Event_Name1, Toast.LENGTH_SHORT).show();
        layot=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        databaseHelper=new DatabaseHelper(this);
        button2=new Button(this);
        button2.setVisibility(View.INVISIBLE);
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
        //ques=databaseHelper.Questions("readyaimfire",2,1);
        for(int i=0;i<5;i++){
            button[i]=new Button(this);
            button[i].setText("Question "+(i+1)+" :");
            ques[i]=databaseHelper.Questions(Event_Name1,i,0);
            Toast.makeText(Main2Activity.this, "Question : "+ques[i], Toast.LENGTH_SHORT).show();
            button[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Button b = (Button) v;
                    Question_display(b.getText().toString());
                }
            });
            button[i].setLayoutParams(layot);

            gridLayout.addView(button[i]);
            for (int j=0;j<4;j++){
//                linearLayout.addView(button[i]);
                button1[i][j]=new Button(this);
                button1[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Answer_display(v.getId());
                    }
                });
                button1[i][j].setId(((i + 1) * 10) + j + 1);
                //Toast.makeText(Main2Activity.this, ""+String.valueOf(((i + 1) * 10) + j+1), Toast.LENGTH_SHORT).show();
                button1[i][j].setText("" + answers[j]);
                gridLayout.addView(button1[i][j]);
            }
        }

    }

    int clicked[]=new int[]{0,0,0,0,0};
    public void Answer_display(int id1){
        //Toast.makeText(Main2Activity.this, "ID is "+String.valueOf(id1), Toast.LENGTH_SHORT).show();
        Button b=(Button)findViewById(id1);
        assert b != null;
        int x=(id1/10)-1;
        if (clicked[x] != 0) {
                    Button z = (Button) findViewById(clicked[x]);
                    if (z != null) {
                        //Toast.makeText(Main2Activity.this, "Old id "+String.valueOf(clicked[x]), Toast.LENGTH_SHORT).show();
                        z.setBackgroundColor(R.color.black);
                    }
                }
                b.setBackgroundColor(R.color.white);
                clicked[x]=id1;
                Toast.makeText(Main2Activity.this, "New id "+String.valueOf(id1), Toast.LENGTH_SHORT).show();

    }
    public void Question_display(String quest){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Question");
        for(int i=0;i<5;i++){
            if(quest.equals("Question "+(i+1)+" :")){
                alert.setMessage(ques[i]);
                break;
            }
        }
        alert.show();
        alert.setCancelable(true);

    }
    String result;
    public void Submit_1(View view){
        result="";
        for(int i=0;i<5;i++){
            int l=clicked[i]%10;
            if(l==1){
                result+='a';
            }else if(l==2){
                result+='b';
            }else if(l==3){
                result+='c';
            }else if(l==4){
                result+='d';
            }
        }
        Toast.makeText(Main2Activity.this, ""+result, Toast.LENGTH_SHORT).show();
        Generate_answer(name+'%'+phone_number+'%'+pass_number+'%'+result+'%'+Event_Name1+'#');
    }
    public void Generate_answer(String ans){
        MultiFormatWriter multiformat=new MultiFormatWriter();
        try{
            BitMatrix bitmatrix=multiformat.encode(ans, BarcodeFormat.QR_CODE,400,400);
            BarcodeEncoder barcode=new BarcodeEncoder();
            Bitmap bitmap=barcode.createBitmap(bitmatrix);
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("Kindly show this to Organiser");
            //LayoutInflater factory=LayoutInflater.from(Main2Activity.this);
            ImageView imageView=new ImageView(this);
            imageView.setImageBitmap(bitmap);
            builder.setView(imageView);
            builder.create();
            builder.show();
        }catch (Exception e){
            Toast.makeText(Main2Activity.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
