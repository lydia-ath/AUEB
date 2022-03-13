package com.test.toyotomi_new_era;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.HashMap;

import static com.test.toyotomi_new_era.MainActivity.state;

public class AirIntensityActivity extends AppCompatActivity {

    private Button done, cancel, help;
    private CheckBox[] choices;
    private boolean[] overlap;
    private HashMap<Integer, String> map;
    private String intensity;
    private boolean selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_intensity);
        String incoming = getIntent().getStringExtra("currentIntensity");
        this.selected = false;
        this.map = new HashMap<>();
        this.map.put(0, "Χαμηλή ένταση");
        this.map.put(1, "Μέτρια ένταση");
        this.map.put(2, "Υψηλή ένταση");
        this.choices = new CheckBox[3];
        this.done = (Button)findViewById(R.id.done3);
        this.cancel = (Button)findViewById(R.id.cancel);

        this.help = (Button)findViewById(R.id.βοήθεια);
        help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // pop up help
            }
        });
        this.choices[0] = (CheckBox)findViewById(R.id.coldAir);
        this.choices[1] = (CheckBox)findViewById(R.id.medIntensity);
        this.choices[2] = (CheckBox)findViewById(R.id.highIntensity);
        for(CheckBox b: this.choices){
            b.setOnClickListener(mListener);
        }
        this.overlap = new boolean[3];
        Arrays.fill(this.overlap, false);
        int key = Utilities.getKeyByValue(this.map, incoming);
        this.choices[key].setChecked(true);
        this.overlap[key] = true;

        this.setIntensity();

        this.help = (Button)findViewById(R.id.βοήθεια);
        help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // pop up help
                Utilities.popUpHelp(AirIntensityActivity.this, R.layout.help_intensity);
            }
        });
    }

    private final View.OnClickListener mListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            int id = v.getId();
            for (int i = 0; i < choices.length; i++) {
                if (choices[i].getId() == id){
                    choices[i].setChecked(!overlap[i]);
                    overlap[i] = choices[i].isChecked();
                }
                else{
                    choices[i].setChecked(false);
                    overlap[i] = false;
                }
            }
        }
    };

    private void setIntensity(){
        this.done.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                for(int i = 0; i < choices.length; i++){
                    if(choices[i].isChecked()) {
                        intensity = map.get(i);
                        selected = true;
                    }
                }
                if(selected){
                    state.setAirIntensity(intensity);

                    Toast.makeText(getApplicationContext(),
                            "Ένταση αέρα: " + state.getAirIntensity().toLowerCase() + '.',
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.putExtra("intensity", state.getAirIntensity());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                else{
                    Utilities.popUpDone(AirIntensityActivity.this);
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