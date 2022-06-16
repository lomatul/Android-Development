package com.example.pictureandvideoeditorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);

        logo logo = new logo();
        logo.start();
    }

    public class  logo extends Thread{
        public void run()
        {
            try {
                sleep(2000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            Intent intent = new Intent(splashscreen.this,MainActivity.class);
            startActivity(intent);
            splashscreen.this.finish();
        }
    }
}