package com.test.toyotomi_new_era;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private Button airTypeButton, airDirectionButton, sleepModeButton, timerButton,
            airIntensityButton, helpButton;
    static State state;
    private boolean on;
    private ImageButton plusButton, minusButton, powerButton;
    private TextView temperatureText, airDirectionText, airTypeText, sleepModeText, timerText,
            airIntensityText;
    private int temperature;
    private static final String CELSIUS = "°C", AIRTYPE = "Τύπος αέρα: ",
            AIRINTENSITY = "Ένταση αέρα: ", AIRDIRECTION = "Κατεύθυνση αέρα: ",
            TIMER = "Χρονόμετρο: ", SLEEPMODE = "Λειτουργία ύπνου: ";
    private boolean exitFlag = true;
    private String timeToClose = "";


    /**
     * TODO https://stackoverflow.com/questions/14292398/how-to-pass-data-from-2nd-activity-to-1st-activity-when-pressed-back-android
     * TODO https://stackoverflow.com/questions/1124548/how-to-pass-the-values-from-one-activity-to-previous-activity
     */
    @SuppressLint("SetTextI18n")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {
                String newType = data.getStringExtra("type");
                this.airTypeText.setText(AIRTYPE + newType);
                this.airTypeText.setTextColor(Color.parseColor("#000000"));
            }
        }
        else if (requestCode == 2){
            if(resultCode == Activity.RESULT_OK) {
                String newDir = data.getStringExtra("dir");
                this.airDirectionText.setText(AIRDIRECTION + newDir);
                this.airDirectionText.setTextColor(Color.parseColor("#000000"));
            }
        }
        else if (requestCode == 3){
            if(resultCode == Activity.RESULT_OK) {
                String newInt = data.getStringExtra("intensity");
                this.airIntensityText.setText(AIRINTENSITY + newInt);
                this.airIntensityText.setTextColor(Color.parseColor("#000000"));
            }
        }
        else if (requestCode == 4){
            if(resultCode == Activity.RESULT_OK) {
                String sleep = data.getStringExtra("sleep");
                if (sleep.equalsIgnoreCase("Ενεργοποίηση")){
                    this.sleepModeText.setText(SLEEPMODE + "Ενεργοποιημένο");
                }
                else{
                    this.sleepModeText.setText(SLEEPMODE + "Απενεργοποιημένο");
                }
                this.sleepModeText.setTextColor(Color.parseColor("#000000"));

            }
        }
        else if (requestCode == 5){
            if(resultCode == Activity.RESULT_OK) {
                String newTimer = data.getStringExtra("timer");
                if(!newTimer.equalsIgnoreCase("Απενεργοποιημένο")){
                    int parameter = convertHoursToDigit(newTimer);
                    try {
                        timeToClose = expectedTimeToClose(parameter);
                        this.timerText.setText(TIMER+ "Τερματισμός στις:"+timeToClose);
                        this.timerText.setTextColor(Color.parseColor("#000000"));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }else {
                    this.timerText.setText(TIMER + newTimer);
                    this.timerText.setTextColor(Color.parseColor("#000000"));
                }

            }
        }
    }


    @Override
    public void onBackPressed() { //probably useless
        new AlertDialog.Builder(this)
                .setTitle("Απενεργοποίση κλιματιστικού:")
                .setMessage("Θέλετε σίγουρα να απενεργοποιήσετε το κλιματιστικό;")
                .setNegativeButton("Όχι", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        on = true;
                        exitFlag = false;
                    }
                })
                .setPositiveButton("Ναι", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        goodbye();

                    }
                }).create().show();
    }

    @Override
     public void onStart(){
         super.onStart();
         this.airTypeButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if (on){
                    Intent intent = new Intent(MainActivity.this,
                            AirTypeActivity.class);
                    intent.putExtra("currentType", state.getAirType());
                    startActivityForResult(intent,1);
                }
            }
         });
        this.helpButton = (Button)findViewById(R.id.βοήθεια);
        helpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // pop up help
                Utilities.popUpHelp(MainActivity.this, R.layout.help_main);
            }
        });

         this.airDirectionButton.setOnClickListener(new View.OnClickListener(){
             public void onClick(View v){
                 if (on){
                     Intent intent = new Intent(MainActivity.this,
                             AirDirectionActivity.class);
                     intent.putExtra("currentDir", state.getAirDirection());
                     startActivityForResult(intent,2);
                 }
             }
         });

         this.airIntensityButton.setOnClickListener(new View.OnClickListener(){
             public void onClick(View v){
                 if (on){
                     Intent intent = new Intent(MainActivity.this,
                             AirIntensityActivity.class);
                     intent.putExtra("currentIntensity", state.getAirIntensity());
                     startActivityForResult(intent,3);
                 }
             }
         });

         this.sleepModeButton.setOnClickListener(new View.OnClickListener(){

             @Override
             public void onClick(View v) {
                 if (on){
                     Intent intent = new Intent(MainActivity.this,
                             SleepActivity.class);
                     intent.putExtra("currentSleepMode", state.isSleepMode());
                     startActivityForResult(intent,4);
                 }
             }
         });

         this.timerButton.setOnClickListener(new View.OnClickListener(){

            @Override
             public void onClick(View v){
                if (on){
                    Intent intent = new Intent(MainActivity.this,
                            TimerActivity.class);
                    intent.putExtra("currentTimer", state.getTimer());
                    startActivityForResult(intent,5);
                }
            }
         });

         this.plusButton.setOnClickListener(new View.OnClickListener(){

             @SuppressLint("SetTextI18n")
             @Override
             public void onClick(View v){
                 if (on){
                     if(temperature<32) {
                         temperature++;
                         temperatureText.setText(temperature + CELSIUS);
                         temperatureText.setTextColor(Color.parseColor("#000000"));
                     }
                 }
             }
         });

         this.minusButton.setOnClickListener(new View.OnClickListener(){

             @SuppressLint("SetTextI18n")
             @Override
             public void onClick(View v) {
                 if (on){
                     if(temperature>16){
                         temperature--;
                         temperatureText.setText(temperature + CELSIUS);
                         temperatureText.setTextColor(Color.parseColor("#000000"));
                     }
                 }
             }
         });

         this.powerButton.setOnClickListener(new View.OnClickListener(){

             @SuppressLint("SetTextI18n")
             @Override
             public void onClick(View v){
                 if(on) { // double check with another boolean openForFirstTime
                     //onBackPressed();
                     exitMessage();
                     //goodbye();
                     if(!exitFlag){
                         airTypeText.setText(AIRTYPE);
                         airTypeText.setTextColor(Color.parseColor("#000000"));
                         airIntensityText.setText(AIRINTENSITY);
                         airIntensityText.setTextColor(Color.parseColor("#000000"));
                         sleepModeText.setText(SLEEPMODE);
                         sleepModeText.setTextColor(Color.parseColor("#000000"));
                         timerText.setText(TIMER);
                         timerText.setTextColor(Color.parseColor("#000000"));
                         airDirectionText.setText(AIRDIRECTION);
                         airDirectionText.setTextColor(Color.parseColor("#000000"));
                         temperatureText.setText(null);
                         on = false;
                     }
                 }
                 else{
                     welcome();
                     airTypeText.setText(AIRTYPE + state.getAirType());
                     airTypeText.setTextColor(Color.parseColor("#000000"));
                     airIntensityText.setText(AIRINTENSITY + state.getAirIntensity());
                     airIntensityText.setTextColor(Color.parseColor("#000000"));
                     sleepModeText.setText(SLEEPMODE + "Απενεργοποιημένο");
                     state.setSleepMode("Απενεργοποιημένο");
                     sleepModeText.setTextColor(Color.parseColor("#000000"));
                     timerText.setText(TIMER + "Απενεργοποιημένο");
                     state.setTimer("Απενεργοποιημένο");
                     timerText.setTextColor(Color.parseColor("#000000"));
                     airDirectionText.setText(AIRDIRECTION + state.getAirDirection());
                     airDirectionText.setTextColor(Color.parseColor("#000000"));
                     temperatureText.setText(state.getTemperature() + CELSIUS);
                     temperatureText.setTextColor(Color.parseColor("#000000"));
                     on = true;
                 }
             }
         });

     }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        state = new State(25, "Παντού", "Κρύος αέρας", "Μέτρια ένταση",
                "Απενεργοποιημένο", "Απενεργοποιημένο");
        //from here
        Thread myThread = null;
        Runnable myRunnableThread = new CountDownRunner();
        myThread= new Thread(myRunnableThread);
        myThread.start();
        //up to here
        this.on = false;
        this.powerButton = (ImageButton)findViewById(R.id.power_off);
        this.airTypeButton = (Button)findViewById(R.id.τύπος_αέρα);
        this.airDirectionButton = (Button)findViewById(R.id.κατεύθυνση_αέρα);
        this.sleepModeButton = (Button)findViewById(R.id.λειτουργια_υπνου);
        this.timerButton = (Button)findViewById(R.id.χρονόμετρο);
        this.airIntensityButton = (Button)findViewById(R.id.ένταση_αέρα);
        this.minusButton = (ImageButton)findViewById(R.id.minus);
        this.plusButton = (ImageButton)findViewById(R.id.plus);
        this.temperatureText = (TextView)findViewById(R.id.celsius);
        this.temperature = state.getTemperature();
        this.airTypeText = (TextView)findViewById(R.id.text_τύπος_αέρα);
        this.airIntensityText = (TextView)findViewById(R.id.text_ένταση_αέρα);
        this.airDirectionText = (TextView)findViewById(R.id.text_κατεύθυνση_αέρα);
        this.sleepModeText = (TextView)findViewById(R.id.text_λειτουργία_ύπνου);
        this.timerText = (TextView)findViewById(R.id.text_χρονόμετρο);
    }

    private void welcome()
    {
        Context context = this;

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        TextView msg = new TextView(context);
        msg.setText("\nΚΑΛΩΣΉΡΘΑΤΕ!");
        msg.setGravity(Gravity.CENTER_HORIZONTAL);
        alertDialogBuilder.setView(msg);

        //Create alert dialog
        final AlertDialog alert = alertDialogBuilder.create();
        alert.show();

        new CountDownTimer(5000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {
                alert.dismiss();
            }
        }.start();
    }
    private void exitMessage()
    {
        new AlertDialog.Builder(this)
                .setTitle("Απενεργοποίση κλιματιστικού:")
                .setMessage("Θέλετε σίγουρα να απενεργοποιήσετε το κλιματιστικό;")
                .setNegativeButton("Όχι", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        on = true;
                        exitFlag = true;
                    }
                })
                .setPositiveButton("Ναι", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        goodbye();
                        airTypeText.setText(AIRTYPE);
                        airTypeText.setTextColor(Color.parseColor("#000000"));
                        airIntensityText.setText(AIRINTENSITY);
                        airIntensityText.setTextColor(Color.parseColor("#000000"));
                        sleepModeText.setText(SLEEPMODE);
                        sleepModeText.setTextColor(Color.parseColor("#000000"));
                        timerText.setText(TIMER);
                        timerText.setTextColor(Color.parseColor("#000000"));
                        airDirectionText.setText(AIRDIRECTION);
                        airDirectionText.setTextColor(Color.parseColor("#000000"));
                        temperatureText.setText(null);
                        on = false;

                    }
                }).create().show();
    }



    private void goodbye()
    {
        Context context = this;


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        TextView msg = new TextView(context);
        msg.setText("ΤΟ ΚΛΙΜΑΤΙΣΤΙΚΌ \nΑΠΕΝΕΡΓΟΠΟΙΉΘΗΚΕ!");
        msg.setGravity(Gravity.CENTER_HORIZONTAL);
        alertDialogBuilder.setView(msg);

        //Create alert dialog
        final AlertDialog alert = alertDialogBuilder.create();
        alert.show();

        new CountDownTimer(5000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {
                alert.dismiss();
            }
        }.start();
    }


    private String expectedTimeToClose(int hoursToDigit) throws ParseException {

        Date a=new Date();

        a.setTime(System.currentTimeMillis()+(hoursToDigit));
        //Date date = sdf.parse(currentDateAndTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(a);
        //calendar.add(Calendar.HOUR, hours);
        String completeCalendar = calendar.getTime().toString();
        String calendarTime = completeCalendar.substring(11,16);
        return calendarTime;
    }

    private int convertHoursToDigit(String newTimer){
        int result = 0;
        if(newTimer.equalsIgnoreCase("30 λεπτά")) result = (60*60*1000)/2;
        else if(newTimer.equalsIgnoreCase("1 ώρα")) result = (60*60*1000);
        else if(newTimer.equalsIgnoreCase("1μιση ώρες")) result = (90*60*1000);
        else if(newTimer.equalsIgnoreCase("2 ώρες")) result = (60*60*1000)*2;
        else if(newTimer.equalsIgnoreCase("3 ώρες")) result = (60*60*1000)*3;
        else if(newTimer.equalsIgnoreCase("4 ώρες")) result = (60*60*1000)*4;
        else if(newTimer.equalsIgnoreCase("8 ώρες")) result = (60*60*1000)*8;
        else if(newTimer.equalsIgnoreCase("12 ώρες")) result = (60*60*1000)*12;
        return result;
    }
    public void doWork(final String time) {
        runOnUiThread(new Runnable() {
            public void run() {
                try{
                    Date dt = new Date();
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dt);
                    String completeCalendar = calendar.getTime().toString();
                    String curTime = completeCalendar.substring(11,19);
                    if(time.equalsIgnoreCase(curTime)){
                        goodbye();
                    }
                }catch (Exception e) {}
            }
        });
    }
//https://stackoverflow.com/questions/10634231/how-to-display-current-time-that-changes-dynamically-for-every-second-in-android
    class CountDownRunner implements Runnable{
        public void run() {
            while(!Thread.currentThread().isInterrupted()){
                try {
                    doWork(timeToClose);
                    Thread.sleep(1000); // Pause of 1 Second
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }catch(Exception e){
                }
            }
        }
    }

}
