package com.example.ui_home_1105.Recycler;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ui_home_1105.DetailActivity;
import com.example.ui_home_1105.MainActivity;
import com.example.ui_home_1105.R;
import com.example.ui_home_1105.Room.AppDatabase;
import com.example.ui_home_1105.Room.User;
import com.example.ui_home_1105.StwchActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Dictionary;
import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {
    private ArrayList<User> userData = new ArrayList<>();
    private AppDatabase db;
    private Calendar cal;
    private Context mContext;
    private TextView v_text_total_time;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.memorecycler_itemview,parent,false);

        //view.setBackgroundColor(Color.parseColor("#ffffff"));
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.onBind(userData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return userData.size();
    }

    public void addItem(User user) {
        userData.add(user);
        notifyDataSetChanged();
    }

    public void addItems(ArrayList<User> users) {
        userData = users;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView key;
        private TextView title;
        private TextView description;
        private TextView btn_ud;
        private TextView btn_del;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            key = itemView.findViewById(R.id.key);
            title = itemView.findViewById(R.id.memoTextView1);
            description = itemView.findViewById(R.id.memoTextView2);
            btn_ud = itemView.findViewById(R.id.button_ud);
            btn_del = itemView.findViewById(R.id.button_del);

        }

        public void onBind(User user, int position) {
            String s = "" + (position+1);
            key.setText(s);
            title.setText(user.getTitle());
            description.setText(user.getDes());

            itemView.setBackgroundColor(Color.parseColor(user.getColor()));
            db = AppDatabase.getInstance(itemView.getContext());
            cal = Calendar.getInstance();

            btn_ud.setOnClickListener(v -> {

                Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
                intent.putExtra("data", user);
                itemView.getContext().startActivity(intent);
            });

            btn_del.setOnClickListener(v -> {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
                alertDialogBuilder.setMessage("삭제하시겠습니까?");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                userData.remove(user);
                                db.userDao().delete(user);
                                v_text_total_time.setText(getTotalTime());
                                notifyDataSetChanged();
                            }
                        });
                alertDialogBuilder.setNegativeButton("no",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notifyDataSetChanged();

                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            });

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), StwchActivity.class);
                intent.putExtra("data", user);
                itemView.getContext().startActivity(intent);
            });

        }
    }
    public RecyclerAdapter(Context context, TextView text_total_time) {
        mContext = context;
        v_text_total_time = text_total_time;

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

}