package com.test.toyotomi_new_era;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;

import static com.test.toyotomi_new_era.MainActivity.state;

public class TimerActivity extends AppCompatActivity {

    private CheckBox[] choices;
    private boolean[] overlap;
    private Button done, cancel, help;
    private String type;
    private HashMap<Integer, String> map;
    private boolean selected;
    private boolean[] activateCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        // raises NullPointer exception!!! We lack one choice about disabling.
        String incoming = getIntent().getStringExtra("currentTimer");
        this.done = (Button)findViewById(R.id.done);
        this.cancel = (Button)findViewById(R.id.cancel);

        this.help = (Button)findViewById(R.id.βοήθεια);
        this.help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utilities.popUpHelp(TimerActivity.this, R.layout.help_timer);
            }
        });

        this.selected = false;
        this.choices = new CheckBox[9];
        this.activateCancel = new boolean[8]; // we only have to check the choices that set timer on
        this.choices[0] = (CheckBox)findViewById(R.id.half_hour);
        this.choices[1] = (CheckBox)findViewById(R.id.one_hour);
        this.choices[2] = (CheckBox)findViewById(R.id.one_and_half_hours);
        this.choices[3] = (CheckBox)findViewById(R.id.two_hours);
        this.choices[4] = (CheckBox)findViewById(R.id.three_hours);
        this.choices[5] = (CheckBox)findViewById(R.id.four_hours);
        this.choices[6] = (CheckBox)findViewById(R.id.eight_hours);
        this.choices[7] = (CheckBox)findViewById(R.id.twelve_hours);
        this.choices[8] = (CheckBox)findViewById(R.id.ακύρωση);
        for(boolean b: activateCancel){b = false;} //nothing is checked
        if(!incoming.equals("Απενεργοποιημένο")){findViewById(R.id.ακύρωση).setVisibility(View.VISIBLE);}
        for(CheckBox b: this.choices){
           b.setOnClickListener(mListener);
        }
        this.overlap = new boolean[9];
        Arrays.fill(this.overlap, false);
        this.map = new HashMap<>();
        this.map.put(0, "30 λεπτά");
        this.map.put(1, "1 ώρα");
        this.map.put(2, "1μιση ώρες");
        this.map.put(3, "2 ώρες");
        this.map.put(4, "3 ώρες");
        this.map.put(5, "4 ώρες");
        this.map.put(6, "8 ώρες");
        this.map.put(7, "12 ώρες");
        this.map.put(8, "Απενεργοποιημένο");

        int key = Utilities.getKeyByValue(this.map, incoming);
        this.choices[key].setChecked(true);
        this.overlap[key] = true;

        this.setClock();

    }
    private final View.OnClickListener mListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            int id = v.getId();
            for (int i = 0; i < choices.length; i++) {
                //curr.setChecked(curr.getId() == id);
                if (choices[i].getId() == id){
                    choices[i].setChecked(!overlap[i]);
                    overlap[i] = choices[i].isChecked();
                }
                else{
                    choices[i].setChecked(false);
                    overlap[i] = false;
                }
                if(i<choices.length-1){ //exclude disable
                    if(choices[i].isChecked()){
                        activateCancel[i] = true; //now the button is checked
                    }else activateCancel[i] = false;
                }
            }

        }
    };

    private void setClock() {
        this.done.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                for(int i = 0; i < choices.length; i++){
                    if(choices[i].isChecked()) {
                        type = map.get(i);
                        selected = true;
                    }
                }
                if(selected){
                    state.setTimer(type);

                    Toast.makeText(getApplicationContext(),
                            "Το κλιματιστικό θα απενεργοποιηθεί σε: " + state.getTimer().toLowerCase() + '.',
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.putExtra("timer", state.getTimer());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                else{
                    Utilities.popUpDone(TimerActivity.this);
                }
            }
        });

        this.cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    

}