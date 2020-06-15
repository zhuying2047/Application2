package com.swufe.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.HashMap;

public class WeatherMainActivity extends AppCompatActivity implements Runnable{

    TextView currentTemp;
    TextView tempRange;
    ListView lifeAdvice;
    private SimpleAdapter listitemAdapter;
    private ArrayList<HashMap<String,String>> listItems ;
    Handler handler;
    private String TAG = "WeatherMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_main);

        currentTemp = findViewById(R.id.current_temp);
        tempRange = findViewById(R.id.range_temp);
        lifeAdvice = findViewById(R.id.life_advice);

        //开启子线程
        Thread t  = new Thread(this);
        t.start();

        //获取子线程传过来的数据
        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==5){
                    //获取msj.obj中的数据
                    Bundle bdl =(Bundle) msg.obj;
                    String temp = bdl.getString("temp");
                    String range = bdl.getString("range");
                    Log.i(TAG,"handleMessage:temp:"+temp);
                    Log.i(TAG,"handleMessage:range:"+range);
                    currentTemp.setText(temp);
                    tempRange.setText(range);
                    //将listItems和列表布局连接起来
                    listitemAdapter = new SimpleAdapter(WeatherMainActivity.this,listItems,R.layout.temp_item,
                            new String[]{"title","content"},new int[]{R.id.advice_title,R.id.advice_content});

                    lifeAdvice.setAdapter(listitemAdapter);
                }
                super.handleMessage(msg);
            }
        };
    }

    @Override
    public void run() {
        Bundle bundle = new Bundle();
        Document doc = null;
        try {
            doc = (Document) Jsoup.connect("http://tianqi.2345.com/chengdu1d/56294.htm").get();
            Log.i(TAG,"run:"+doc.title());

            Elements nowtemp = doc.getElementsByClass("real-mess");
            Element nowdata = nowtemp.get(0);
            Elements temps = nowdata.getElementsByTag("div");
            Element element1 = temps.get(1);
            Element element2 = temps.get(2);

            //获取到当前温度和温度范围
            String strTemp = element1.text();
            String strRange = element2.text();
            bundle.putString("temp",strTemp);
            bundle.putString("range",strRange);
            Log.i(TAG,"run:"+strTemp);
            Log.i(TAG,"run:"+strRange);

            //获取生活指数
            Elements tips = doc.getElementsByClass("live-tips-box");
            Element element3 = tips.get(0);
            Elements shortTips = element3.getElementsByClass("m-tips");
            Elements longTips = element3.getElementsByClass("advance");
            //0-9是需要的建议
            listItems = new ArrayList<HashMap<String, String>>();
            for(int i=0;i<10;i++){
                Element tip1 = shortTips.get(i);
                Element tip2 = longTips.get(i);
                String str1 = tip1.text();
                String str2 = tip2.text();
                HashMap<String,String> map = new HashMap<String,String>();
                map.put("title",str1);
                map.put("content",str2);
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
}
