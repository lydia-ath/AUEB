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

public class AirTypeActivity extends AppCompatActivity {

    private CheckBox[] choices;
    private boolean[] overlap;
    private Button done, cancel, help;
    private String type;
    private HashMap<Integer, String> map;
    private boolean selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_type);
        String incoming = getIntent().getStringExtra("currentType");
        this.done = (Button)findViewById(R.id.done);
        this.cancel = (Button)findViewById(R.id.cancel);

        this.help = (Button)findViewById(R.id.βοήθεια);
        this.help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Utilities.popUpHelp(AirTypeActivity.this, R.layout.help_air_type);
            }
        });
        this.selected = false;
        this.choices = new CheckBox[4];
        this.choices[0] = (CheckBox)findViewById(R.id.warmAir);
        this.choices[1] = (CheckBox)findViewById(R.id.coldAir);
        this.choices[2] = (CheckBox)findViewById(R.id.dryAir);
        this.choices[3] = (CheckBox)findViewById(R.id.fan);
        for(CheckBox b: this.choices){
            b.setOnClickListener(mListener);
        }
        this.overlap = new boolean[4];
        Arrays.fill(this.overlap, false);
        this.map = new HashMap<>();
        this.map.put(0, "Ζεστός αέρας");
        this.map.put(1, "Κρύος αέρας");
        this.map.put(2, "Ξηρός αέρας");
        this.map.put(3, "Λειτουργία ανεμιστήρα");

        int key = Utilities.getKeyByValue(this.map, incoming);
        this.choices[key].setChecked(true);
        this.overlap[key] = true;
        this.setType();
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
            }
        }
    };

    private void setType() {
        this.done.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                for(int i = 0; i < choices.length; i++){
                    if(choices[i].isChecked()) {
                        type = map.get(i);
                        selected = true;
                    }
                }
                if(selected){
                    state.setAirType(type);

                    Toast.makeText(getApplicationContext(),
                            "Επιλέχθηκε " + state.getAirType().toLowerCase() + '.',
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.putExtra("type", state.getAirType());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                else{
                    Utilities.popUpDone(AirTypeActivity.this);
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