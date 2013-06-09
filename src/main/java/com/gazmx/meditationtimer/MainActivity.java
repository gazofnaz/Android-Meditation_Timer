package com.gazmx.meditationtimer;

import android.os.Bundle;
import android.app.Activity;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;
import android.media.MediaPlayer;
import android.widget.Button;


public class MainActivity extends Activity {

    MediaPlayer mpAudio;
    Button b_play;
    Button b_pause;
    Button b_stop;
    MyCount counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set view to my xml file
        setContentView(R.layout.activity_main);

        //set music
        mpAudio = MediaPlayer.create(this, R.raw.singingbowl);

        //start counter
        counter = new MyCount(5000,1000);
        counter.start();

        //action for play
        b_play = (Button) findViewById(R.id.b_play);
        b_play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mpAudio.start();
            }
        });

        //action for pause
        b_pause = (Button) findViewById(R.id.b_pause);
        b_pause.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mpAudio.pause();
            }
        });

        //action for stop
        b_stop = (Button) findViewById(R.id.b_stop);
        b_stop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mpAudio.stop();
            }
        });


    }

    public class MyCount extends CountDownTimer{

        //not sure what this does
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //after the count is up, we start the song
        @Override
        public void onFinish() {
            mpAudio.start();
            final TextView textViewToChange = (TextView) findViewById(R.id.t_duration);
            textViewToChange.setText("Done!");
        }

        //tick each second
        @Override
        public void onTick(long millisUntilFinished) {
            final TextView textViewToChange = (TextView) findViewById(R.id.t_duration);
            textViewToChange.setText(
                    "Left: " + millisUntilFinished/1000);
        }
    }
}
