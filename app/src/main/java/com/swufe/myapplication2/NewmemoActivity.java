package com.swufe.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewmemoActivity extends AppCompatActivity {

    EditText newContent;
    TextView newTime;
    private String TAG = "NewmemoActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmemo);

        newContent = findViewById(R.id.new_content);
        newTime = findViewById(R.id.new_time);

        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月dd日 HH:mm");
        final String timeStr = sdf.format(today);
        newTime.setText(timeStr);

    }

    /*
    保存按钮事件处理：
    点击“保存”按钮将数据提交到数据库
     */
    public void onClick1(View btn){
        String new_content = newContent.getText().toString();
        String new_time = newTime.getText().toString();
        //如果用户没有输入内容就点击保存，提示用户
        if(new_content.length()>0){
            MemoItem memoItem = new MemoItem(new_content,new_time);
            DBManager manager = new DBManager(this);
            manager.add(memoItem);
            Log.i(TAG,"onClick1: 新备忘录已保存");
        }else{
            Toast.makeText(this,"请输入内容",Toast.LENGTH_SHORT).show();
        }

    }

    /*
    取消按钮事件处理：
    点击“取消”按钮将出现提示框，点击是直接返回到MemoMainActivity
     */
    public void onClick2(View btn){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("").setMessage("是否确认退出当前备忘录的编辑").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG,"onClick:对话框事件处理");
                Intent intent = new Intent(NewmemoActivity.this, MemoMainActivity.class);
                startActivity(intent);
            }
        }).setNegativeButton("否",null);
        builder.create().show();
    }
}
