package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.DexterBuilder;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

   // private  static  int SPLASH_TIME_OUT = 3000;

    ListView listView;
    String[] items;
    View lastTouchedView ;
    int position=9999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listviewsong);


        runtimePermission();

    }

    public void runtimePermission()
    {
        Dexter.withContext(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        displaySongs();
                    }


                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();

                    }
                }).check();

    }

    //file missing here that's why null pointer exception
    public ArrayList<File> findSong(File file) {

        ArrayList arrayList = new ArrayList();

        File [] files = file.listFiles();

        if(files != null) {

            for (File singlefile : files) {

                if (singlefile.isDirectory() && !singlefile.isHidden()) {

                    arrayList.addAll(findSong(singlefile));

                } else {

                    if (singlefile.getName().endsWith(".wav")) {

                        arrayList.add(singlefile);

                    } else if (singlefile.getName().endsWith(".mp3")) {

                        arrayList.add(singlefile);

                    }

                }



            }

        }

        return arrayList;

    }
    void displaySongs()
    {
        //may be from here file is not passed log kore dekhte parish

//        Log.d("environment", String.valueOf(Environment.getExternalStorageDirectory()+"/download"));
        File sdCardRoot = Environment.getExternalStorageDirectory()   ;
//        File dir = new File(sdCardRoot.getAbsolutePath() +"/storage/");
//        Log.d("environment", String.valueOf(sdCardRoot));

        final ArrayList<File> mySongs = findSong (sdCardRoot);
        Log.d("environment", String.valueOf(mySongs));

        items = new String[mySongs.size()];
        for (int i=0; i<mySongs.size(); i++)
        {
            items[i]= mySongs.get(i).getName().toString().replace(".mp3", "").replace(".wav", "");

        }
        /*ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,items);
        listView.setAdapter(myAdapter);*/

        customAdapter customAdapter= new customAdapter();
        listView.setAdapter(customAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String songName = (String) listView.getItemAtPosition(i);
                position = i;
                customAdapter customAdapter= new customAdapter();
                listView.setAdapter(customAdapter);
                startActivity(new Intent(getApplicationContext(),playerActivity.class)
                        .putExtra("songs", mySongs)
                        .putExtra("songname", songName)
                        .putExtra("pos", i));

            }

        });
    }

    class customAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if(view==null){
                view = getLayoutInflater().inflate(R.layout.activity_listitem, null);
            }

            Log.d("myTag", String.valueOf(position));
            if(i==position){
                view.setBackgroundColor(Color.GRAY);
            }

            TextView textsong = view.findViewById(R.id.textsongname);
            textsong.setSelected(true);
            textsong.setText(items[i]);
          position=9999;
            return  view;
        }
    }

}