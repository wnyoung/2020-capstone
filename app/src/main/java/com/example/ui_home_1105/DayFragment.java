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

import java.util.GregorianCalendar;

public class DayFragment extends Fragment {
    private AppDatabase db;
    private CalendarView calendarView;
    private int calendar_year,calendar_month,calendar_day;
    private int today_year,today_month,today_day;
    private MyBarChart mBarChart;

    public DayFragment(int year, int month, int day){
        calendar_year = year;
        calendar_month = month;
        calendar_day = day;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_day, container, false);
        //Log.i("calendar", "DAY!!");

        db = AppDatabase.getInstance(getActivity());

        mBarChart = (MyBarChart) v.findViewById(R.id.barchart);

        //Log.i("calendar", "DAY2!!");

        mBarChart.clearChart();
        mBarChart.onDataChanged();
        //Log.i("calendar", "bar chart dayOfMonth: "+ calendar_year+ calendar_month+ calendar_day);;
        for(User i: db.userDao().getDayList(calendar_year, calendar_month, calendar_day)){
            MyBarModel barModel = new MyBarModel(i.getDes(), i.getSt(), Color.parseColor(i.getColor()));
            mBarChart.addBar(barModel);
            //Log.i("calendar", "bar chart dayOfMonth: ");
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
