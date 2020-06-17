package com.swufe.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class WeatherMain2Activity extends AppCompatActivity implements Runnable {

    TextView nowdate;
    TextView nowtemp;
    private String TAG = "WeatherMain2Activity";
    public String next;
    ListView temp_7;
    private SimpleAdapter listitemAdapter;
    private ArrayList<HashMap<String, String>> listItems;

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_main2);

        nowdate = findViewById(R.id.date);
        nowtemp = findViewById(R.id.temp);
        temp_7 = findViewById(R.id.weather_7);

        //开启子线程
        Thread t = new Thread(this);
        t.start();

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                if (msg.what == 5) {
                    //获取msj.obj中的数据
                    Bundle bdl = (Bundle) msg.obj;
                    String date = bdl.getString("date");
                    String temp = bdl.getString("temp");
                    Log.i(TAG, "handleMessage:temp:" + date);
                    Log.i(TAG, "handleMessage:range:" + temp);
                    nowdate.setText(date);
                    nowtemp.setText(temp);
                    //将listItems和列表布局连接起来
                    listitemAdapter = new SimpleAdapter(WeatherMain2Activity.this, listItems, R.layout.temp_main,
                            new String[]{"time", "range"}, new int[]{R.id.temp_date, R.id.temp});

                    temp_7.setAdapter(listitemAdapter);

                }
            }
        };
    }

    @Override
    public void run () {
        Bundle bundle = new Bundle();
        Document doc = null;
        try {
            doc = (Document) Jsoup.connect("http://tianqi.2345.com/").get();
            Log.i(TAG, "run:" + doc.title());

            Elements temp = doc.getElementsByClass("info-box");
            Element nowdata = temp.get(0);

            //获取时间
            Elements dates = nowdata.getElementsByTag("p");
            Element date = dates.get(0);
            String date3 = date.text();
            Log.i(TAG, "run:" + date.text());

            //获取天气情况
            Elements temps = nowdata.getElementsByTag("a");
            Element element1 = temps.get(3);
            String strTemp = element1.text();
            Log.i(TAG, "run:" + strTemp);
            bundle.putString("date", date3);
            bundle.putString("temp", strTemp);

            //获取到接下来要转到的页面地址
            String attr = element1.attr("href");
            next = "http://tianqi.2345.com" + attr;
            Log.i(TAG, "run:" + next);

            //爬取24小时天气放在列表中
            Elements weather = doc.getElementsByClass("weaday7 wea-white-icon");
            Element weather2 = weather.get(0);
            Elements day = weather2.getElementsByTag("b");
            Elements date2 = weather2.getElementsByTag("em");
            Elements range = weather2.getElementsByTag("font");
            //获取到数据
            listItems = new ArrayList<HashMap<String, String>>();
            for (int i = 0; i < 7; i++) {
                //获取日期
                Element Date = date2.get(i);
                Element day2 = day.get(2 * i);
                String str1 = Date.text() + " " + day2.text();
                Log.i(TAG, "run:" + Date.text() + " " + day2.text());
                //获取未来七天天气情况和温度范围
                Element con = day.get(2 * i + 1);
                Element range2 = range.get(i);
                String str2 = con.text() + " " + range2.text();
                Log.i(TAG, "run:" + con.text() + " " + range2.text());
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("time", str1);
                map.put("range", str2);
                listItems.add(map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //通过message对象带回
        Message msg = handler.obtainMessage(5);    //msg.what = 5;
        //将需要返回到主线程的数据放到msg.obj中带到主线程
        msg.obj = bundle;
        handler.sendMessage(msg);
    }

    public void openNew(View btn){
        Intent intent = new Intent(this,WeatherMainActivity.class);
        intent.putExtra("nextPage",next);
        startActivity(intent);
    }
}

