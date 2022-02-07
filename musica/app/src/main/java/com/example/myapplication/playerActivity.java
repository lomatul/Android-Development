package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gauravk.audiovisualizer.visualizer.BarVisualizer;

import java.io.File;
import java.util.ArrayList;

public class playerActivity extends AppCompatActivity {

    Button play,playnext,playprev,fastprev,fastnext;
    TextView txtsongname,txtstart,txtstop;
    SeekBar seekmusic;
    BarVisualizer visualizer;
    ImageView imageView;



    String sname;
    public static final String Extra_Name="song_name";
    static MediaPlayer mediaPlayer;
    int position;
    ArrayList<File> mySongs;
    Thread updateseekbar;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if (visualizer != null)
        {
            visualizer.release();
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        getSupportActionBar().setTitle("Now Playing");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        playprev = findViewById(R.id.playprev);
        playnext = findViewById(R.id.playnext);
        play = findViewById(R.id.play);
        fastnext= findViewById(R.id.fastnext);
        fastprev = findViewById(R.id.fastprev);
        txtsongname = findViewById(R.id.txtsn);
        txtstart= findViewById(R.id.txtstart);
        txtstop = findViewById(R.id.txtstop);
        seekmusic = findViewById(R.id.seekbar);
        visualizer = findViewById(R.id.blast);
        imageView = findViewById(R.id.imageview);

        if (mediaPlayer != null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();

        }
        Intent i = getIntent();
        Bundle bundle = i.getExtras();

        mySongs = (ArrayList) bundle.getParcelableArrayList("songs");
        String songName = i.getStringExtra("songname");
        position = bundle.getInt("pos",0);
        txtsongname.setSelected(true);
        Uri uri = Uri.parse(mySongs.get(position).toString());
        sname=mySongs.get(position).getName();
        txtsongname.setText(sname);
        mediaPlayer =MediaPlayer.create(getApplicationContext(),uri);
        mediaPlayer.start();

        updateseekbar = new Thread()
        {
            @Override
            public void run()
            {
                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = 0;

                while (currentPosition <totalDuration)
                {
                    try {
                        sleep(500);
                        currentPosition= mediaPlayer.getCurrentPosition();
                        seekmusic.setProgress(currentPosition);
                    }
                    catch (InterruptedException | IllegalStateException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
        };

        seekmusic.setMax(mediaPlayer.getDuration());
        updateseekbar.start();
        seekmusic.getProgressDrawable().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.MULTIPLY);
        seekmusic.getThumb().setColorFilter(getResources().getColor(R.color.black),PorterDuff.Mode.SRC_IN);

        seekmusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        String endTime = createTime(mediaPlayer.getDuration());
        txtstop.setText(endTime);

        final Handler handler = new Handler();
        final int delay = 1000;

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                String currentTime = createTime(mediaPlayer.getCurrentPosition());
                txtstart.setText(currentTime);
                handler.postDelayed(this, delay);

            }
        },delay);

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   NotificationManager manager = (NotificationManager)
                        getApplicationContext().getSystemService(
                                Context.NOTIFICATION_SERVICE
                        );

                Intent intent1 = new Intent( playerActivity.this
                        ,playerActivity.class);
                intent1.putExtra("next",true);
                intent1.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

                  */


                if (mediaPlayer.isPlaying())
                {
                    play.setBackgroundResource(R.drawable.ic_play);
                    mediaPlayer.pause();
                }
                else
                {
                    play.setBackgroundResource(R.drawable.ic_pause);
                    mediaPlayer.start();
                }
            }
        });


        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                playnext.performClick();
            }
        });


        int audiosessionId = mediaPlayer.getAudioSessionId();
        if(audiosessionId!=-1)
        {
            visualizer.setAudioSessionId(audiosessionId);
        }

        playnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = ((position+1)%mySongs.size());
                Uri u = Uri.parse(mySongs.get(position).toString());
                mediaPlayer=MediaPlayer.create(getApplicationContext(),u);
                sname = mySongs.get(position).getName();
                txtsongname.setText(sname);
                mediaPlayer.start();
                play.setBackgroundResource(R.drawable.ic_pause);
                startAnimation(imageView);

                int audiosessionId = mediaPlayer.getAudioSessionId();
                if(audiosessionId!=-1)
                {
                    visualizer.setAudioSessionId(audiosessionId);
                }
            }
        });

        playprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                position = ((position-1)<0 ?(mySongs.size()-1) : (position-1));

                Uri u = Uri.parse(mySongs.get(position).toString());
                mediaPlayer=MediaPlayer.create(getApplicationContext(),u);
                sname = mySongs.get(position).getName();
                txtsongname.setText(sname);
                mediaPlayer.start();
                play.setBackgroundResource(R.drawable.ic_pause);
                startAnimation(imageView);
                int audiosessionId = mediaPlayer.getAudioSessionId();
                if(audiosessionId!=-1)
                {
                    visualizer.setAudioSessionId(audiosessionId);
                }
            }
        });



        fastnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying())
                {

                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+1000);
                }

            }
        });
        fastprev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying())
                {

                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-1000);
                }

            }
        });

    }



    public void startAnimation(View view)
    {
        ObjectAnimator animator = ObjectAnimator.ofFloat(imageView,"rotation",0f,360f);
        animator.setDuration(1000);
        AnimatorSet animatorSet= new AnimatorSet();
        animatorSet.playTogether(animator);
        animatorSet.start();
    }

    public  String  createTime(int duration)
    {
        String time = "";
        int min = duration/1000/60;
        int sec = duration/1000%60;


        time+=min+":";

        if(sec<10)
        {
            time+=0;
        }
        time+=sec;

        return  time ;
    }
}