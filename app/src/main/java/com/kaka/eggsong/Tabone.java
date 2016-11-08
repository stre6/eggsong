package com.kaka.eggsong;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by stre6 on 2016-10-20.
 */


public class Tabone extends Fragment{

    private ListView listView;
    public static ArrayList<MusicDto> list;
    MusicDto musicDto;
    private ImageView album;
    private TextView artist,title;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab1, null);
        title = (TextView)view.findViewById(R.id.title);
        album = (ImageView)view.findViewById(R.id.album);
        artist=(TextView)view.findViewById(R.id.artist);
        getMusicList();
        listView=(ListView)view.findViewById(android.R.id.list);
        MyAdapter adapter=new MyAdapter(getActivity(),list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Intent intent=new Intent();
                intent.setClass(getActivity().getApplicationContext(), MusicActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("playlist",list);
                startActivity(intent);


            }
        });

        return view;

    }


    public  void getMusicList(){
        list = new ArrayList<>();
        //가져오고 싶은 컬럼 명을 나열합니다. 음악의 아이디, 앰블럼 아이디, 제목, 아스티스트 정보를 가져옵니다.
        String[] projection = {MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.ALBUM_ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST
        };

        Cursor cursor = getActivity().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection, null, null, null);

        while(cursor.moveToNext()){
            MusicDto musicDto = new MusicDto();
            musicDto.setId(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
            musicDto.setAlbumId(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));
            musicDto.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            musicDto.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
            list.add(musicDto);
        }
        cursor.close();
    }


}