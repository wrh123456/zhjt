package com.example.zhjt.Utils;

import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;

public class PlayMusic{
    private static final String TAG = "PlayMusic";
    private MediaPlayer mediaPlayer=new MediaPlayer();
    private int key;
    public PlayMusic(int a){
        Log.d(TAG, "PlayMusic: "+a);
       this.key=a;
        initdata();
    }
    private void initdata(){
        String musicname="";
        switch (key){
            case 1:
                musicname="a1.mp3";
                break;
            case 2:
                musicname="a2.mp3";
                break;
            case 3:
                musicname="a3.mp3";
                break;
            case 4:
                musicname="a4.mp3";
                break;
            case 5:
                musicname="a5.mp3";
                break;
            case 6:
                musicname="a6.mp3";
                break;
            case 7:
                musicname="a7.mp3";
                break;
            case 8:
                musicname="a8.mp3";
                break;
            default:break;
        }

        try {
            File file=new File(Environment.getExternalStorageDirectory(),musicname);
            mediaPlayer.setDataSource(file.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void stopmusic(){
        try {

            mediaPlayer.stop();
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
