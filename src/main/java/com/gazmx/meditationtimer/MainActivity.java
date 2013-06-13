package com.gazmx.meditationtimer;

import android.os.Bundle;
import android.app.Activity;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.media.MediaPlayer;
import android.widget.Button;


public class MainActivity extends Activity {

    MediaPlayer mpAudio;
    Button b_play;
    Button b_stop;
    MyCount counter;
    Integer myDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set view to my xml file
        setContentView(R.layout.activity_main);

        //action for play
        b_play = (Button) findViewById(R.id.b_play);
        b_play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //set music
                //@todo add option to play sound on start
                mpAudio = MediaPlayer.create(MainActivity.this, R.raw.singingbowl);

                EditText timerDuration = (EditText) findViewById(R.id.i_duration);
                //parse edit text String to Int
                //@todo Add some error checking here
                myDuration = Integer.parseInt(timerDuration.getText().toString());
                //Convert minutes to milliseconds
                myDuration = myDuration * 60 * 1000;
                //instantiate counter 1200000 = 20 minutes
                counter = new MyCount(myDuration,1000);
                //start counter
                counter.start();
            }
        });

        //action for stop
        b_stop = (Button) findViewById(R.id.b_stop);
        b_stop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                counter.cancel();
                mpAudio.stop();
            }
        });
    }

    public class MyCount extends CountDownTimer{

        //Constructor, using name of the class, super passes it to parent?
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
                    //@todo convert this to minutes/seconds
                    "Left: " + millisUntilFinished/1000);
        }
    }
}
