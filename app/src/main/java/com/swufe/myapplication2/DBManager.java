package com.swufe.myapplication2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private DBHelper dbHelper;
    private String TBNAME;

    public DBManager(Context context) {
        dbHelper = new DBHelper(context);
        TBNAME = DBHelper.TB_NAME;
    }

    /*
     *添加一行数据
     */
    public void add(MemoItem item){
        //获得数据库
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //构造一个ContentValues对象
        ContentValues values = new ContentValues();
        //把数据放到列名为“content”的列中
        values.put("content",item.getContent());
        values.put("date",item.getDate());
        db.insert(TBNAME,null,values);
        db.close();
    }

    /*
     *添加多行数据
     */
    public void addAll(List<MemoItem> list){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for(MemoItem item : list){
            ContentValues values = new ContentValues();
            values.put("content",item.getContent());
            values.put("date",item.getDate());
            db.insert(TBNAME,null,values);
        }
        db.close();
    }

    /*
     *删除所有数据
     */
    public void deleteAll(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME,null,null);
        db.close();
    }

    /*
    删除一行数据
     */
    public void delete(String s1){
        //使用数据库对象调用delete()方法。各参数分别表示
        //参数1：表名
        //参数2：条件语句
        //参数3：条件占位符填充
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(TBNAME,"content=?",new String[]{s1});
    }

    /*
     *显示所有数据
     */
    public List<MemoItem> listAll(){
        List<MemoItem> memoList = null;
        //获取只读数据库
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //查询表里的所有数据
        Cursor cursor = db.query(TBNAME,null,null,null,null,null,null);
        //当表不为空时，将数据装载到列表rateList中
        if(cursor!=null){
            memoList = new ArrayList<MemoItem>();
            //将光标移到下一行，一开始光标指向列名那一行
            while(cursor.moveToNext()){
                //rateItem是行数据对象，将行数据存到rateList中
                MemoItem item = new MemoItem();
                item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                item.setContent(cursor.getString(cursor.getColumnIndex("CONTENT")));
                item.setDate(cursor.getString(cursor.getColumnIndex("DATE")));
                memoList.add(item);
            }
            cursor.close();
        }
        db.close();
        return memoList;
    }

    /*
    更新一行数据
     */
    public void update(MemoItem item,String con){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("content",item.getContent());
        values.put("date",item.getDate());
        db.update(TBNAME,values,"content=?",new String[]{con});
        db.close();
    }
}
