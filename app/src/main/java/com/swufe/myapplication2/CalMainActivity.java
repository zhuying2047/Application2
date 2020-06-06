package com.swufe.myapplication2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class CalMainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0; // 数字1234567890
    Button add, sub, mul, div; //加减乘除
    Button dot, equ; // 小数点，=号
    Button clear; //清除
    EditText result; // 显示文本
    boolean clr_flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cal_main);

        // 获取页面上的控件
        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);
        btn5 = findViewById(R.id.btn_5);
        btn6 = findViewById(R.id.btn_6);
        btn7 = findViewById(R.id.btn_7);
        btn8 = findViewById(R.id.btn_8);
        btn9 = findViewById(R.id.btn_9);
        btn0 = findViewById(R.id.btn_0);
        add = findViewById(R.id.btn_add);
        sub = findViewById(R.id.btn_sub);
        mul = findViewById(R.id.btn_mul);
        div = findViewById(R.id.btn_div);
        equ = findViewById(R.id.btn_equ);
        dot = findViewById(R.id.btn_dot);
        clear = findViewById(R.id.btn_clear);
        result = findViewById(R.id.et_result);

        //为每个控件设置监听
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn6.setOnClickListener(this);
        btn7.setOnClickListener(this);
        btn8.setOnClickListener(this);
        btn9.setOnClickListener(this);
        btn0.setOnClickListener(this);
        add.setOnClickListener(this);
        sub.setOnClickListener(this);
        mul.setOnClickListener(this);
        div.setOnClickListener(this);
        equ.setOnClickListener(this);
        dot.setOnClickListener(this);
        clear.setOnClickListener(this);
    }

    // 设置按钮点击后的监听
    public void onClick(View v){
        String str = result.getText().toString();
        switch (v.getId()){
            case  R.id.btn_0:
            case  R.id.btn_1:
            case  R.id.btn_2:
            case  R.id.btn_3:
            case  R.id.btn_4:
            case  R.id.btn_5:
            case  R.id.btn_6:
            case  R.id.btn_7:
            case  R.id.btn_8:
            case  R.id.btn_9:
            case  R.id.btn_dot:
                if(clr_flag){
                    clr_flag = false;
                    str = "";
                    result.setText("");
                }
                result.setText(str + ((Button)v).getText());
                break;
            case R.id.btn_add:
            case R.id.btn_sub:
            case R.id.btn_mul:
            case R.id.btn_div:
                if(clr_flag){
                    clr_flag = false;
                    str = "";
                    result.setText("");
                }

            if(str.contains("+")||str.contains("-")||str.contains("×")||str.contains("÷")){
                str = str.substring(0,str.indexOf(" "));
            }
                result.setText(str + " " + ((Button)v).getText() + " ");
                break;
            case R.id.btn_clear:
                if(clr_flag){
                    clr_flag = false;
                }
                str = "";
                result.setText("");
                break;
            case R.id.btn_equ:
                getResult();
                break;

        }
    }

    private void getResult(){
        String exp = result.getText().toString();
        if(exp==null||exp.equals("")){
            return;
        }
        //若获取的字符串里面不含运算符，则不用运算
        if(!exp.contains(" ")){
            return;
        }
        if(clr_flag){
            clr_flag = false;
            return;
        }
        clr_flag = true;
        //截取运算符前面的字符串
        String s1 = exp.substring(0,exp.indexOf(" "));
        //截取运算符
        String op = exp.substring(exp.indexOf(" ")+1,exp.indexOf(" ")+2);
        //截取运算符后面的字符串
        String s2 = exp.substring(exp.indexOf(" ")+3);
        double cnt = 0;
        if(!s1.equals("")&&!s2.equals("")){   //s1和s2都不为空
            double n1 = Double.parseDouble(s1);
            double n2 = Double.parseDouble(s2);
            if(op.equals("+")){
                cnt = n1 + n2;
            }
            if(op.equals("-")){
                cnt = n1 - n2;
            }
            if(op.equals("×")){
                cnt = n1 * n2;
            }
            if(op.equals("÷")){
                cnt = n1 / n2;
            }
            if(!s1.contains(".")&&!s2.contains(".")&&!op.equals("÷")){
                int res = (int)cnt;
                result.setText(res+"");
            }else{
                result.setText(cnt + "");
            }
        }else if(s1.equals("")&&!s2.equals("")){  //s1是空，s2不是空
            double n2 = Double.parseDouble(s2);
            if(op.equals("+")){
                cnt = n2;
            }
            if(op.equals("-")){
                cnt = 0 - n2;
            }
            if(op.equals("×")){
                cnt = 0;
            }
            if(op.equals("÷")){
                cnt = 0;
            }
            if(!s2.contains(".")){
                int res = (int)cnt;
                result.setText(res + "");
            }else{
                result.setText(cnt + "");
            }
        }else if(!s1.equals("")&&s2.equals("")){
            double n1 = Double.parseDouble(s1);
            cnt = n1;
            if(!s2.contains(".")){
                int res = (int)cnt;
                result.setText(res + "");
            }else{
                result.setText(cnt + "");
            }
        }
    }
}