package com.gazmx.meditationtimer;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.media.MediaPlayer;
import android.widget.Button;
import android.widget.SeekBar;

/**
 * Sets a timer, plays a sound after the specified duration.
 *
 * @author Gareth Arnott
 */
public class MainActivity extends Activity {

    private Button b_play;
    private Button b_stop;
    private Integer myDuration;
    private SeekBar seekBar;
    private TextView timerInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set view to my xml file
        setContentView(R.layout.activity_main);
        //get the seek bar
        seekBar = (SeekBar) findViewById(R.id.i_seek);
        //get the duration input (value set by seek bar)
        timerInput = (TextView) findViewById(R.id.i_duration);

        //action for play
        b_play = (Button) findViewById(R.id.b_play);
        b_play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), MeditationService.class);
                //@todo add option to play sound on start
                //parse edit text String to Int
                try{
                    myDuration = Integer.parseInt(timerInput.getText().toString());
                    //convert to ms
                    myDuration = myDuration * 60 * 1000;
                }
                catch(NullPointerException ex){
                    //default 15 minutes
                    myDuration = 900000;
                }
                //correct way to pass value to service
                myIntent.putExtra("myDuration", myDuration );
                startService(myIntent);
                // hopefully will fix the tendency to not fire the sound
                // when battery power is low. guess the cpu shuts down until woken
                getWindow().clearFlags( WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON );
            }
        });

        //action for stop
        b_stop = (Button) findViewById(R.id.b_stop);
        b_stop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getWindow().clearFlags( android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON );
                stopService(new Intent(getBaseContext(), MeditationService.class));
            }
        });

        seekBar.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener(){
            //set display text to count value.
            public void onProgressChanged(SeekBar seekBar, int duration,
                                          boolean fromUser){
                timerInput.setText(String.valueOf(duration));
            }
            public void onStartTrackingTouch(SeekBar seekBar){
            }
            public void onStopTrackingTouch(SeekBar seekBar){
            }
        });
    }
}
