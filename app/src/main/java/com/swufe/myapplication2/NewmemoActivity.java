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
    String content2;
    public int enter_state;
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

        //获取在主备忘录页面点击的备忘录数据
        content2= getIntent().getStringExtra("Content");
        enter_state = getIntent().getIntExtra("enter_state",0);
        newContent.setText(content2);

    }

    /*
    保存按钮事件处理：
    点击“保存”按钮将数据提交到数据库
     */
    public void onClick1(View btn){
        String new_content = newContent.getText().toString();
        String new_time = newTime.getText().toString();
        //如果从main获取的备忘录为空时，则新建备忘录，若不为空，修改备忘录
        if(enter_state==0){
            //如果用户没有输入内容就点击保存，提示用户
            if(new_content.length()>0){
                MemoItem memoItem = new MemoItem(new_content,new_time);
                DBManager manager = new DBManager(this);
                manager.add(memoItem);
                Log.i(TAG,"onClick1: 新备忘录已保存");
                finish();
                //点击保存之后又转回到主备忘录页面
                //Intent intent = new Intent(this,MemoMainActivity.class);
                //startActivity(intent);
            }else{
                Toast.makeText(this,"请输入内容",Toast.LENGTH_SHORT).show();
            }
        }else{
            MemoItem memoItem = new MemoItem(new_content,new_time);
            DBManager manager = new DBManager(this);
            manager.update(memoItem,content2);
            finish();
        }
    }

    /*
    取消按钮事件处理：
    点击“取消”按钮将出现提示框，点击是直接返回到MemoMainActivity
     */
    public void onClick2(View btn){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("").setMessage("是否退出编辑?").setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i(TAG,"onClick:对话框事件处理");
                finish();
                //点击是之后转到主备忘录界面
//                Intent intent = new Intent(NewmemoActivity.this, MemoMainActivity.class);
//                startActivity(intent);
            }
        }).setNegativeButton("否",null);
        builder.create().show();
    }
}
