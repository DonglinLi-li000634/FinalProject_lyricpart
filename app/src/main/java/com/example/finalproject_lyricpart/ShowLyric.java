package com.example.finalproject_lyricpart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

import android.content.DialogInterface;
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
        Button likeSong=(Button)findViewById(R.id.likeBtn);
        likeSong.setOnClickListener(click->{
            //android.app.AlertDialog alertDialog=new android.app.AlertDialog.Builder(this);
            AlertDialog.Builder builder=new AlertDialog.Builder(ShowLyric.this);
            builder.setTitle(getResources().getString(R.string.massage1))
                    .setPositiveButton(getResources().getString(R.string.massage2), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent goToLP=new Intent(ShowLyric.this,FavoriteActivity.class);
                            goToLP.putExtra("likeor",true);
                            goToLP.putExtra("lyrics",lyricFromLast);
                            startActivityForResult(goToLP,30);
                        }
                    }).setNegativeButton(getResources().getString(R.string.massage3), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent goToLP=new Intent(ShowLyric.this,LyricPart.class);
                    goToLP.putExtra("likeor",false);
                    startActivityForResult(goToLP,30);
                }
            }).create().show();
        });
    }
}