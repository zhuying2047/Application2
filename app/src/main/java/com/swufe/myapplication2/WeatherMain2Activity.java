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
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class WeatherMain2Activity extends AppCompatActivity implements Runnable{

    TextView nowdate;
    TextView nowtemp;
    private String TAG = "WeatherMain2Activity";
    public String next;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_main2);

        nowdate = findViewById(R.id.date);
        nowtemp = findViewById(R.id.temp);

        //开启子线程
        Thread t  = new Thread(this);
        t.start();

        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {

            }
        };

    }

    @Override
    public void run() {
        Document doc = null;
        try {
            doc = (Document) Jsoup.connect("http://tianqi.2345.com/").get();
            Log.i(TAG, "run:" + doc.title());

            Elements temp = doc.getElementsByClass("info-box");
            Element nowdata = temp.get(0);

            //获取时间
            Elements dates = nowdata.getElementsByTag("p");
            Element date = dates.get(0);
            Log.i(TAG, "run:" + date.text());
            //nowdate.setText(date.text());

            //获取天气情况
            Elements temps = nowdata.getElementsByTag("a");
            Element element1 = temps.get(3);
            String strTemp = element1.text();
            Log.i(TAG, "run:" + strTemp);

            //获取到接下来要转到的页面地址
            String attr = element1.attr("href");
            next = "http://tianqi.2345.com" + attr;
            Log.i(TAG, "run:" + next);

            //爬取24小时天气放在列表中


            //把时间和天气情况传到主线程


//            Element element2 = temps.get(2);
//            int i=0;
//            for(Element element : temps){
//                Log.i(TAG,"run: table["+i+"]=" + element);
//                i++;
//            }



//            Element nowdata = nowtemp.get(0);
//            Elements temps = nowdata.getElementsByTag("div");
//            Element element1 = temps.get(1);
//            Element element2 = temps.get(2);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("").setMessage("是否了解更多?")
//                .setPositiveButton("是", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String url = next;
//                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                        intent.setData(Uri.parse(url));
//                        WeatherMain2Activity.this.startActivity(intent);
//                    }
//                })
//                .setNegativeButton("否",null);
//
//        builder.create().show();

    }
