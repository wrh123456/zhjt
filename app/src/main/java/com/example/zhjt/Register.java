package com.example.zhjt;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.mob.MobSDK;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Random;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private EditText phone;//手机，账号
    private EditText yanzheng;//短信验证码
    private Button button_yan;//验证码按钮
    private EditText et_pass;//密码
    private EditText et_pass2;//重复密码
    private CheckBox checkbox_yes;//同意协议
    private Button yesRegister;//注册按钮
    private String code = "";
    private EventHandler eh;
    private boolean b3=true;
    private Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event=msg.arg1;
            int result=msg.arg2;
            Object data=msg.obj;
            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    if (data!=null){
                        Log.d("result",data.toString());
                    }else
                        Log.d("result","one data null.");
                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    //获取验证码成功
                    if (data!=null){
                        Log.d("result",data.toString());
                    }else
                        Log.d("result","two data null");
                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                    //返回支持发送验证码的国家列表
                }
            }else{
                b3=false;
                ((Throwable)data).printStackTrace();
            }

        }
    };

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

        MobSDK.init(this);//初始化

    }

    private void getOkhttp() {//通过服务器存储账号密码
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody=new FormBody.Builder()
                            .add("name",phone.getText().toString())
                            .add("pass",et_pass.getText().toString())
                            .build();
                    Request request = new Request.Builder()
                            .url("http://192.168.3.102:9090/ZHBJ/AdminRegisterServlet")
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    String reader = response.body().string();
                    if(reader.equals("1")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Register.this, "存储成功！", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent();
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setClass(Register.this,Login.class);
                                startActivity(intent);
                            }
                        });
                    }else if(reader.equals("2")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Register.this, "用户名或密码错误1！", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                    else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Register.this, "用户名或密码错误2！", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                } catch (IOException e) {
                    Toast.makeText(Register.this, "请检查您的网络是否连接", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void initdata(){
        //注册回调监听，放到发送和验证前注册，注意这里是子线程需要传到主线程中去操作后续提示
        eh=new EventHandler(){
            @Override
            public void afterEvent(final int event, final int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                mhandler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eh);
        // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
        SMSSDK.getVerificationCode("86",phone.getText().toString());

    }//发送短信

    public boolean Verification(){
        if(TextUtils.isEmpty(phone.getText().toString())){
            Toast.makeText(Register.this,"手机号不能为空",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(yanzheng.getText().toString())){
            Toast.makeText(Register.this,"验证码不能为空",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(et_pass.getText().toString())||TextUtils.isEmpty(et_pass2.getText().toString())){
            Toast.makeText(Register.this,"密码不能为空",Toast.LENGTH_LONG).show();
        }
        else if(phone.getText().length()!=11){
            Toast.makeText(Register.this,"手机格式错误",Toast.LENGTH_LONG).show();
        }
        else if(!et_pass.getText().toString().equals(et_pass2.getText().toString())){
            Toast.makeText(Register.this,"两次密码输入不一致",Toast.LENGTH_LONG).show();
        }
        else if(checkbox_yes.isChecked()==false){
            Toast.makeText(Register.this,"必须同意GlocalMe用户协议",Toast.LENGTH_LONG).show();
        }
        else{
            return true;
        }
        return false;
    }//表单验证

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_yanzheng://获取验证码按钮
                initdata();
                break;
            case R.id.yesRegister:
                boolean b=Verification();
                if(b){
                    // 提交验证码，其中的code表示验证码，如“1357”
                    SMSSDK.submitVerificationCode("86",phone.getText().toString(),yanzheng.getText().toString());
                    if(b3) {
                        Toast.makeText(Register.this, "注册成功", Toast.LENGTH_LONG).show();
                        getOkhttp();
                    }
                    else{
                        Toast.makeText(Register.this, "验证码错误", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            default:
                break;
        }
    }//点击事件

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    protected void onStop(){
        super.onStop();
        //用完回调要注销掉，否则可能会出现内存泄露
        SMSSDK.unregisterEventHandler(eh);
    }
}
