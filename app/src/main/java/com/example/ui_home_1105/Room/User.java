package com.example.ui_home_1105.Room;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "memoTable")
public class User implements Parcelable {

    //Room에서 자동으로 id를 할당
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    private int id;
    @ColumnInfo(name = "user_title")        //시간을 스트링으로
    private String title;
    @ColumnInfo(name = "user_des")          //과목명
    private String des;
    @ColumnInfo(name = "user_color")        //색
    private String color;
    @ColumnInfo(name = "user_st")           //초단위 시간 정수로
    private int st;
    @ColumnInfo(name = "user_year")         //날짜 저장
    private int year;
    @ColumnInfo(name = "user_month")         //날짜 저장
    private int month;
    @ColumnInfo(name = "user_day")         //날짜 저장
    private int day;


    public User(String title, String des, String color, int st, int year, int month, int day) {
        this.title = title;
        this.des = des;
        this.color = color;
        this.st = st;
        this.year = year;
        this.month = month;
        this.day = day;


    }

    protected User(Parcel in) {
        id = in.readInt();
        title = in.readString();
        des = in.readString();
        color = in.readString();
        st = in.readInt();
        year = in.readInt();
        month = in.readInt();
        day = in.readInt();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSt() {
        return st;
    }

    public void setSt(int st) {
        this.st = st;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(des);
        dest.writeString(color);
        dest.writeInt(st);
        dest.writeInt(year);
        dest.writeInt(month);
        dest.writeInt(day);


    }
}
