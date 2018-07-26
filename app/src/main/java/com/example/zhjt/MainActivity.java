package com.example.zhjt;

import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView image_gun;//图片翻页效果
    private Button gongjiao;//公交出行
    private Button xunzhou;//徐州交通
    private Button tianqi;//天气
    private Button geren;//个人信息管理
    private Button dache;//打车信息
    private Button shenghuo;//生活优惠券
    private Button chuxing;//出行朋友圈
    private Button shinei;//市内路况
    private ImageView map;//我的位置地图
    private TextView melocation;//我的位置
    private List<ImageView> listimg;
    private int i = 0;
    private int[] imgrows = new int[]{
            R.drawable.main_viewpager_one,
            R.drawable.main_viewpager_two,
            R.drawable.main_viewpager_three
    };
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1) {
                image_gun.setImageResource(imgrows[i]);
                i++;
                if(i>2){
                    i=0;
                }
                handler.sendEmptyMessageDelayed(1,2000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image_gun=(ImageView)findViewById(R.id.image_gun);
        image_gun.setScaleType(ImageView.ScaleType.FIT_XY);
        image_gun.setOnClickListener(this);
        actionimag();
        gongjiao=(Button)findViewById(R.id.main_gongjiao);
        gongjiao.setOnClickListener(this);
        xunzhou=(Button)findViewById(R.id.main_xunzhou);
        xunzhou.setOnClickListener(this);
        tianqi=(Button)findViewById(R.id.main_tianqi);
        tianqi.setOnClickListener(this);
        geren=(Button)findViewById(R.id.main_geren);
        geren.setOnClickListener(this);
        dache=(Button)findViewById(R.id.main_dache);
        dache.setOnClickListener(this);
        shenghuo=(Button)findViewById(R.id.main_shenghuo);
        shenghuo.setOnClickListener(this);
        chuxing=(Button)findViewById(R.id.main_chuxing);
        chuxing.setOnClickListener(this);
        shinei=(Button)findViewById(R.id.main_shinei);
        shinei.setOnClickListener(this);
        map=(ImageView)findViewById(R.id.main_map);
        map.setOnClickListener(this);
        melocation=(TextView)findViewById(R.id.main_meloacation);
        melocation.setOnClickListener(this);
    }
    public void actionimag(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message=new Message();
                message.what=1;
                handler.sendMessage(message);
            }
        }).start();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_gun:break;
            case R.id.main_gongjiao:break;
            case R.id.main_xunzhou://徐州交通按钮点击事件
                Intent intent=new Intent(MainActivity.this,XuZhouTraffic.class);
                startActivity(intent);
                break;
            case R.id.main_tianqi:break;
            case R.id.main_geren:break;
            case R.id.main_dache:break;
            case R.id.main_shenghuo:
                Intent intent1=new Intent(MainActivity.this,LifeCoupon.class);
                startActivity(intent1);
                break;
            case R.id.main_chuxing:break;
            case R.id.main_shinei:break;
            case R.id.main_map:break;
            case R.id.main_meloacation:break;
        }

    }
}
