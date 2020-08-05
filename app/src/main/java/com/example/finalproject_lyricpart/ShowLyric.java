package com.example.finalproject_lyricpart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.net.URI;

public class ShowLyric extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_lyric);
        TextView lyricShow=(TextView)findViewById(R.id.lyricForResult);
        Intent fromLast=getIntent();
        String lyricFromLast=fromLast.getStringExtra("lyric");
        String nameA=fromLast.getStringExtra("nameA");
        String nameT=fromLast.getStringExtra("nameT");
        lyricShow.setText(lyricFromLast);
        Button gotoGoogle=(Button)findViewById(R.id.searchOnGoogle);
        gotoGoogle.setOnClickListener(click->{
            String URLSEC="https://www.google.com/search?q="+nameA+"+"+nameT;
            Uri urix=Uri.parse(URLSEC);
            Intent intent=new Intent(Intent.ACTION_VIEW,urix);
            startActivity(intent);
        });
    }
}