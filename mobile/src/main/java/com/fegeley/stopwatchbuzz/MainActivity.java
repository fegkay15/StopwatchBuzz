package com.fegeley.stopwatchbuzz;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private int ms = 0;
    private int sec = 0;
    private int min = 0;
    private int hour = 0;
    DecimalFormat msd = new DecimalFormat("000");
    DecimalFormat secd = new DecimalFormat("00");
    DecimalFormat mind = new DecimalFormat("00");
    DecimalFormat hourd = new DecimalFormat("00");
    private int vibInterval = 0;
    private Timer t;
    private int count;
    TextView textView;
    EditText editText;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    Vibrator vibe = null;

    MediaPlayer swe;
    Boolean play = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);
        editText2 = (EditText) findViewById(R.id.editText2);
        editText3 = (EditText) findViewById(R.id.editText3);
        editText4 = (EditText) findViewById(R.id.editText4);
        vibe = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
        swe = MediaPlayer.create(this, R.raw.swe);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setLogo(R.mipmap.ic_launcher);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    //@Override
    //public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        //return true;
    //}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startPressed(View v){
        int one;
        int two;
        int three;
        int four;
        if(editText.getText().toString().trim().length() != 0) {
            one = Integer.parseInt(editText.getText().toString());
        }else {
            one = Integer.parseInt(editText.getHint().toString());
        }

        if(editText2.getText().toString().trim().length() != 0) {
            two = Integer.parseInt(editText2.getText().toString());
        }else {
            two = Integer.parseInt(editText2.getHint().toString());
        }

        if(editText3.getText().toString().trim().length() != 0) {
            three = Integer.parseInt(editText3.getText().toString());
        }else {
            three = Integer.parseInt(editText3.getHint().toString());
        }

        if(editText4.getText().toString().trim().length() != 0) {
            four = Integer.parseInt(editText4.getText().toString());
        }else {
            four = Integer.parseInt(editText4.getHint().toString());
        }

        vibInterval = (one *  3600000) + (two *  60000) + (three *  1000) + (four * 1);

        editText.setFocusable(false);
        editText2.setFocusable(false);
        editText3.setFocusable(false);
        editText4.setFocusable(false);



        t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                count++;
                if(vibInterval == 15600000){
                    vibInterval = 2700;
                    play = true;
                    swe.start();
                }
                if(count == vibInterval){
                    vib();
                    if(play == true){
                        swe.start();
                    }
                    count = 0;
                }
                ms++;
                if (ms == 1000) {
                    ms = 0;
                    addSec();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(hourd.format(hour) + ":" + mind.format(min) + ":" + secd.format(sec) + ":" + msd.format(ms));
                    }
                });

            }
        }, 0, 1);
    }

    public void stopPressed(View v){
        t.cancel();
    }

    public void resetPressed(View v){
        ms = 0;
        sec = 0;
        min = 0;
        hour = 0;
        count = 0;
        vibInterval = 0;
        textView.setText("00:00:00:000");
        t.cancel();
        editText.setFocusableInTouchMode(true);
        editText2.setFocusableInTouchMode(true);
        editText3.setFocusableInTouchMode(true);
        editText4.setFocusableInTouchMode(true);
        editText.setText("");
        editText2.setText("");
        editText3.setText("");
        editText4.setText("");
        play = false;
    }

    public void addSec(){
        sec++;
        if(sec == 60){
            sec = 0;
            addMin();
        }
    }

    public void addMin(){
        min++;
        if(min == 60){
            min = 0;
            addHour();
        }
    }
    public void addHour(){
        hour++;
        if(hour == 100){
            hour = 0;
        }
    }

    public void vib(){
        RadioButton radioButton = (RadioButton) findViewById(R.id.radioButton);
        RadioButton radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
        RadioButton radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
        if (radioButton.isChecked()) {
            vibe.vibrate(1000);
        } else if (radioButton2.isChecked()) {
            vibe.vibrate(500);
        } else if (radioButton3.isChecked()) {
            vibe.vibrate(100);
        } else {
            vibe.vibrate(100);
        }
    }
}
