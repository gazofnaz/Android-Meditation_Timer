package com.gazmx.meditationtimer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Created by Gareth on 26/10/13.
 */
public class MeditationService extends Service {
    private MyCount counter;
    private MediaPlayer mpAudio;

    @Override
    public IBinder onBind(Intent arg0){
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId ){
        Integer myDuration = intent.getExtras().getInt("myDuration");;
        counter = new MyCount(myDuration,1000);
        counter.start();
        //this service will run until we stop it
        Toast.makeText(this, "Service Started " + myDuration, Toast.LENGTH_LONG).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_LONG).show();
    }

    public class MyCount extends CountDownTimer {

        //Constructor, using name of the class, super passes it to parent?
        public MyCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //after the count is up, we start the song
        @Override
        public void onFinish() {
            mpAudio = MediaPlayer.create(MeditationService.this, R.raw.singingbowl);
            mpAudio.start();
            stopService(new Intent(getBaseContext(), MeditationService.class));
            //final TextView textViewToChange = (TextView) findViewById(R.id.t_duration);
            //textViewToChange.setText("Done!");
        }

        //tick each second
        @Override
        public void onTick(long millisUntilFinished) {
            //get textView object
            //final TextView textViewToChange = (TextView) findViewById(R.id.t_duration);

            int seconds = (int) (millisUntilFinished / 1000) % 60 ;
            int minutes = (int) ((millisUntilFinished / (1000*60)) % 60);
            //set nice print for time
            String finishTime=minutes+":"+seconds;
            //change time in textViews
            //textViewToChange.setText("Left: " + finishTime);
        }
    }
}
