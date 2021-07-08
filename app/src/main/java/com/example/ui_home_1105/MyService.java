package com.example.ui_home_1105;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import static android.content.ContentValues.TAG;

public class MyService extends Service {
    Thread thread;
    boolean isThread = false;
    Object view;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        clickBtn();

//여기서 감지
        Log.d(TAG, "Home Button Touch33");
        Log.d(TAG, "Home Button Touch11");

        isThread = true;
        thread = new Thread() {
            public void run() {
                while (isThread) {
                    try {                               //try~catch구문은 오류가 발생할 것 같은 구문으로 오류를 예외처리해줌
                        sleep(5000); //5초 동안 잠시 쉬어라
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.sendEmptyMessage(0);
                }
            }
        };
        thread.start();

        Log.d(TAG, "Home Button Touch22");
        Log.d(TAG, "Home Button Touch00");

        return super.onStartCommand(intent, flags, startId);
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Toast.makeText(getApplicationContext(), "딴짓 알림(5분 간격으로 울립니다.)", Toast.LENGTH_SHORT).show();


            //isThread = false;
        }
    };

    private void clickBtn() {
        Log.d(TAG, "Home Button Touch55");

        //알림(Notification)을 관리하는 관리자 객체를 운영체제(Context)로부터 소환하기
        NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //Notification 객체를 생성해주는 건축가객체 생성(AlertDialog 와 비슷)
        NotificationCompat.Builder builder= null;

        Log.d(TAG, "Home Button Touch44");

        //Oreo 버전(API26 버전)이상에서는 알림시에 NotificationChannel 이라는 개념이 필수 구성요소가 됨.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            String channelID="channel_01"; //알림채널 식별자
            String channelName="MyChannel01"; //알림채널의 이름(별명)

            //알림채널 객체 만들기
            NotificationChannel channel= new NotificationChannel(channelID,channelName,NotificationManager.IMPORTANCE_DEFAULT);

            //알림매니저에게 채널 객체의 생성을 요청
            notificationManager.createNotificationChannel(channel);

            //알림건축가 객체 생성
            builder=new NotificationCompat.Builder(this, channelID);


        }else{
            //알림 건축가 객체 생성
            builder= new NotificationCompat.Builder(this, null);
        }


        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);




//        Intent resultIntent = new Intent(this, subactivity.class);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
//        stackBuilder.addParentStack( subactivity.class );
//        stackBuilder.addNextIntent(resultIntent);
//        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

//        Intent notiIconClickIntent = new Intent(this, subactivity.class);
//        notiIconClickIntent .putExtra("particularFragment", "notiIntent");
//        PendingIntent pendingIntent = PendingIntent.getActivity(this,101,notiIconClickIntent,PendingIntent.FLAG_UPDATE_CURRENT);

        //isThread = false;
        builder.setAutoCancel(true);        //공부는 엉덩이 싸움 notification 클릭하면 subactivity로 들어감과 동시에 알림창에서 notification 사라짐
        builder.setSmallIcon(getNotificationIcon());


        //상태바를 드래그하여 아래로 내리면 보이는
        //알림창(확장 상태바)의 설정
        builder.setContentTitle("공부는 엉덩이 싸움");//알림창 제목
        builder.setContentText("딴짓 중.....");//알림창 내용


        //알림창의 큰 이미지
        Bitmap bm= BitmapFactory.decodeResource(getResources(), R.drawable.mainicon);
        builder.setLargeIcon(bm);//매개변수가 Bitmap을 줘야한다.
        builder.setContentIntent(pendingIntent);




        //건축가에게 알림 객체 생성하도록
        Notification notification=builder.build();

        //알림매니저에게 알림(Notify) 요청
        notificationManager.notify(1, notification);
    }

    private int getNotificationIcon() {
        boolean useWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
        return useWhiteIcon ? R.drawable.mainicon : R.drawable.mainicon;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isThread = false;

//        isThread = false;
//        thread.interrupt();

    }









}