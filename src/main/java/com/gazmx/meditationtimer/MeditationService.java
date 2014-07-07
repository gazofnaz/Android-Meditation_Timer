package com.gazmx.meditationtimer;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.widget.Toast;
import java.util.concurrent.TimeUnit;

/**
 * Created by Gareth on 26/10/13.
 */
public class MeditationService extends Service {
    private MyCount counter;
    private MediaPlayer mpAudio;

    @Override
    public IBinder onBind( Intent arg0 ){
        return null;
    }

    @Override
    public int onStartCommand( Intent intent, int flags, int startId ){

        Integer myDuration = intent.getExtras().getInt( "myDuration" );

        // handy method to convert ms to minutes
        long nicePrintDuration = TimeUnit.MILLISECONDS.toMinutes(myDuration);

        // quick pluralisation
        String message =  " minute";
        if ( nicePrintDuration > 1 ){
            message =  " minutes";
        }

        counter = new MyCount( myDuration,1000 );
        counter.start();

        mpAudio = new MediaPlayer();
        mpAudio.setAudioStreamType(AudioManager.STREAM_ALARM);

        try {
            mpAudio.setDataSource(getApplicationContext(), Uri.parse("android.resource://com.gazmx.meditationtimer/" + R.raw.singingbowl));
        }
        catch( Exception $e ){
            // @todo do something with this
        }
        mpAudio.prepareAsync();

        //this service will run until we stop it
        Toast.makeText(this,
                       "Beginning Session, " + nicePrintDuration + message,
                       Toast.LENGTH_LONG).show();
        return START_STICKY;

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Toast.makeText(this, "Ending Session", Toast.LENGTH_LONG).show();
    }

    public class MyCount extends CountDownTimer {

        //Constructor, using name of the class, super passes it to parent?
        public MyCount( long millisInFuture, long countDownInterval ) {
            super( millisInFuture, countDownInterval );
        }

        //after the count is up, we start the song
        @Override
        public void onFinish() {
            mpAudio.start();
            stopService(new Intent( getBaseContext(), MeditationService.class ) );
        }

        //tick each second
        // @todo fix ticker
        @Override
        public void onTick( long millisUntilFinished ) {

            int seconds = (int) ( millisUntilFinished / 1000 ) % 60 ;
            int minutes = (int) ( ( millisUntilFinished / (1000*60 ) ) % 60 );
            String finishTime=minutes+":"+seconds;

        }
    }
}
