package com.example.ui_home_1105;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.ui_home_1105.Room.AppDatabase;
import com.example.ui_home_1105.Room.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.eazegraph.lib.models.BarModel;

import java.util.GregorianCalendar;

public class Menu2Fragment extends Fragment {
    private AppDatabase db;
    private View v2;
    private CalendarView calendarView;
    private int today_year,today_month,today_day;
    private int calendar_year,calendar_month,calendar_day;
    private MyBarChart mBarChart;

    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private BottomNavigationView statisticNavigationView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        GregorianCalendar toDayMan = new GregorianCalendar();
        today_year = toDayMan.get(toDayMan.YEAR);  //년
        today_month = toDayMan.get(toDayMan.MONTH)+1;//월
        today_day = toDayMan.get(toDayMan.DAY_OF_MONTH); // 일 int 값으로 불러오기
        calendar_year = today_year;
        calendar_month = today_month;
        calendar_day = today_day;

        v2 = inflater.inflate(R.layout.fragment_menu2, container, false);
        db = AppDatabase.getInstance(getActivity());
        calendarView = (CalendarView) v2.findViewById(R.id.calendarView);
        fragmentManager = getActivity().getSupportFragmentManager();
        statisticNavigationView = v2.findViewById(R.id.statistic_navigation_view);
        // 첫 화면 지정
        transaction = fragmentManager.beginTransaction();
        switch (statisticNavigationView.getSelectedItemId()) {
            case R.id.navigation_day: {
                transaction.replace(R.id.frame_layout2, new DayFragment(calendar_year, calendar_month, calendar_day)).commitAllowingStateLoss();

                break;
            }
            case R.id.navigation_week: {
                transaction.replace(R.id.frame_layout2, new WeekFragment(calendar_year, calendar_month, calendar_day)).commitAllowingStateLoss();
                break;
            }
            case R.id.navigation_month: {
                transaction.replace(R.id.frame_layout2, new MonthFragment(calendar_year, calendar_month)).commitAllowingStateLoss();
                break;
            }
        }


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                calendar_year = year;
                calendar_month = (month + 1);
                calendar_day = dayOfMonth;
                //Log.i("calendar", "menu: "+ statisticNavigationView.getSelectedItemId());
                transaction = fragmentManager.beginTransaction();
                switch (statisticNavigationView.getSelectedItemId()) {
                    case R.id.navigation_day: {
                        transaction.replace(R.id.frame_layout2, new DayFragment(calendar_year, calendar_month, calendar_day)).commitAllowingStateLoss();
                        Log.i("calendar", "day go: ");
                        break;
                    }
                    case R.id.navigation_week: {
                        transaction.replace(R.id.frame_layout2, new WeekFragment(calendar_year, calendar_month, calendar_day)).commitAllowingStateLoss();
                        Log.i("calendar", "week go: ");
                        break;
                    }
                    case R.id.navigation_month: {
                        transaction.replace(R.id.frame_layout2, new MonthFragment(calendar_year, calendar_month)).commitAllowingStateLoss();
                        break;
                    }
                }
            }
        });





        // bottomNavigationView의 아이템이 선택될 때 호출될 리스너 등록
        statisticNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_day: {
                        transaction.replace(R.id.frame_layout2, new DayFragment(calendar_year, calendar_month, calendar_day)).commitAllowingStateLoss();

                        break;
                    }
                    case R.id.navigation_week: {
                        transaction.replace(R.id.frame_layout2, new WeekFragment(calendar_year, calendar_month, calendar_day)).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_month: {
                        transaction.replace(R.id.frame_layout2, new MonthFragment(calendar_year, calendar_month)).commitAllowingStateLoss();
                        break;
                    }
                }
                return true;
            }
        });




        return v2;
    }
    @Override
    public void onResume() {
        super.onResume();
        transaction = fragmentManager.beginTransaction();
        switch (statisticNavigationView.getSelectedItemId()) {
            case R.id.navigation_day: {
                transaction.replace(R.id.frame_layout2, new DayFragment(calendar_year, calendar_month, calendar_day)).commitAllowingStateLoss();

                break;
            }
            case R.id.navigation_week: {
                transaction.replace(R.id.frame_layout2, new WeekFragment(calendar_year, calendar_month, calendar_day)).commitAllowingStateLoss();
                break;
            }
            case R.id.navigation_month: {
                transaction.replace(R.id.frame_layout2, new MonthFragment(calendar_year, calendar_month)).commitAllowingStateLoss();
                break;
            }
        }
    }

}
