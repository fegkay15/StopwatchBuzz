package com.fegeley.stopwatchbuzz;

import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private int ms = 0;
    private int sec = 0;
    private int min = 0;
    private int hour = 0;
    private int ms2 = 0;
    private int sec2 = 0;
    private int min2 = 0;
    private int hour2 = 0;
    DecimalFormat msd = new DecimalFormat("000");
    DecimalFormat secd = new DecimalFormat("00");
    DecimalFormat mind = new DecimalFormat("00");
    DecimalFormat hourd = new DecimalFormat("00");
    private int vibInterval = 0;
    private Timer t;
    private int count = 0;
    private int count2 = 0;
    private boolean running = false;
    private boolean pauseLap = true;
    TextView textView;
    TextView textView10;
    EditText editText;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    Vibrator vibe = null;
    private String overall = "00:00:00:000";
    private String lap = "00:00:00:000";
    LinearLayout linearLayout;

    MediaPlayer swe;
    Boolean play = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        textView10 = (TextView) findViewById(R.id.textView10);
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

        linearLayout = (LinearLayout) findViewById(R.id.linlay);
        AutoResizeTextView txt1 = new AutoResizeTextView(MainActivity.this);
        txt1.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        txt1.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        txt1.setTypeface(Typeface.MONOSPACE);
        txt1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 500);
        txt1.setSingleLine();
        txt1.setText("     Overall - Lap                     ");
        linearLayout.addView(txt1);
        Spinner spin = (Spinner) findViewById(R.id.spinner2);
        spin.setSelection(1);
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
        if(running == true){
            return;
        }
        running = true;
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
                if(pauseLap == false) {
                    ms2++;
                    count2++;
                }
                Spinner spin = (Spinner) findViewById(R.id.spinner);
                if(vibInterval == 15600000){
                    vibInterval = 2700;
                    play = true;
                    swe.start();
                }
                if (count == vibInterval) {
                    if (spin.getSelectedItemPosition() == 0) {
                        vib();
                        if (play == true) {
                            swe.start();
                        }

                    }
                    count = 0;
                }else if (count2 == vibInterval) {
                    if (spin.getSelectedItemPosition() == 1) {
                        vib();
                        if (play == true) {
                            swe.start();
                        }
                    }
                    count2 = 0;
                }
                ms++;
                if (ms == 1000) {
                    ms = 0;
                    sec++;
                    if(sec == 60){
                        sec = 0;
                        min++;
                        if(min == 60){
                            min = 0;
                            hour++;
                            if(hour == 100){
                                hour = 0;
                            }
                        }
                    }
                }
                if (ms2 == 1000) {
                    ms2 = 0;
                    sec2++;
                    if(sec2 == 60){
                        sec2 = 0;
                        min2++;
                        if(min2 == 60){
                            min2 = 0;
                            hour2++;
                            if(hour2 == 100){
                                hour2 = 0;
                            }
                        }
                    }
                }

                overall = hourd.format(hour) + ":" + mind.format(min) + ":" + secd.format(sec) + ":" + msd.format(ms);
                lap = hourd.format(hour2) + ":" + mind.format(min2) + ":" + secd.format(sec2) + ":" + msd.format(ms2);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        textView.setText(overall);
                        textView10.setText(lap);
                    }
                });

            }
        }, 0, 1);
    }

    public void stopPressed(View v){
        if(t != null) {
            t.cancel();
        }
        running = false;
    }

    public void resetPressed(View v){
        play = false;
        if(t != null) {
            t.cancel();
        }
        pauseLap = true;
        ms = 0;
        sec = 0;
        min = 0;
        hour = 0;
        count = 0;
        ms2 = 0;
        sec2 = 0;
        min2 = 0;
        hour2 = 0;
        count2 = 0;
        vibInterval = 0;
        textView.setText("00:00:00:000");
        textView10.setText("00:00:00:000");

        editText.setFocusableInTouchMode(true);
        editText2.setFocusableInTouchMode(true);
        editText3.setFocusableInTouchMode(true);
        editText4.setFocusableInTouchMode(true);

        editText.setText("");
        editText2.setText("");
        editText3.setText("");
        editText4.setText("");
        running = false;
    }

    public void vib(){
        Spinner spin2  = (Spinner) findViewById(R.id.spinner2);
        if (spin2.getSelectedItemPosition() == 0) {
            vibe.vibrate(1000);
        } else if (spin2.getSelectedItemPosition() == 1) {
            vibe.vibrate(500);
        } else if (spin2.getSelectedItemPosition() == 2) {
            vibe.vibrate(100);
        } else {
            vibe.vibrate(100);
        }
    }

    public void newLap(View v){
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linlay);
        AutoResizeTextView txt1 = new AutoResizeTextView(MainActivity.this);
        txt1.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        txt1.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        txt1.setTypeface(Typeface.MONOSPACE);
        txt1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 500);
        txt1.setSingleLine();
        txt1.setText(textView.getText() + " - " + textView10.getText() + " (end)  ");
        if(!textView10.getText().equals("00:00:00:000")){
            linearLayout.addView(txt1);
        }
        textView10.setText("00:00:00:000");
        AutoResizeTextView txt2 = new AutoResizeTextView(MainActivity.this);
        txt2.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        txt2.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        txt2.setTypeface(Typeface.MONOSPACE);
        txt2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 500);
        txt2.setSingleLine();
        txt2.setText(textView.getText() + " - " + textView10.getText() + " (start)");
        ms2 = 0;
        sec2 = 0;
        min2 = 0;
        hour2 = 0;
        count2 = 0;
        linearLayout.addView(txt2);
        pauseLap = false;
    }

    public void whatEnd(View v){
        ToggleButton tb = (ToggleButton) findViewById(R.id.toggleButton);
        if(tb.isChecked()){
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linlay);
            AutoResizeTextView txt1 = new AutoResizeTextView(MainActivity.this);
            txt1.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
            txt1.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            txt1.setTypeface(Typeface.MONOSPACE);
            txt1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 500);
            txt1.setSingleLine();
            txt1.setText(textView.getText() + " - " + textView10.getText() + " (end)  ");
            if(!textView10.getText().equals("00:00:00:000")){
                linearLayout.addView(txt1);
            }
            ms2 = 0;
            sec2 = 0;
            min2 = 0;
            hour2 = 0;
            count2 = 0;
            textView10.setText("00:00:00:000");
            newLap(v);
        }else{
            pauseLap = true;
            LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linlay);
            AutoResizeTextView txt1 = new AutoResizeTextView(MainActivity.this);
            txt1.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
            txt1.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
            txt1.setTypeface(Typeface.MONOSPACE);
            txt1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 500);
            txt1.setSingleLine();
            txt1.setText(textView.getText() + " - " + textView10.getText() + " (end)  ");
            if(!textView10.getText().equals("00:00:00:000")){
                linearLayout.addView(txt1);
            }
            ms2 = 0;
            sec2 = 0;
            min2 = 0;
            hour2 = 0;
            count2 = 0;
            textView10.setText("00:00:00:000");
        }
    }

    public void clear(View v){
        linearLayout.removeAllViewsInLayout();
        AutoResizeTextView txt1 = new AutoResizeTextView(MainActivity.this);
        txt1.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
        txt1.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        txt1.setTypeface(Typeface.MONOSPACE);
        txt1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 500);
        txt1.setSingleLine();
        txt1.setText("     Overall - Lap                     ");
        linearLayout.addView(txt1);
    }

    public void copy(View v){
        int laps = linearLayout.getChildCount();
        String lapData = "";
        for(int i = 0; i < laps; i++){
            TextView children = (TextView) linearLayout.getChildAt(i);
            lapData = lapData + "\n" + children.getText().toString();
        }
        Toast.makeText(MainActivity.this, "Copied Lap Data to Clipboard",
                Toast.LENGTH_LONG).show();
        ((ClipboardManager) getSystemService(this.CLIPBOARD_SERVICE))
                .setText(lapData);
    }
}
