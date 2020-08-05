package com.example.finalproject_lyricpart;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
    MyAdapter adapter;
    String ForResult;
    TextView txt1,txt2;
    String URLSEC;
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
        ListView myList = (ListView) findViewById(R.id.resultLst);
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
                    Lyrics newLyric = new Lyrics(ForResult);
                    lyricList.add(newLyric);

                    adapter = new MyAdapter();
                    myList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    myList.setSelection(adapter.getCount() - 1);
                }else{
                    Toast.makeText(LyricPart.this,getResources().getString(R.string.error1),Toast.LENGTH_LONG).show();
                }

            }else{
                Toast.makeText(LyricPart.this,getResources().getString(R.string.error),Toast.LENGTH_LONG).show();
            }
            //Log.i("result:",ForResult);

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

    protected class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return lyricList.size();
        }

        @Override
        public Lyrics getItem(int i) {
            return lyricList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = getLayoutInflater();
            View rowView;
            TextView rowMessage;
            Lyrics thisRow = getItem(i);
            rowView = inflater.inflate(R.layout.show, viewGroup, false);
            rowMessage = rowView.findViewById(R.id.msg);
            ImageView opt = rowView.findViewById(R.id.img);
            opt.setImageResource(R.drawable.lyric);
            //rowMessage.setText(thisRow.getLyric());
            rowMessage.setText((thisRow.getLyric()));
            return rowView;
        }
    }

}
    //@SuppressLint("StaticFieldLeak")
