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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WeekFragment extends Fragment {
    private AppDatabase db;
    private CalendarView calendarView;
    private int calendar_year,calendar_month,calendar_day;
    private int y1,m1,d1, y2,m2,d2;
    private int sunday,satday;
    private int today_year,today_month,today_day;
    private MyBarChart mBarChart;

    public WeekFragment(int year, int month, int day){
        calendar_year = year;
        calendar_month = month;
        calendar_day = day;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_week, container, false);
        Log.i("calendar", "WEEK!!");
        Log.i("calendar", "calendar: "+ calendar_year+ calendar_month + calendar_day);

        db = AppDatabase.getInstance(getActivity());

        mBarChart = (MyBarChart) v.findViewById(R.id.barchart);

        mBarChart.clearChart();
        mBarChart.onDataChanged();
        //Log.i("calendar", "bar chart dayOfMonth: "+ calendar_year+ calendar_month+ calendar_day);;
        getSunSatday();
        Log.i("calendar", "y1: "+ y1 + " m1: "+ m1 + " d1: " + d1 + " y2: " +  y2+ " m2: "+ m2+ " d2: "+ d2);
        if(m1!=m2) {
            for (User i : db.userDao().getWeekList(y1, m1, d1, y2, m2, d2)) {
                MyBarModel barModel = new MyBarModel(i.getDes(), i.getSt(), Color.parseColor(i.getColor()));
                mBarChart.addBar(barModel);
            }
        }else{
            for (User i : db.userDao().getWeekList(y1, m1, d1, d2)) {
                MyBarModel barModel = new MyBarModel(i.getDes(), i.getSt(), Color.parseColor(i.getColor()));
                mBarChart.addBar(barModel);
            }
        }

        mBarChart.startAnimation();

        if(mBarChart.getData().size()==0){
            TextView tv = v.findViewById(R.id.textView);
            mBarChart.setVisibility(View.GONE);
            tv.setVisibility(View.VISIBLE);
        }


        return v;
    }

    void getSunSatday(){
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, calendar_year);
        cal.set(Calendar.MONTH, calendar_month-1);
        cal.set(Calendar.DATE, calendar_day);


        int dayofyear = cal.get(Calendar.DAY_OF_YEAR);
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);


        switch(dayofweek) {
            case 1:
                sunday = dayofyear;
                satday = dayofyear + 6;
                break;
            case 2:
                sunday = dayofyear - 1;
                satday = dayofyear + 5;
                break;
            case 3:
                sunday = dayofyear - 2;
                satday = dayofyear + 4;
                break;
            case 4:
                sunday = dayofyear - 3;
                satday = dayofyear + 3;
                break;
            case 5:
                Log.i("calendar", "목");
                sunday = dayofyear - 4;
                satday = dayofyear + 2;
                break;
            case 6:
                Log.i("calendar", "금");
                sunday = dayofyear - 5;
                satday = dayofyear + 1;
                break;
            case 7:
                sunday = dayofyear - 6;
                satday = dayofyear;
                break;
            default:
                break;
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.DAY_OF_YEAR, sunday);
        y1 = cal1.get(Calendar.YEAR);
        m1 = cal1.get(Calendar.MONTH)+1;
        d1 = cal1.get(Calendar.DATE);
        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.DAY_OF_YEAR, satday);
        y2 = cal2.get(Calendar.YEAR);
        m2 = cal2.get(Calendar.MONTH)+1;
        d2 = cal2.get(Calendar.DATE);




    }
}
