package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {
    Button btnluu,btndong;
    EditText tieude;
    EditText noidung;
    String date;
    String keySearch="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        btnluu=findViewById(R.id.button);
        btndong=findViewById(R.id.button2);
        tieude=findViewById(R.id.tieude);
        noidung=findViewById(R.id.noidug);
        DateFormat df=new SimpleDateFormat("yyyy.MM.dd");
        date= df.format(Calendar.getInstance().getTime());
        Intent Rintent=getIntent();
        String Action= Rintent.getAction();
        if(Action=="update"){
            GhiChu ghiChu=(GhiChu) Rintent.getExtras().get("ghiChu");
            keySearch=ghiChu.getTieude();
            tieude.setText(ghiChu.getTieude());
            noidung.setText(ghiChu.getNoidung());
        }
        btnluu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLuu=new Intent();
                intentLuu.putExtra("tieude",tieude.getText().toString());
                intentLuu.putExtra("ngaythang",date);
                intentLuu.putExtra("noidung",noidung.getText().toString());
                if(Action=="update"){
                    intentLuu.putExtra("key",keySearch);
                }
                setResult(RESULT_OK,intentLuu);
                finish();
            }
        });
        btndong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}