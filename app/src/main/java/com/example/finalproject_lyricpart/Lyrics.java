package com.example.finalproject_lyricpart;

public class Lyrics {
    protected String lyric;
    protected long id;

    public Lyrics(long id,String lyric){
        this.id=id;
        this.lyric=lyric;
    }
    public Lyrics(long id){
        this.id=id;
        this.lyric="";
    }
    public String getLyric() {
        return this.lyric;
    }
    public void setLyric(String lyric){
        this.lyric=lyric;
    }
    public long getId(){
        return this.id;
    }
    public void setId(long id){
        this.id=id;
    }
}
