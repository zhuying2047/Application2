package com.swufe.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    TextView time;
    TextView date;
    private String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        time = findViewById(R.id.time);
        date = findViewById(R.id.date);

        //获取当前时间和日期
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
        SimpleDateFormat sdf2 = new SimpleDateFormat("M月dd日");
        SimpleDateFormat sdf3 = new SimpleDateFormat("EEEE");
        final String timeStr = sdf1.format(today);
        final String dateStr = sdf2.format(today) + " "+ sdf3.format(today);

        Log.i(TAG,"当前时间为"+timeStr);
        Log.i(TAG,"当前日期和星期为"+dateStr);

        time.setText(timeStr);
        date.setText(dateStr);
    }

    /*
    启用菜单项
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu,menu);
        return true;
    }

    /*
    处理菜单事件
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.weather){
            Intent intent = new Intent(this,WeatherMainActivity.class);
            startActivity(intent);
        }else if(item.getItemId()==R.id.memo){
            Intent intent = new Intent(this,MemoMainActivity.class);
            startActivity(intent);
        }else if(item.getItemId()==R.id.calculator){
            Intent intent = new Intent(this,CalMainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
