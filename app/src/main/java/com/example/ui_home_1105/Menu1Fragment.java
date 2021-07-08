package com.example.ui_home_1105;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ui_home_1105.Recycler.RecyclerAdapter;
import com.example.ui_home_1105.Room.AppDatabase;
import com.example.ui_home_1105.Room.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Menu1Fragment extends Fragment {
    private final int SAVE_MEMO_ACTIVITY = 1;
    private Button btn_dialog;      //과목 추가 버튼
    private TextView text_total_time;
    private View v1;                //fragment_menu1.xml

    //리사이클러 뷰
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerAdapter adapter;
    private List<User> users;

    private AppDatabase db;
    private Calendar cal;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v1 = inflater.inflate(R.layout.fragment_menu1, container, false);

        db = AppDatabase.getInstance(getActivity());
        cal = Calendar.getInstance();


        String date_text = new SimpleDateFormat("yyyy.MM.dd EEEE").format((System.currentTimeMillis()));
        TextView dateNow;
        dateNow = (TextView) v1.findViewById(R.id.dateNow);
        dateNow.setText(date_text);
        text_total_time = (TextView) v1.findViewById(R.id.Total_time);
        text_total_time.setText(getTotalTime());


        recyclerView = v1.findViewById(R.id.mainRecyclerView);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        adapter = new RecyclerAdapter(getActivity(),text_total_time);

        users = AppDatabase.getInstance(getActivity()).userDao().getAll();
        int size = users.size();
        for(int i = 0; i < size; i++){
            adapter.addItem(users.get(i));
        }

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        //recyclerView.setBackgroundColor(Color.parseColor("#ffab91"));

     
        //과목 추가 버튼누르면 과목 추가화면으로 이동
        btn_dialog = (Button) v1.findViewById(R.id.btn_dialog);
        btn_dialog.setOnClickListener(v -> {
            move();
        });

        return v1;
    }

    private void move() {
        Intent intent = new Intent(getActivity(), SaveMemoActivity.class);
        startActivity(intent);
    }


    @Override
    public void onStart() {
        users = AppDatabase.getInstance(getActivity()).userDao().getDayList(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE));
        adapter.addItems((ArrayList) users);
        super.onStart();
    }

    private String getTotalTime() {
        List<User> li;
        li = db.userDao().getDayList(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE));
        int answer = 0;

        for (User i : li) {
            answer = answer + i.getSt();
        }


        int sec = (answer) % 60;
        int min = (answer) / 60 %60;
        int hour = (answer) / 3600;

        String result = String.format("%02d:%02d:%02d", hour, min, sec);
        return result;

    }

    @Override
    public void onResume() {
        super.onResume();
        text_total_time.setText(getTotalTime());
    }


}
