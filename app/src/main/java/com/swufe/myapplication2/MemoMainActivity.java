package com.swufe.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MemoMainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private String TAG = "MemoMainActivity";
    ListView memolist;
    private SimpleAdapter listitemAdapter;
    private ArrayList<HashMap<String,String>> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_main);

        initListView();
        memolist.setOnItemClickListener(this);
        memolist.setOnItemLongClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        initListView();
    }

    /*
    对列表的初始化，先加了几个示例数据，但是后面要从数据库里获取
    */
    private void initListView(){
        listItems = new ArrayList<HashMap<String, String>>();
        //数据库数据填充
        DBManager manager = new DBManager(MemoMainActivity.this);
        for(MemoItem rateItem:manager.listAll()){
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("content",rateItem.getContent());
            map.put("date",rateItem.getDate());
            listItems.add(map);
        }
//        for(int i = 0;i<=10;i++){
//            HashMap<String,String> map = new HashMap<String,String>();
//            map.put("content","content"+i);
//            map.put("date","date +i");
//            listItems.add(map);
//        }

        listitemAdapter = new SimpleAdapter(this,listItems,R.layout.list_item,
                new String[]{"content","date"},new int[]{R.id.list_item_body,R.id.list_item_time});

        memolist = findViewById(R.id.memo_list);
        memolist.setAdapter(listitemAdapter);
    }

    /*
    点击新建后的按钮事件，转到新建备忘录窗口
     */
    public void onClick(View btn){
        Intent intent = new Intent(this, NewmemoActivity.class);
        startActivity(intent);
    }

    /*
    列表单击事件：点击进入编辑页面EditmemoActivity
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //获取点击了的备忘录数据
        HashMap<String,String> map = listItems.get(position);
        String contentStr = map.get("content");
        String dateStr = map.get("date");

        //将数据传送到NewmemoActivity
        Intent editMemo = new Intent(this,NewmemoActivity.class);
        editMemo.putExtra("Content",contentStr);
        editMemo.putExtra("enter_state",1);
        startActivity(editMemo);

        //删除数据库里和这个内容一样的备忘录
        //DBManager manager = new DBManager(MemoMainActivity.this);
        //manager.delete(contentStr);
        //这样就要求用户无论修改与否都要点击保存
    }

    /*
    列表长按事件：出现提示框，删除与否
     */
    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

        Log.i(TAG,"onItemLongClick:长按列表项position="+position);

        //构造对话框进行确认删除操作
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("").setMessage("是否删除选中备忘录?")
                .setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i(TAG,"onClick:对话框事件处理");
                        //对列表进行更新
                        //listItems.remove(position);
                        //listitemAdapter.notifyDataSetChanged();
                        HashMap<String,String> map = listItems.get(position);
                        String content1 = map.get("content");
                        //对数据库中的数据进行删除
                        DBManager manager = new DBManager(MemoMainActivity.this);
                        manager.delete(content1);
                        initListView();
                    }
                })
                .setNegativeButton("否",null);

        builder.create().show();

        return true;

    }



}
