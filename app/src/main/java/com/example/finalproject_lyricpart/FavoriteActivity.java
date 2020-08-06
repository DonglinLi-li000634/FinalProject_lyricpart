package com.example.finalproject_lyricpart;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {

    ArrayList<Lyrics> lyricList=new ArrayList<>();
    SQLiteDatabase db;
    MyAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ListView myList=(ListView) findViewById(R.id.FavoriteLyric) ;
        MyDBOpener opener=new MyDBOpener(this);
        db=opener.getWritableDatabase();
        String[] columns={MyDBOpener.COL_ID,MyDBOpener.COL_LYRIC};
        Cursor results=db.query(false,MyDBOpener.TABLE_NAME,columns,null,null,null,null,null,null);
        int idColumnID=results.getColumnIndex(MyDBOpener.COL_ID);
        int lyricColumnID=results.getColumnIndex(MyDBOpener.COL_LYRIC);
        Intent fromShow=getIntent();
        String lyrics=fromShow.getStringExtra("lyrics");
        Boolean flag=fromShow.getBooleanExtra("likeor",false);
        while (results.moveToNext()){
            String lyricData=results.getString(lyricColumnID);
            long idData=results.getLong(idColumnID);
            lyricList.add(new Lyrics(idData,lyricData));
        }
        if(flag){
            adapter = new MyAdapter();
            myList.setAdapter(adapter);
            //adapter.notifyDataSetChanged();
            myList.setSelection(adapter.getCount() - 1);
            ContentValues newRow=new ContentValues();
            newRow.put(MyDBOpener.COL_LYRIC,lyrics);
            long newId=db.insert(MyDBOpener.TABLE_NAME,null,newRow);
            Lyrics newLyric=new Lyrics(newId,lyrics);
            lyricList.add(newLyric);
            adapter=new MyAdapter();
            adapter.notifyDataSetChanged();
            myList.setSelection(adapter.getCount()-1);
        }else{
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle(getResources().getString(R.string.massage4))
                    .setPositiveButton(getResources().getString(R.string.massage2), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent=new Intent(FavoriteActivity.this,LyricPart.class);
                            startActivityForResult(intent,30);
                        }
                    }).setNegativeButton(getResources().getString(R.string.massage3), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(FavoriteActivity.this,getResources().getString(R.string.massage5),Toast.LENGTH_LONG).show();
                }
            });
        }
        myList.setOnItemLongClickListener((p,b,pos,id)->{
            androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            Lyrics lyrics1=lyricList.get(pos);
            alertDialogBuilder.setTitle(getResources().getString(R.string.massage6)).setMessage("The selected row is: "+(pos+1)+"\nThe DataBase id is: "+id)
                    .setPositiveButton(getResources().getString(R.string.massage2),(click,arg)->{
                    deleteLyric(lyrics1);
                    lyricList.remove(pos);
                    adapter.notifyDataSetChanged();
            }).setNegativeButton(getResources().getString(R.string.massage3),(clack,arg)->{}).create().show();
            return true;
        });
        Button backTo=(Button)findViewById(R.id.BackToSearch);
        backTo.setOnClickListener(click->{
            Intent intent=new Intent(FavoriteActivity.this,LyricPart.class);
            startActivityForResult(intent,30);
        });
    }
    private void deleteLyric(Lyrics i){
        db.delete(MyDBOpener.TABLE_NAME,MyDBOpener.COL_ID+"= ?",new String[] {Long.toString(i.getId())});
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