package com.example.ui_home_1105;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ui_home_1105.Room.AppDatabase;
import com.example.ui_home_1105.Room.User;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import petrov.kristiyan.colorpicker.ColorPicker;

public class SaveMemoActivity extends AppCompatActivity {

    private final int REQUEST_CODE = 200;
    private EditText description;
    //private TextView result;
    private Button button_color;
    private Button save;
    private AppDatabase db;
    private String select_color;
    private int today_year,today_month,today_day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_memo);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        select_color = "#ffab91";

        initialized();
    }

    private void initialized() {

        GregorianCalendar toDayMan = new GregorianCalendar();
        today_year = toDayMan.get(toDayMan.YEAR);  //년
        today_month = toDayMan.get(toDayMan.MONTH)+1;//월
        today_day = toDayMan.get(toDayMan.DAY_OF_MONTH); // 일 int 값으로 불러오기
        //Log.i("calendar", "SaveMemoActivity year: "+ today_year + ", month: "+ today_month+", dayOfMonth: "+ today_day);

        description = findViewById(R.id.description);
        //result = findViewById(R.id.result);

        db = AppDatabase.getInstance(this);

        save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                make_title();
            }
        });


        button_color = findViewById(R.id.button_color);
        button_color.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                openColorPicker();
            }
        });

    }

//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        switch (item.getItemId()) {
//            case R.id.save:
//                make_title();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
    private void make_title() {

        //EditText editText = new EditText(getApplicationContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("저장하시겠습니까?");
        //builder.setView(editText);

        builder.setPositiveButton("저장", (dialog, which) -> {
            //String s = editText.getText().toString();
            String s="00:00:00";
            // db에 저장하기
            String des = description.getText().toString();
            User memo = new User(s, des, select_color, 0, today_year, today_month, today_day);
            Boolean dupl = false;
            for(User i : db.userDao().getDayList(today_year, today_month, today_day)){
                if(des.equals(i.getDes())) {
                    dupl = true;
                    break;
                }

            }
            if(dupl)
                Toast.makeText(getApplicationContext(),des+"과목이 이미 존재합니다.",Toast.LENGTH_SHORT).show();
            else {
                db.userDao().insert(memo);
                Toast.makeText(getApplicationContext(), description.getText().toString() + "과목이 저장되었습니다", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                finish();
            }
        });

        builder.setNegativeButton("취소", (dialog, which) -> {
            dialog.dismiss();
        });

        builder.show();
    }

    public void openColorPicker() {
        final ColorPicker colorPicker = new ColorPicker(this);  // ColorPicker 객체 생성
        ArrayList<String> colors = new ArrayList<>();  // Color 넣어줄 list

        colors.add("#ffab91");
        colors.add("#F48FB1");
        colors.add("#ce93d8");
        colors.add("#b39ddb");
        colors.add("#9fa8da");
        colors.add("#90caf9");
        colors.add("#81d4fa");
        colors.add("#80deea");
        colors.add("#80cbc4");
        colors.add("#c5e1a5");
        colors.add("#e6ee9c");
        colors.add("#fff59d");
        colors.add("#ffe082");
        colors.add("#ffcc80");
        colors.add("#bcaaa4");

        colorPicker.setColors(colors)  // 만들어둔 list 적용
                .setDefaultColorButton(-21615)
                .setColumns(5)  // 5열로 설정
                .setRoundColorButton(true)  // 원형 버튼으로 설정
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
                        button_color.setBackgroundColor(Color.parseColor(colors.get(position)));  // OK 버튼 클릭 시 이벤트
                        //button_color.setText(colors.get(position));  // OK 버튼 클릭 시 이벤트
                        select_color=colors.get(position);
                    }

                    @Override
                    public void onCancel() {
                        // Cancel 버튼 클릭 시 이벤트
                    }
                }).show();  // dialog 생성
    }


}