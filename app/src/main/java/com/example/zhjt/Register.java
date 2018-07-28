package com.example.zhjt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.zhjt.Utils.PlayMusic;

import java.util.Random;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private EditText phone;//手机，账号
    private EditText yanzheng;//短信验证码
    private Button button_yan;//验证码按钮
    private EditText et_pass;//密码
    private EditText et_pass2;//重复密码
    private CheckBox checkbox_yes;//同意协议
    private Button yesRegister;//注册按钮
    private String code = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        phone = (EditText) findViewById(R.id.et_phone);
        yanzheng = (EditText) findViewById(R.id.et_yanzheng);
        et_pass = (EditText) findViewById(R.id.et_pass);
        et_pass2 = (EditText) findViewById(R.id.et_pass2);
        checkbox_yes = (CheckBox) findViewById(R.id.checkbox_yes);
        button_yan = (Button) findViewById(R.id.button_yanzheng);
        yesRegister = (Button) findViewById(R.id.yesRegister);
        button_yan.setOnClickListener(this);
        yesRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_yanzheng://获取验证码按钮
                Random random = new Random();
                for (int i = 0; i < 4; i++) {
                    code += random.nextInt(10);
                }
                Log.d("MainActivity", "onClick: " + code);
                yanzheng.setText(code);
                code = "";
                break;
            case R.id.yesRegister:
                break;
            default:
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
