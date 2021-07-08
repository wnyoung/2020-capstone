package com.example.ui_home_1105;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MyBTService extends Service {
    private int st;

    TextView mTvBluetoothStatus;
    TextView mTvReceiveData;
    TextView mTvSendData;
    Button mBtnBluetoothOn;
    Button mBtnBluetoothOff;
    Button mBtnConnect;
    Button mBtnSendData;

    BluetoothAdapter mBluetoothAdapter;
    Set<BluetoothDevice> mPairedDevices;
    List<String> mListPairedDevices;

    Handler mBluetoothHandler;
    ConnectedBluetoothThread mThreadConnectedBluetooth;
    BluetoothDevice mBluetoothDevice;
    BluetoothSocket mBluetoothSocket;

    String selectedDeviceName;
    String readMessage;

    Boolean Seatstate;
    final static int BT_REQUEST_ENABLE = 1;
    final static int BT_MESSAGE_READ = 2;
    final static int BT_CONNECTING_STATUS = 3;
    final static int BT_MESSAGE_STWCHSTART = 4;
    final static int BT_MESSAGE_ALARM = 5;
    final static UUID BT_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        Log.d("MyBTService","onBind()");
        return null;
    }

    @Override
    public void onCreate() {
        Log.d("MyBTService","onCreate()");
        super.onCreate();

        Seatstate=false;
        Log.d("MyBTService","Seatstate == false");

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mPairedDevices = mBluetoothAdapter.getBondedDevices();
        mBluetoothHandler = new Handler(){
            public void handleMessage(android.os.Message msg){
                if(msg.what == BT_MESSAGE_READ) {
                    readMessage = null;

                    readMessage = new String((byte[]) msg.obj);
                    //readMessage = new String((byte[]) msg.obj, "UTF-8");

                }
                if(msg.what == BT_MESSAGE_STWCHSTART) {     //방석 on
                    Log.d("MyBTService","BT_MESSAGE_STWCHSTART");


                    //알림(Notification)을 관리하는 관리자 객체를 운영체제(Context)로부터 소환하기
                    NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

//Notification 객체를 생성해주는 건축가객체 생성(AlertDialog 와 비슷)
                    NotificationCompat.Builder builder= null;


                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    PendingIntent pendingIntent= PendingIntent.getActivity(getApplication(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

// Log.d(TAG, "Home Button Touch44");

//Oreo 버전(API26 버전)이상에서는 알림시에 NotificationChannel 이라는 개념이 필수 구성요소가 됨.
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

                        String channelID="channel_0"; //알림채널 식별자
                        String channelName="MyChannel0"; //알림채널의 이름(별명)

//알림채널 객체 만들기
                        NotificationChannel channel= new NotificationChannel(channelID,channelName,NotificationManager.IMPORTANCE_DEFAULT);

//알림매니저에게 채널 객체의 생성을 요청
                        notificationManager.createNotificationChannel(channel);

//알림건축가 객체 생성
                        builder=new NotificationCompat.Builder(getApplication(), channelID);


                    }else{
//알림 건축가 객체 생성
                        builder= new NotificationCompat.Builder(getApplication(), null);
                    }


// Intent notiIconClickIntent = new Intent(this, Menu1Fragment.class);
// notiIconClickIntent .putExtra("particularFragment", "notiIntent");
// PendingIntent pendingIntent = PendingIntent.getActivity(this,101,notiIconClickIntent,PendingIntent.FLAG_UPDATE_CURRENT);

//isThread = false;
                    builder.setAutoCancel(true); //공부는 엉덩이 싸움 notification 클릭하면 subactivity로 들어감과 동시에 알림창에서 notification 사라짐
                    builder.setSmallIcon(getNotificationIcon());
                    builder.setContentIntent(pendingIntent);
                    builder.setVibrate(new long[] {100, 500});


//상태바를 드래그하여 아래로 내리면 보이는
//알림창(확장 상태바)의 설정
                    builder.setContentTitle("공부는 엉덩이 싸움");//알림창 제목
                    builder.setContentText("공부 시작");//알림창 내용


//알림창의 큰 이미지
                    Bitmap bm= BitmapFactory.decodeResource(getResources(), R.drawable.mainicon);
                    builder.setLargeIcon(bm);//매개변수가 Bitmap을 줘야한다.
// builder.setContentIntent(pendingIntent);






//건축가에게 알림 객체 생성하도록
                    Notification notification=builder.build();

//알림매니저에게 알림(Notify) 요청
                    notificationManager.notify(1, notification);


                }
                else if(msg.what == BT_MESSAGE_ALARM) {      //방석off
                    Log.d("MyBTService","BT_MESSAGE_ALARM");
                    //수빈이 노티

                    //알림(Notification)을 관리하는 관리자 객체를 운영체제(Context)로부터 소환하기
                    NotificationManager notificationManager=(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

//Notification 객체를 생성해주는 건축가객체 생성(AlertDialog 와 비슷)
                    NotificationCompat.Builder builder= null;


                    Intent intent = new Intent(getApplication(), stopalarm.class);
                    PendingIntent pendingIntent= PendingIntent.getActivity(getApplication(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

// Log.d(TAG, "Home Button Touch44");

//Oreo 버전(API26 버전)이상에서는 알림시에 NotificationChannel 이라는 개념이 필수 구성요소가 됨.
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

                        String channelID="channel_01"; //알림채널 식별자
                        String channelName="MyChannel01"; //알림채널의 이름(별명)

//알림채널 객체 만들기
                        NotificationChannel channel= new NotificationChannel(channelID,channelName,NotificationManager.IMPORTANCE_DEFAULT);

//알림매니저에게 채널 객체의 생성을 요청
                        notificationManager.createNotificationChannel(channel);

//알림건축가 객체 생성
                        builder=new NotificationCompat.Builder(getApplication(), channelID);


                    }else{
//알림 건축가 객체 생성
                        builder= new NotificationCompat.Builder(getApplication(), null);
                    }


// Intent notiIconClickIntent = new Intent(this, Menu1Fragment.class);
// notiIconClickIntent .putExtra("particularFragment", "notiIntent");
// PendingIntent pendingIntent = PendingIntent.getActivity(this,101,notiIconClickIntent,PendingIntent.FLAG_UPDATE_CURRENT);

//isThread = false;
                    builder.setAutoCancel(true); //공부는 엉덩이 싸움 notification 클릭하면 subactivity로 들어감과 동시에 알림창에서 notification 사라짐
                    builder.setSmallIcon(getNotificationIcon());
                    builder.setContentIntent(pendingIntent);
                    builder.setVibrate(new long[] {100, 500});


//상태바를 드래그하여 아래로 내리면 보이는
//알림창(확장 상태바)의 설정
                    builder.setContentTitle("공부는 엉덩이 싸움");//알림창 제목
                    builder.setContentText("쉬는 시간 알람");//알림창 내용


//알림창의 큰 이미지
                    Bitmap bm= BitmapFactory.decodeResource(getResources(), R.drawable.mainicon);
                    builder.setLargeIcon(bm);//매개변수가 Bitmap을 줘야한다.
// builder.setContentIntent(pendingIntent);






//건축가에게 알림 객체 생성하도록
                    Notification notification=builder.build();

//알림매니저에게 알림(Notify) 요청
                    notificationManager.notify(1, notification);


                }

            }

            private int getNotificationIcon() {
                boolean useWhiteIcon = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
                return useWhiteIcon ? R.drawable.mainicon : R.drawable.mainicon;
            }
        };
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("MyBTService","onStartCommand()");
        selectedDeviceName = intent.getStringExtra("selectedDeviceName");
        //Toast.makeText(this, "received param : "+selectedDeviceName, Toast.LENGTH_SHORT).show();

        for(BluetoothDevice tempDevice : mPairedDevices) {
            if (selectedDeviceName.equals(tempDevice.getName())) {
                //Toast.makeText(this, "received param : "+selectedDeviceName, Toast.LENGTH_SHORT).show();
                mBluetoothDevice = tempDevice;
                break;
            }
        }
        try {

            mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(BT_UUID);
            mBluetoothSocket.connect();
            //Toast.makeText(this, "11 : "+selectedDeviceName, Toast.LENGTH_SHORT).show();

            mThreadConnectedBluetooth = new ConnectedBluetoothThread(mBluetoothSocket);
            mThreadConnectedBluetooth.start();
            Toast.makeText(this, "방석에 연결 되었습니다.", Toast.LENGTH_LONG).show();
            //mBluetoothHandler.obtainMessage(BT_CONNECTING_STATUS, 1, -1).sendToTarget();
        } catch (IOException e) {
            Toast.makeText(this, "블루투스 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
        }

        //Toast.makeText(this, "received param : "+param, Toast.LENGTH_SHORT).show();

        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {
        Log.d("MyBTService","onDestroy()");
        super.onDestroy();
    }

    private class ConnectedBluetoothThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedBluetoothThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {

                Toast.makeText(getApplicationContext(), "소켓 연결 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            Log.d("MyBTService","thread running");
            while (true) {
                try {
                    bytes = mmInStream.available();
                    if (bytes != 0) {
                        SystemClock.sleep(50);
                        bytes = mmInStream.available();

                        bytes = mmInStream.read(buffer, 0, bytes);
                        //mBluetoothHandler.obtainMessage(BT_MESSAGE_READ, bytes, -1, buffer).sendToTarget();

                        readMessage = new String(buffer, 0, 3, "UTF-8");

                        int sensorValue =0;
                        if(isNum(readMessage.substring(2, 3))) {
                            sensorValue = Integer.parseInt(readMessage);
                        }else if(isNum(readMessage.substring(1, 2))){
                            sensorValue = Integer.parseInt(readMessage.substring(1, 2))*10 + Integer.parseInt(readMessage.substring(0, 1));
                        }else{
                            sensorValue = Integer.parseInt(readMessage.substring(0, 1));
                        }

                        //Log.d("MyBTService", "intege: readMessage" + readMessage.substring(1, 2));
                        //}
                        Log.d("MyBTService", "readMessage " + readMessage);
                        Log.d("MyBTService", "sensorValue " + Integer.toString(sensorValue));
                        //Integer.parseInt(readMessage);




                        if(sensorValue>300){

                            if(Seatstate ==false) {
                                //st =0;
                                Log.d("MyBTService", "방석 on");
                                mBluetoothHandler.obtainMessage(BT_MESSAGE_STWCHSTART, sensorValue, -1).sendToTarget();
                                Seatstate = true;
                                Log.d("MyBTService", "Seatstate == true");
                            }
                            //st++;
                        }
                        if(sensorValue<=300 && Seatstate ==true) {
                            Log.d("MyBTService", "방석 off");
                            mBluetoothHandler.obtainMessage(BT_MESSAGE_ALARM, sensorValue, -1).sendToTarget();
                            Seatstate = false;
                            Log.d("MyBTService","Seatstate == false");
                        }
                        //Log.d("MyBTService", "STWCHe =="+ Integer.toString(st));
                    }
                } catch (IOException e) {
                    break;
                }
            }
        }
        //        public void write(String str) {
//            byte[] bytes = str.getBytes();
//            try {
//                mmOutStream.write(bytes);
//            } catch (IOException e) {
//                Toast.makeText(this, "데이터 전송 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
//            }
//        }
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "소켓 해제 중 오류가 발생했습니다.", Toast.LENGTH_LONG).show();
            }
        }
    }

    Boolean isNum(String str){
        switch(str){
            case "0" : case "1" : case "2" : case "3" : case "4" : case "5" : case "6" : case "7" : case "8" : case "9" :
                return true;
            default:
                return false;
        }
    }


}
