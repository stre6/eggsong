package com.kaka.eggsong;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MyService extends Service {
    private ArrayList<MusicDto> list;
    private MediaPlayer mediaPlayer;
    private TextView title;
    private ImageView album,previous,play,pause,next;
    private SeekBar seekBar;
    boolean isPlaying = true;
    private ContentResolver res;
    private MusicActivity.ProgressUpdate progressUpdate;
    private int position;
    private CheckBox banbor;
    public MyService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    public void playMusic(MusicDto musicDto) {

        try {
            seekBar.setProgress(0);
            title.setText(musicDto.getArtist()+" - "+musicDto.getTitle());
            Uri musicURI = Uri.withAppendedPath(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, ""+musicDto.getId());
            mediaPlayer.reset();
            mediaPlayer.setDataSource(this, musicURI);
            mediaPlayer.prepare();
            mediaPlayer.start();
            NotificationSomethings();
            seekBar.setMax(mediaPlayer.getDuration());
            if(mediaPlayer.isPlaying()){
                play.setVisibility(View.GONE);
                pause.setVisibility(View.VISIBLE);
            }else{
                play.setVisibility(View.VISIBLE);
                pause.setVisibility(View.GONE);
            }


            Bitmap bitmap = BitmapFactory.decodeFile(getCoverArtPath(Long.parseLong(musicDto.getAlbumId()),getApplication()));
            album.setImageBitmap(bitmap);

        }
        catch (Exception e) {
            Log.e("SimplePlayer", e.getMessage());
        }
    }




    public void NotificationSomethings(){

        Resources res=getResources();

        Intent notificationIntent= new Intent(this.getApplicationContext(),NotificationSomethings.class);
        notificationIntent.putExtra("notificationId",9999);
        PendingIntent contentIntent=PendingIntent.getActivity(this,0,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
        builder.setContentTitle(MediaStore.Audio.Media.TITLE)
                .setContentText(MediaStore.Audio.Media.ARTIST)
                .setTicker(MediaStore.Audio.Media.TITLE)
                .setSmallIcon(R.drawable.alsong)
                .setLargeIcon(BitmapFactory.decodeResource(res,R.id.album))
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setDefaults(Notification.DEFAULT_ALL);

        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            builder.setCategory(Notification.CATEGORY_MESSAGE)
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setVisibility(Notification.VISIBILITY_PUBLIC);
        }
        NotificationManager nm=(NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(1234,builder.build());
    }
    //앨범이 저장되어 있는 경로를 리턴합니다.
    private static String getCoverArtPath(long albumId, Context context) {

        Cursor albumCursor = context.getContentResolver().query(
                MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Audio.Albums.ALBUM_ART},
                MediaStore.Audio.Albums._ID + " = ?",
                new String[]{Long.toString(albumId)},
                null
        );
        boolean queryResult = albumCursor.moveToFirst();
        String result = null;
        if (queryResult) {
            result = albumCursor.getString(0);
        }
        albumCursor.close();
        return result;
    }

    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play:
                pause.setVisibility(View.VISIBLE);
                play.setVisibility(View.GONE);
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
                mediaPlayer.start();
                break;
            case R.id.pause:
                pause.setVisibility(View.GONE);
                play.setVisibility(View.VISIBLE);
                mediaPlayer.pause();
                break;
            case R.id.pre:
                if(position-1>=0 ){
                    position--;
                    playMusic(list.get(position));
                    seekBar.setProgress(0);
                }
                break;
            case R.id.next:
                if(position+1<list.size()){
                    position++;
                    playMusic(list.get(position));
                    seekBar.setProgress(0);
                }

                break;
            case R.id.banbor:
                banbor.isChecked();
                mediaPlayer.setLooping(true);
        }

    }


    class ProgressUpdate extends Thread{
        @Override
        public void run() {
            while(isPlaying){
                try {
                    Thread.sleep(500);
                    if(mediaPlayer!=null){
                        seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    }
                } catch (Exception e) {
                    Log.e("ProgressUpdate",e.getMessage());
                }

            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        isPlaying = false;
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
