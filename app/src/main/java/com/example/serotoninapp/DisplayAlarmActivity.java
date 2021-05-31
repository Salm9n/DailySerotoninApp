package com.example.serotoninapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DisplayAlarmActivity extends AppCompatActivity {

    private static final String TAG = "DisplayAlarmActivity";

    private TextView alarmText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_alarm);

        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        navigationView.getMenu().getItem(1).setChecked(true);

        Button setAlarm = (Button)  findViewById(R.id.alarmbutton);
        Button cancelAlarm = (Button) findViewById(R.id.cancelbutton);
        alarmText = findViewById(R.id.textView5);

        setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    Calendar mcurrentTime = Calendar.getInstance();
                    int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                    int minute = mcurrentTime.get(Calendar.MINUTE);
                    TimePickerDialog mTimePicker;
                    mTimePicker = new TimePickerDialog(DisplayAlarmActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                            Calendar cal = Calendar.getInstance();
                            cal.set(Calendar.HOUR_OF_DAY, selectedHour);
                            cal.set(Calendar.MINUTE, selectedMinute);
                            cal.set(Calendar.SECOND, 0);

                            String text = "Alarm set for: ";
                            Date date = cal.getTime();
                            DateFormat format = new SimpleDateFormat("hh:mm:ss");
                            text += format.format(date);
                            //text += DateFormat.getTimeInstance(DateFormat.SHORT).format(cal);
                            alarmText.setText(text);

                            Intent intent1 = new Intent(getApplicationContext(), AlarmReceiver.class);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
                            am.setRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
                            Toast toast = Toast.makeText(DisplayAlarmActivity.this, "Alarm set for " + cal.getTime().toString() , Toast.LENGTH_SHORT);
                            toast.show();
                            Log.d(TAG,"Alarm set for: " + cal.getTime().toString());
                        }
                    }, hour, minute, false);//Yes 24 hour time
                    mTimePicker.setTitle("Select Alarm Time");
                    mTimePicker.show();
                }

            });

        cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), AlarmReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
                AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);

                am.cancel(pendingIntent);
                alarmText.setText("No alarm set");
                Toast toast = Toast.makeText(DisplayAlarmActivity.this, "Alarm canceled" , Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        }


    public void setHome(MenuItem menuItem) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void setAlarm(MenuItem menuItem){
        Intent intent = new Intent(this,DisplayAlarmActivity.class);
        startActivity(intent);
    }

    private void updateTimeText(Calendar calendar) {
        String text = "Alarm set for: ";
        Date date = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
        String strDate = dateFormat.format(date);
        text += DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar);


        alarmText.setText(strDate);


    }
}