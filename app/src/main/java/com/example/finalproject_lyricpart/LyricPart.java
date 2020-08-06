package com.example.finalproject_lyricpart;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LyricPart extends AppCompatActivity {
    String nameA, nameT;
    Button search;
    EditText artist, title1;
    ProgressBar progressBar;
    ArrayList<Lyrics> lyricList = new ArrayList<>();
    String ForResult;
    TextView txt1,txt2;
    String URLSEC;
    SQLiteDatabase db;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lyric_part);

        //initSpanner
        //String[] adaptArray={"artist+title",""}
        //ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.activity_lyric_part,adaptArray);
        search = (Button) findViewById(R.id.searchBtn);
        artist = (EditText) findViewById(R.id.artistTxt);
        title1 = (EditText) findViewById(R.id.titleTxt);
        //ListView myList = (ListView) findViewById(R.id.resultLst);
        txt1=(TextView) findViewById(R.id.TextView1);
        txt2=(TextView) findViewById(R.id.TextView2);
        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //progressBar.setVisibility(View.VISIBLE);
        //GetLyrics get=new GetLyrics();
        //String ForResult;
        search.setOnClickListener(click -> {
            nameA = artist.getText().toString();
            nameT = title1.getText().toString();
            //Toast.makeText(this,nameA+"2"+nameT,Toast.LENGTH_LONG).show();
            if (nameA.length() != 0 && nameT.length() != 0) {
                nameA = artist.getText().toString();
                nameT = title1.getText().toString();
                URLSEC="https://api.lyrics.ovh/v1/" + nameA + "/" + nameT;
                new Thread(){
                    @Override
                    public void run(){
                        super.run();
                        ForResult=gethttpresult();
                    }
                }.start();
                //ForResult=gethttpresult();
                //get.execute();
                //get.execute();
                //ForResult=get.doInBackground();
                //assert ForResult != null;
                if (ForResult!=null) {
                    //Lyrics newLyric = new Lyrics(ForResult);
                    SharedPreferences lyricGeted=getSharedPreferences("lyric",MODE_PRIVATE);
                    String preLyric=lyricGeted.getString("lyric","");
                    Intent goToNext=new Intent(LyricPart.this,ShowLyric.class);
                    goToNext.putExtra("lyric",ForResult);
                    goToNext.putExtra("nameA",nameA);
                    goToNext.putExtra("nameT",nameT);
                    Toast.makeText(LyricPart.this,getResources().getString(R.string.success1),Toast.LENGTH_LONG).show();
                    startActivityForResult(goToNext,30);

                }else{
                    Toast.makeText(LyricPart.this,getResources().getString(R.string.error1),Toast.LENGTH_LONG).show();
                }

            }else{
                Toast.makeText(LyricPart.this,getResources().getString(R.string.error),Toast.LENGTH_LONG).show();
            }
            //Log.i("result:",ForResult);
        });
        Button goToF=(Button)findViewById(R.id.CheckFavorite);
        goToF.setOnClickListener(click->{
            Intent intent=new Intent(LyricPart.this,FavoriteActivity.class);
            startActivityForResult(intent,30);
        });
    }

    public  String gethttpresult(){
        String data = "";
        String results=null;
        //String urlStr="https://api.lyrics.ovh/v1/"+a+"/"+b;
        try {
            URL url = new URL(URLSEC);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

            String temp = "";
            while (temp != null) {
                temp = bufferedReader.readLine();
                data = data + temp;
            }
            JSONObject jsonObject=new JSONObject(data);
            results=jsonObject.getString("lyrics");
            return results;

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return e.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return e.toString();
        }
    }
    @Override
    protected void onPause(){
        super.onPause();
        SharedPreferences prefs = getSharedPreferences("lyric", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("lyric", ForResult);
        editor.commit();
    }
}
    //@SuppressLint("StaticFieldLeak")
