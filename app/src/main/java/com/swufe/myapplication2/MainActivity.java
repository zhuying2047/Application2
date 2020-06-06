package com.swufe.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
