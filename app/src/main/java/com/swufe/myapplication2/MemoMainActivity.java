package com.swufe.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MemoMainActivity extends AppCompatActivity {

    private String TAG = "MemoMainActivity";
    ListView memolist;
    private SimpleAdapter listitemAdapter;
    private ArrayList<HashMap<String,String>> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_main);

        //initListView();
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


}
