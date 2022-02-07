package com.example.counter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public  int sum=0;
   public Button increase,decrease,reset;
   public TextView counter,count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        increase = (Button) findViewById(R.id.increase);
        decrease =(Button)findViewById(R.id.decrease);
        reset = (Button)findViewById(R.id.reset);
        count=(TextView) findViewById(R.id.count);
        final MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.beep);





        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sum=sum+1;
                count.setText(Integer.toString(sum));
                mediaPlayer.start();
            }
        });


        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sum =sum-1;
                mediaPlayer.start();
                count.setText(Integer.toString(sum));
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mediaPlayer.start();
                AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Do You Want To Reset ?");
                builder.setCancelable(true);
                builder.setNegativeButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sum = 0;
                        count.setText(Integer.toString(sum));
                    }
                });

                builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        count.setText(Integer.toString(sum));
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }

        }

        );


    }
}