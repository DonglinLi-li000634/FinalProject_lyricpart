package com.example.finalproject_lyricpart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button getIn=findViewById(R.id.getIn);
        if(getIn!=null){
            getIn.setOnClickListener(click->{
                Intent gotoMain=new Intent(MainActivity.this,LyricPart.class);
                startActivityForResult(gotoMain,30);
            });
        }
    }
}