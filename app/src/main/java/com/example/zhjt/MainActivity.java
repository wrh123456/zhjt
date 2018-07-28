package com.example.zhjt;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhjt.Utils.PlayMusic;
import com.example.zhjt.Utils.ShakeListener;

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
    private ShakeListener mshakelistener=null;
    private int keys=0;
    private PlayMusic playMusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //播放音乐
        playMusic=new PlayMusic(1);

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
        ApplyPower();
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
    private void ApplyPower(){ //申请权限
        List<String> permissionList=new ArrayList<>();
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if(!permissionList.isEmpty()){
            String[] permissions=permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MainActivity.this,permissions,1);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if(grantResults.length>0){
                    for(int result:grantResults){
                        if(result!=PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"必须同意所有权限才能使用本程序",Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                }
                break;
            default:break;
        }
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
            case R.id.main_tianqi:
                Intent intent2=new Intent(MainActivity.this,Weather.class);
                startActivity(intent2);
                break;
            case R.id.main_geren:break;
            case R.id.main_dache:
                Intent intent3=new Intent(MainActivity.this,TakeCar.class);
                startActivity(intent3);
                break;
            case R.id.main_shenghuo:
                Intent intent1=new Intent(MainActivity.this,LifeCoupon.class);
                startActivity(intent1);
                break;
            case R.id.main_chuxing:break;
            case R.id.main_shinei:break;

            case R.id.main_map:
            case R.id.main_meloacation:
                Intent intent4=new Intent(MainActivity.this,MyLocation.class);
                startActivity(intent4);
                break;
                default:break;
        }
    }

    @Override
    protected void onResume() {//摇一摇关闭应用进登录
        super.onResume();
        mshakelistener=new ShakeListener(this);
        mshakelistener.setOnShakeListener(new ShakeListener.OnShakeListener() {
            @Override
            public void onShake() {
                AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
                keys++;

                dialog.setTitle("是否关闭应用进入登录页面");
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent=new Intent(MainActivity.this,Login.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        keys=0;
                    }
                });
                if(keys==1) {
                    dialog.show();
                }

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mshakelistener!=null){
            mshakelistener.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {//活动由不可见变为可见使调用
        super.onStart();
        //播放音乐
        playMusic=new PlayMusic(1);
    }

    @Override
    protected void onStop() {//活动完全不可见时调用
        super.onStop();
        playMusic.stopmusic();
    }
}
