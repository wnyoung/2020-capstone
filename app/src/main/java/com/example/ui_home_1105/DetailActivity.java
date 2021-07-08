package com.example.ui_home_1105;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ui_home_1105.Room.AppDatabase;
import com.example.ui_home_1105.Room.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

import petrov.kristiyan.colorpicker.ColorPicker;

public class DetailActivity extends AppCompatActivity {
    private final int REQUEST_CODE = 200;

   // private EditText detailTitle;
    private EditText detailDes;
    private AppDatabase db;
    private Calendar cal;

    private FloatingActionButton exit;
    private FloatingActionButton update;

    private int id;
    private String title;
    private String des;

    private Button button_color;
    private String select_color;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_detail);
        cal = Calendar.getInstance();
        initialized();

        // 수정
        update.setOnClickListener(v -> {
            //문제점 : 그냥 그대로 저장이된다.
            //title = detailTitle.getText().toString();
            des = detailDes.getText().toString();

            Boolean dupl = false;
            for(User i : db.userDao().getDayList(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.get(Calendar.DATE))){
                if(des.equals(i.getDes()) && i.getId() != id) {
                    dupl = true;
                    break;
                }

            }
            if(dupl)
                Toast.makeText(getApplicationContext(),des+"과목이 이미 존재합니다.",Toast.LENGTH_SHORT).show();
            else {
                db.userDao().update_detail(des, select_color, id);
                finish();
            }

        });
        //그냥 종료
        exit.setOnClickListener(v -> {
            Intent intent = new Intent(getApplication(), MainActivity.class);
            startActivity(intent);
        });
    }

    private void initialized() {
        update = findViewById(R.id.update);
        exit = findViewById(R.id.exit);
        //detailTitle = findViewById(R.id.detailTitle);

        detailDes = findViewById(R.id.detailDes);
        db = AppDatabase.getInstance(this);

        User detail = getIntent().getParcelableExtra("data");

        id = detail.getId();
        title = detail.getTitle();
        des = detail.getDes();
        select_color = detail.getColor();

        //detailTitle.setText(title);
        detailDes.setText(des);

        button_color = findViewById(R.id.button_color);
        button_color.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                openColorPicker();
            }
        });



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
