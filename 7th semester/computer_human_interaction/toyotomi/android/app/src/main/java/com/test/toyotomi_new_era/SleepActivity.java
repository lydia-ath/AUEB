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

public class SleepActivity extends AppCompatActivity {

    private CheckBox[] choices;
    private Button done, cancel, help;
    private boolean[] overlap;
    private String mode;
    private HashMap<Integer, String> map;
    private boolean selected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);
        String incoming = getIntent().getStringExtra("currentSleepMode");
        System.out.println(incoming);
        if (incoming.equalsIgnoreCase("Απενεργοποίηση")||incoming.equalsIgnoreCase("Απενεργοποιημένο")){
            incoming = "Απενεργοποίηση";
        }
        else{
            incoming = "Ενεργοποίηση";
        }
        this.done = (Button)findViewById(R.id.done);
        this.cancel = (Button)findViewById(R.id.cancel);

        this.help = (Button)findViewById(R.id.βοήθεια);
        help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // pop up help
                Utilities.popUpHelp(SleepActivity.this, R.layout.help_sleep);
            }
        });
        this.selected = false;
        this.choices = new CheckBox[2];
        this.choices[0] = (CheckBox) findViewById(R.id.activate);
        this.choices[1] = (CheckBox) findViewById(R.id.reactivate);
        for (CheckBox b : this.choices) {
            b.setOnClickListener(mListener);
        }
        this.overlap = new boolean[2];
        Arrays.fill(this.overlap, false);
        this.map = new HashMap<>();
        this.map.put(0,"Ενεργοποίηση");
        this.map.put(1, "Απενεργοποίηση");

        int key = Utilities.getKeyByValue(this.map, incoming);
        this.choices[key].setChecked(true);
        this.overlap[key] = true;

        this.setMode();
    }

    private final View.OnClickListener mListener = new View.OnClickListener() {

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

    private void setMode() {
        this.done.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                for (int i = 0; i < choices.length; i++) {
                    if (choices[i].isChecked()) {
                        mode = map.get(i);
                        selected = true;
                    }
                }
                if (selected) {
                    state.setSleepMode(mode);

                    Toast.makeText(getApplicationContext(),
                            "Επιλέξατε: "+ state.isSleepMode().toLowerCase() + '.',
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent();
                    intent.putExtra("sleep", state.isSleepMode());
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                } else {
                    Utilities.popUpDone(SleepActivity.this);
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
