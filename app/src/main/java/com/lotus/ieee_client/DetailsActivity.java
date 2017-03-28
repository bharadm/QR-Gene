package com.lotus.ieee_client;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.lang.Override;
import java.lang.String;

public class DetailsActivity extends AppCompatActivity {
    EditText et[]=new EditText[3];
    String val[]=new String[2];
    String phone_number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_details);
        et[0]=(EditText)findViewById(R.id.et_nop);
        et[1]=(EditText)findViewById(R.id.et_pn);
        et[2]=(EditText)findViewById(R.id.et_pno);
    }
    public void bt_main(View v){
        val[0]=et[0].getText().toString();
        val[1]=et[1].getText().toString();
        phone_number=String.valueOf(et[2].getText().toString());
        Toast.makeText(DetailsActivity.this, ""+val[0]+"..."+val[1], Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, ListActivity.class)
                .putExtra("Name", val[0])
                .putExtra("Pass Number", val[1])
                .putExtra("Phone Number",phone_number));
        finish();

    }

}
