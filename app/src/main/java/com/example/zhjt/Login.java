package com.example.zhjt;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhjt.Utils.PlayMusic;
import com.example.zhjt.Utils.RememberName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Login extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Login";
    private EditText username;
    private EditText password;
    private Button login;
    private Button register;
    private TextView unpass;
    private TextView a;

    //实现记录上一次的用户名
    private RememberName sp = new RememberName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.button_login);
        register = (Button) findViewById(R.id.button_register);
        unpass = (TextView) findViewById(R.id.unpass);
        unpass.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        login.setOnClickListener(this);
        register.setOnClickListener(this);

        //获取实例
        String spname = sp.getName("username", this);
        if (!spname.equals("")) {
            username.setText(spname);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:
                getOkhttp();
                break;
            case R.id.button_register:
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void getJsonLoginData(String data) {
        try {
            JSONArray jsonArray = new JSONArray(data);
            int k = -1;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String name = jsonObject.getString("name");
                String pass = jsonObject.getString("pass");
                if (username.getText().toString().equals(name) && password.getText().toString().equals(pass)) {
                    k = 1;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            sp.setName("username", Login.this, username.getText().toString());
                            Toast.makeText(Login.this, "登录成功！", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                }
            }
            if (k == -1) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(Login.this, "用户名或密码错误！", Toast.LENGTH_LONG).show();
                    }
                });

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    private void getOkhttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://192.168.3.102:9090/login.json")
                            .build();
                    Response response = client.newCall(request).execute();
                    String reader = response.body().string();
                    getJsonLoginData(reader);
                } catch (IOException e) {
                    Toast.makeText(Login.this, "请检查您的网络是否连接", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
