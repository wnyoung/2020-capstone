package com.example.ui_home_1105;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class stopalarm_sub extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopalarm_sub);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Button btn1;
        Button btn2;

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent1 = new Intent(getApplication(), MyService.class);
//                stopService(intent1);

                Intent intent = new Intent(getApplication(), stopalarm.class);
                startActivity(intent);

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MainActivity.class);
                startActivity(intent);


//                NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
//                notificationManager.cancelAll();

//                AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//                Intent intent = new Intent(getApplication(), AlarmReceiver.class);
//                PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplication(),alarmld,intent,0);
//                alarmManager.cancel(pendingIntent);
//                notificationManager.cancelAll();
//                Intent intent = new Intent(getApplication(), AlarmReceiver.class);
//                (intent);
            }
        });
    }
}