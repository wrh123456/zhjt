package com.example.zhjt;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends AppCompatActivity implements View.OnClickListener{
    private EditText username;
    private EditText password;
    private Button login;
    private Button register;
    private TextView unpass;
    private TextView a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        login=(Button)findViewById(R.id.button_login);
        register=(Button)findViewById(R.id.button_register);
        unpass=(TextView)findViewById(R.id.unpass);
        unpass.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_login:
                break;
            case R.id.button_register:
                Intent intent=new Intent(Login.this,Register.class);
                startActivity(intent);
                break;
                default:break;
        }
    }
}
