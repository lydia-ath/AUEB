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

public class AirDirectionActivity extends AppCompatActivity {

    private Button done, cancel, help;
    private CheckBox[] choices;
    private boolean[] overlap;
    private HashMap<Integer, String> map;
    private String dir;
    private boolean selected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_direction);
        String incoming = getIntent().getStringExtra("currentDir");
        this.done = (Button)findViewById(R.id.done2);
        this.cancel = (Button)findViewById(R.id.cancel);

        this.help = (Button)findViewById(R.id.βοήθεια);
        this.help = (Button)findViewById(R.id.βοήθεια);
        help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // pop up help
            }
        });
        this.selected = false;
        this.choices = new CheckBox[4];
        this.choices[0] = (CheckBox)findViewById(R.id.downDir);
        this.choices[1] = (CheckBox)findViewById(R.id.centreDir);
        this.choices[2] = (CheckBox)findViewById(R.id.upDir);
        this.choices[3] = (CheckBox)findViewById(R.id.everywhereDir);
        for(CheckBox b: this.choices){
            b.setOnClickListener(mListener);
        }
        this.overlap = new boolean[4];
        Arrays.fill(this.overlap, false);
        this.map = new HashMap<>();
        this.map.put(0, "Κάτω");
        this.map.put(1, "Κέντρο");
        this.map.put(2, "Πάνω");
        this.map.put(3, "Παντού");

        int key = Utilities.getKeyByValue(this.map, incoming);
        this.choices[key].setChecked(true);
        this.overlap[key] = true;

        this.setDir();

        this.help = (Button)findViewById(R.id.βοήθεια);
        help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // pop up help
                Utilities.popUpHelp(AirDirectionActivity.this, R.layout.help_direction);
            }
        });

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

    private void setDir() {
        this.done.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view){
                for(int i = 0; i < choices.length; i++){
                    if(choices[i].isChecked()) {
                        dir = map.get(i);
                        selected = true;
                    }
                }
                if(selected){
                    state.setAirDirection(dir);

                    Toast.makeText(getApplicationContext(),
                            "Κατεύθυνση αέρα: " + state.getAirDirection().toLowerCase() + '.',
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.putExtra("dir", state.getAirDirection());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                else{
                    Utilities.popUpDone(AirDirectionActivity.this);
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