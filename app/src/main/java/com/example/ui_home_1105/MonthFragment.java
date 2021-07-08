package com.example.ui_home_1105;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.ui_home_1105.Room.AppDatabase;
import com.example.ui_home_1105.Room.User;

import org.eazegraph.lib.models.BarModel;

public class MonthFragment extends Fragment {
    private AppDatabase db;
    private CalendarView calendarView;
    private int calendar_year,calendar_month,calendar_day;
    private int today_year,today_month,today_day;
    private MyBarChart mBarChart;

    public MonthFragment(int year, int month){
        calendar_year = year;
        calendar_month = month;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_month, container, false);
        Log.i("calendar", "MONTH!!");

        db = AppDatabase.getInstance(getActivity());

        mBarChart = (MyBarChart) v.findViewById(R.id.barchart);


        mBarChart.clearChart();
        mBarChart.onDataChanged();
        Log.i("calendar", "bar chart dayOfMonth: "+ calendar_year+ calendar_month+ calendar_day);;
        for(User i: db.userDao().getMonthList(calendar_year, calendar_month)){
            MyBarModel barModel = new MyBarModel(i.getDes(), i.getSt(), Color.parseColor(i.getColor()));
            mBarChart.addBar(barModel);
            Log.i("calendar", "bar chart dayOfMonth: ");
        }

        mBarChart.startAnimation();

        if(mBarChart.getData().size()==0){
            TextView tv = v.findViewById(R.id.textView);
            mBarChart.setVisibility(View.GONE);
            tv.setVisibility(View.VISIBLE);
        }

        return v;
    }
}