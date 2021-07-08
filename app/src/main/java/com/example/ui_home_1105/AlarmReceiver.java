package com.example.ui_home_1105;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Vibrator;

import androidx.core.app.NotificationCompat;

import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;


public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent notificationIntent = new Intent(context, stopalarm_sub.class);

        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);



        PendingIntent pendingI = PendingIntent.getActivity(context, 0,
                notificationIntent, 0);



        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default");




        //OREO API 26 이상에서는 채널 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            builder.setSmallIcon(R.drawable.ic_launcher_foreground); //mipmap 사용시 Oreo 이상에서 시스템 UI 에러남


            String channelName ="매일 알람 채널";
            String description = "매일 정해진 시간에 알람합니다.";
            int importance = NotificationManager.IMPORTANCE_HIGH; //소리와 알림메시지를 같이 보여줌

            NotificationChannel channel = new NotificationChannel("default", channelName, importance);
            channel.setDescription(description);

            channel.setVibrationPattern(new long[] {100,1000,100,1000,100,1000,100,1000,100,1000,100,1000,100,1000,100,1000,100});


//            Vibrator v;
//            v=(Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
//            long[] pattern = {100,1000,200,1000,100,1000,100,1000,100,1000,100,1000,100,1000,100,1000,100,1000,100,1000,100};
//            v.vibrate(pattern,0);


            if (notificationManager != null) {
                // 노티피케이션 채널을 시스템에 등록
                notificationManager.createNotificationChannel(channel);
            }
        }else builder.setSmallIcon(getNotificationIcon()); // Oreo 이하에서 mipmap 사용하지 않으면 Couldn't create icon: StatusBarIcon 에러남

        //      long[] v = {100,1000,100,1000,100,1000,100,1000,100,1000,100,1000,100,1000,100,1000,100,100,1000,100,1000,100,1000,100,1000,100,1000,100,1000,100,1000,100,1000,100};

//        application appState = ((MyApp)getApplicationContext());
//        String state = appState.getState();

        Vibrator v;
        v=(Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
//        v.vibrate(100,1000,200,1000,100,1000,100,1000,100,1000,100,1000);
        long[] pattern = {100,1000,200,1000,100,1000,100,1000,100,1000,100,1000};
        v.vibrate(pattern, -1);

        builder.setWhen(System.currentTimeMillis())
                .setTicker("{Time to watch some cool stuff!}")
                .setContentTitle("알람")
                .setContentText("알람 종료")
                .setContentInfo("INFO")
                .setContentIntent(pendingI)
                .setAutoCancel(true) //터치 시 자동으로 삭제할 지 여부
                .setPriority(NotificationCompat.PRIORITY_HIGH) // 알림의 중요도
                .setSmallIcon(getNotificationIcon())

                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_background));





        if (notificationManager != null) {

            // 노티피케이션 동작시킴
            notificationManager.notify(1234, builder.build());



            Calendar nextNotifyTime = Calendar.getInstance();


            //  Preference에 설정한 값 저장
            SharedPreferences.Editor editor = context.getSharedPreferences("daily alarm", MODE_PRIVATE).edit();
            editor.putLong("nextNotifyTime", nextNotifyTime.getTimeInMillis());
            editor.apply();

        }
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.mainicon : R.drawable.mainicon;
    }


}



