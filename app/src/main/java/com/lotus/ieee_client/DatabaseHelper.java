package com.lotus.ieee_client;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;import java.lang.Override;import java.lang.String;

/**
 * Created by Bharadwaj on 29/09/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DBPath="/data/data/com.lotus.ieee_client/databases/";
    private static String DBName= "ieeedg.db";
    public SQLiteDatabase sqLiteDatabase;
    private final Context mycontext;

    public DatabaseHelper(Context context) {
        super(context, DBName, null, 1);
        this.mycontext=context;
    }

    public void createdatabase() throws IOException {
        boolean dbExist=checkDatabase();
        if(dbExist){

        }else{
            this.getReadableDatabase();
            copyDatabase();
        }
    }

    private boolean checkDatabase() {
        SQLiteDatabase checkDB=null;
        try{
            String myPath=DBPath+DBName;
            checkDB=SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
        }catch (SQLiteException e){

        }
        if (checkDB!=null){
                checkDB.close();
        }
        return checkDB!=null;
    }

    private void copyDatabase() throws IOException{
        InputStream myInput=mycontext.getAssets().open(DBName);
        String OutFileName=DBPath+DBName;
        OutputStream myOutput=new FileOutputStream(OutFileName);
        byte[] buffer=new byte[1024];
        int length;
        while((length=myInput.read(buffer))>0){
            myOutput.write(buffer,0,length);
        }
        myOutput.flush();myOutput.close();myInput.close();
    }

    public void openDatabase() throws IOException{
        String mypath=DBPath+DBName;
        sqLiteDatabase=SQLiteDatabase.openDatabase(mypath,null,SQLiteDatabase.OPEN_READWRITE);
        Log.d("Successfully opened", mypath);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String eventCount_password(int x,String EventName,int y){
        if(x==0) {
            if(y==100) {
                Cursor cursor = sqLiteDatabase.rawQuery("select count(*) from Events;", null);
                if (cursor.moveToFirst()) {
                    return "" + String.valueOf(cursor.getInt(0));
                }
            }else {
                Cursor cursor=sqLiteDatabase.rawQuery("select * from Events;",null);
                if(cursor.moveToFirst()){
                    cursor.moveToPosition(y);
                    Log.d("Position "+y,"Data "+cursor.getString(0));
                    return cursor.getString(0);
                }
            }
        }else if (x==1){
            Cursor cursor=sqLiteDatabase.rawQuery("select Password from Events where Eventname='"+EventName+"';",null);
            if(cursor.moveToFirst()){
                return cursor.getString(0);
            }
        }else if(x==2){
            Cursor cursor=sqLiteDatabase.rawQuery("select Answer from Events where Eventname='"+EventName+"'",null);
            if(cursor.moveToFirst()){
                return cursor.getString(0);
            }
        }
        return null;
    }


    public void InsertAfterQR(String name,String pno,String passno,String answer,String eventname){
        String input=eventCount_password(2,eventname,0);
        //String input=Answer_Check(eventname);
        int score=ScoreCount(input,answer);
        sqLiteDatabase.execSQL("insert into info values('"+name+"','"+pno+"','"+passno+"','"+answer+"','"+score+"');");
    }
    int count=0;
    public int ScoreCount(String ins,String ans){
        for(int i=0;i<ans.length();i++){
            if(ins.charAt(i)==ans.charAt(i)){
                count++;
            }
        }
        return count;
    }
    public String Answer_Check(String eventname){
        Cursor cursor=sqLiteDatabase.rawQuery("select answer from EventAnswer where Eventname='"+eventname+"';",null);
        if(cursor.moveToFirst()){
            return cursor.getString(2);
        }
        return null;
    }
    public String EventQues(int x,String eventname,int y){
        //x =0 means all the events will be displayed
        //Eventname----Password----Answer
        Cursor cursor=sqLiteDatabase.rawQuery("select * from Events;",null);
        if(x==0){
            if(cursor.moveToFirst()){
                return cursor.getString(0);

            }else{

            }
        }else if(x==1){
            Cursor c=sqLiteDatabase.rawQuery("select * from Events where Eventname='"+eventname+"';",null);
            return c.getString(2);
        }
        return null;
    }

    public String Questions(String eventname,int i,int j){
        Cursor c=sqLiteDatabase.rawQuery("select qiz from Questions where ename='"+eventname+"';",null);
        if(c.moveToFirst()){
            c.moveToPosition(i);
            return c.getString(j);
        }else{
            return null;
        }
    }

}
