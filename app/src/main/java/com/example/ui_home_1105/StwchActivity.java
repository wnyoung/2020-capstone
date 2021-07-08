package com.example.ui_home_1105;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ui_home_1105.Room.AppDatabase;
import com.example.ui_home_1105.Room.User;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class StwchActivity  extends AppCompatActivity {
    private Boolean isFirst = true;
    private Button mStartBtn,mPauseBtn;
    private TextView mTimeTextView;
    private Thread timeThread = null;
    private Boolean isRunning = true;
    private int today_year,today_month,today_day;

    private AppDatabase db;
    private int id;
    private String title;
    private String des;
    private int st;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stwch);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

//        Intent intent = new Intent(this,MainActivity.class);
//        stopService(intent);

        GregorianCalendar toDayMan = new GregorianCalendar();
        today_year = toDayMan.get(toDayMan.YEAR);  //년
        today_month = toDayMan.get(toDayMan.MONTH)+1;//월
        today_day = toDayMan.get(toDayMan.DAY_OF_MONTH); // 일 int 값으로 불러오기

        user = getIntent().getParcelableExtra("data");
        id = user.getId();
        title = user.getTitle();
        des = user.getDes();
        st = user.getSt();

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#4ea1d3"));
        }

        mStartBtn = (Button) findViewById(R.id.btn_start);
        mPauseBtn = (Button) findViewById(R.id.btn_pause);
        mTimeTextView = (TextView) findViewById(R.id.timeView);

        mStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setVisibility(View.GONE);
                mPauseBtn.setVisibility(View.VISIBLE);

                timeThread = new Thread(new timeThread());
                timeThread.start();
            }
        });

//        mStopBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                v.setVisibility(View.GONE);
//                mRecordBtn.setVisibility(View.GONE);
//                mStartBtn.setVisibility(View.VISIBLE);
//                mPauseBtn.setVisibility(View.GONE);
//                mRecordTextView.setText("");
//                timeThread.interrupt();
//            }
//        });
//
//        mRecordBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mRecordTextView.setText(mRecordTextView.getText() + mTimeTextView.getText().toString() + "\n");
//            }
//        });

        mPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isRunning = !isRunning;
                if (isRunning) {
                    mPauseBtn.setText("일시정지");
                } else {
                    mPauseBtn.setText("시작");
                }
            }
        });
    }

    @Override
    protected void onUserLeaveHint() {
        Intent intent = new Intent(StwchActivity.this, MyService.class);
        startService(intent);


//        clickBtn();
//
////여기서 감지
//        Log.d(TAG, "Home Button Touch33");
//        Log.d(TAG, "Home Button Touch11");
//
//        isThread = true;
//        thread = new Thread() {
//            public void run() {
//                while (isThread) {
//                    try {                               //try~catch구문은 오류가 발생할 것 같은 구문으로 오류를 예외처리해줌
//                        sleep(5000); //5초 동안 잠시 쉬어라
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    handler.sendEmptyMessage(0);
//                }
//
//            }
//        };
//        thread.start();
//
//        Log.d(TAG, "Home Button Touch22");

        super.onUserLeaveHint();
        timeThread.interrupt();
        //destroy(); 어플 강제종료됨

//        Log.d(TAG, "Home Button Touch00");
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int sec = (msg.arg1) % 60;
            int min = (msg.arg1) / 60% 60;
            int hour = (msg.arg1) / 3600;
            //1000이 1초 1000*60 은 1분 1000*60*10은 10분 1000*60*60은 한시간

            @SuppressLint("DefaultLocale") String result = String.format("%02d:%02d:%02d", hour, min, sec);
            mTimeTextView.setText(result);

            db = AppDatabase.getInstance(getApplicationContext());
            db.userDao().update_stwch(result, st,today_year, today_month, today_day, id);
        }
    };

    public class timeThread implements Runnable {
        @Override
        public void run() {


            while (true) {
                while (isRunning) { //일시정지를 누르면 멈춤
                    Message msg = new Message();
                    msg.arg1 = st;
                    handler.sendMessage(msg);

                    try {
                        Thread.sleep(10);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable(){
                            @Override
                            public void run() {
                                mTimeTextView.setText("");
                                mTimeTextView.setText("00:00:00");
                            }
                        });
                        return; // 인터럽트 받을 경우 return
                    }
                    st++;
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timeThread.interrupt();
    }




   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stwch);


        Intent intent = new Intent(this,MyService.class);
        stopService(intent);
    }*/





}


