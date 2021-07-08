package com.example.ui_home_1105.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Query("UPDATE memoTable SET user_title = :t, user_des = :d WHERE user_id =:id")
    void update(String t, String d, int id);


    @Query("UPDATE memoTable SET user_title = :t WHERE user_des = :d ")
    void update(String t, String d);


    @Query("UPDATE memoTable SET user_des = :d, user_color = :c WHERE user_id = :id ")
    void update_detail(String d, String c, int id);

    @Query("UPDATE memoTable SET user_title = :t, user_st = :st, user_year = :y, user_month = :m, user_day = :d WHERE user_id =:id")
    void update_stwch(String t, int st, int y, int m, int d, int id);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM  memoTable")
    List<User> getAll();

    @Query("DELETE FROM memoTable")
    void deleteAll();

    @Query("SELECT COUNT(*) as cnt FROM memoTable")
    int getDataCount();

    @Query("SELECT user_color FROM memoTable WHERE user_des = :d ")
    String getColor(String d);

    @Query("SELECT user_st FROM memoTable")
    int[] getAllSt();

    @Query("SELECT * FROM memoTable WHERE user_year = :y AND user_month = :m AND user_day = :d ")
    List<User> getDayList(int y, int m, int d);

    @Query("SELECT * FROM memoTable WHERE user_year = :y AND user_month = :m")
    List<User> getMonthList(int y, int m);

    @Query("SELECT * FROM memoTable WHERE (user_year = :y1 AND user_month = :m1 AND user_day>=:d1) or (user_year = :y2 AND user_month = :m2 AND user_day<=:d2)")
    List<User> getWeekList(int y1, int m1, int d1, int y2, int m2, int d2);

    @Query("SELECT * FROM memoTable WHERE (user_year = :y1 AND user_month = :m1 AND (user_day>=:d1 and user_day<=:d2))")
    List<User> getWeekList(int y1, int m1, int d1, int d2);

}

