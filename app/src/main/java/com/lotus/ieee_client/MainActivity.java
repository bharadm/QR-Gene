package com.lotus.ieee_client;

import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=(EditText)findViewById(R.id.et_text);
        imageView=(ImageView)findViewById(R.id.iv_qr);

    }
    public void submit(View view){
        MultiFormatWriter multiformat=new MultiFormatWriter();
        try{
            BitMatrix bitmatrix=multiformat.encode(editText.getText().toString(),BarcodeFormat.QR_CODE,200,200);
            BarcodeEncoder barcode=new BarcodeEncoder();
            Bitmap bitmap=barcode.createBitmap(bitmatrix);
            imageView.setImageBitmap(bitmap);
        }catch (Exception e){
            Toast.makeText(MainActivity.this, ""+e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

}

