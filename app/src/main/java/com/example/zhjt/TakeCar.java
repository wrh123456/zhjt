package com.example.zhjt;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.zhjt.Utils.PlayMusic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TakeCar extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "TakeCar";
    private TextView Takecar_Spenddata;
    private TextView Takecar_Money;
    private Button Takecar_button1;
    private Button Takecar_button2;
    private Button Takecar_button3;
    private boolean isPause = false;//是否暂停
    private long currentSecond = 0;//当前毫秒数
    private Handler handle = new Handler();
    private PlayMusic playMusic;

    //地图
    private MapView mapView;
    private LocationClient mLocationClient;
    private BaiduMap baiduMap;
    private boolean isFirstLocate=true;


    //计算时间
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private Date day=new Date();
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLocationClient=new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());//初始化

        setContentView(R.layout.activity_takecar);
        playMusic=new PlayMusic(6);//播放音乐
        Takecar_Spenddata=(TextView)findViewById(R.id.Takecar_Spenddata);
        Takecar_Money=(TextView)findViewById(R.id.Takecar_Money);
        Takecar_button1=(Button)findViewById(R.id.Takecar_button1);
        Takecar_button2=(Button)findViewById(R.id.Takecar_button2);
        Takecar_button3=(Button)findViewById(R.id.Takecar_button3);
        Takecar_button1.setOnClickListener(this);
        Takecar_button2.setOnClickListener(this);
        Takecar_button3.setOnClickListener(this);
        sp= PreferenceManager.getDefaultSharedPreferences(this);
        editor = sp.edit();
        String nowdate=sp.getString("nowdate","");//关闭时的系统时间
        Long date2=sp.getLong("moneylong",0);//用时
        Log.d(TAG, "onCreate: "+date2);
        if(!nowdate.equals("")&&date2!=0) {
            try {
                String date = df.format(day);//现在的系统时间
                Date day2=df.parse(nowdate);
                long between=(day.getTime()-day2.getTime())/1000;//除以1000是为了转换成秒
                Log.d(TAG, "onCreate: "+day+","+day2);
                Takecar_Spenddata.setText("已花费时长： "+getFormatHMS(between*1000));
                currentSecond=between*1000;
                if(between<=10){
                    Takecar_Money.setText("按时金额 5 元");
                }else{
                    long a=5+((between-10)/2*3);
                    Takecar_Money.setText("按时金额 "+a+" 元");
                }
                actiondate();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        //地图开始
        requestLocation();
        mapView=(MapView)findViewById(R.id.mapview);
        baiduMap=mapView.getMap();
        baiduMap.setMyLocationEnabled(true);//一定要打开，不然不显示位置



    }

    private void requestLocation(){
        initLocation();//初始化位置信息
        mLocationClient.start();
    }

    private void initLocation(){
        LocationClientOption option=new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);//修改定位模式
        option.setIsNeedAddress(true);//是否获取详细信息
        option.setScanSpan(5000);
        mLocationClient.setLocOption(option);
    }

    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if((bdLocation.getLocType() == BDLocation.TypeGpsLocation) || (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation)){
                navigateTo(bdLocation);
            }
        }
    }



    private void navigateTo(BDLocation bdLocation){
        if(isFirstLocate){
            LatLng ll=new LatLng(bdLocation.getLatitude(),bdLocation.getLongitude());
            MapStatusUpdate update= MapStatusUpdateFactory.newLatLng(ll);//地图状态更新工厂
            baiduMap.animateMapStatus(update);//地图状态更新
            update=MapStatusUpdateFactory.zoomTo(16f);//设置缩放级别
            baiduMap.animateMapStatus(update);//地图状态更新
            isFirstLocate=false;
        }
        MyLocationData.Builder builder=new MyLocationData.Builder();
        builder.latitude(bdLocation.getLatitude());
        builder.longitude(bdLocation.getLongitude());
        MyLocationData locationData=builder.build();
        baiduMap.setMyLocationData(locationData);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.Takecar_button1:
                isPause=false;
                actiondate();
                //开始计时
                break;
            case R.id.Takecar_button2:
                isPause=true;
                //停止计时结算时间和花费
                break;
            case R.id.Takecar_button3:
                //付费成功，时间与金钱清零
                isPause=true;
                editor.putString("nowdate","");
                editor.putLong("moneylong",0);
                editor.commit();
                Takecar_Spenddata.setText("已花费时长： 00:00:00");
                Takecar_Money.setText("按时金额 0 元");
                break;
                default:break;
        }
    }

    private void actiondate(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                currentSecond = currentSecond + 1000;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Takecar_Spenddata.setText("已花费时长： "+getFormatHMS(currentSecond));
                        if(currentSecond/1000<=10){
                            Takecar_Money.setText("按时金额 5 元");
                        }else{
                            long a=5+((currentSecond/1000-10)/2*3);
                            Takecar_Money.setText("按时金额 "+a+" 元");
                        }
                    }
                });
                if (!isPause) {
                    //递归调用本runable对象，实现每隔一秒一次执行任务
                    handle.postDelayed(this, 1000);
                }
            }
        }).start();

    }
    public static String getFormatHMS(long time){
        time=time/1000;//总秒数
        int s= (int) (time%60);//秒
        int m= (int) (time/60);//分
        int h=(int) (time/3600);//秒
        return String.format("%02d:%02d:%02d",h,m,s);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
        if(!isPause) {
            String date3= df.format(day);
            editor.putString("nowdate",date3);
            editor.putLong("moneylong", currentSecond);
            editor.commit();
            playMusic.stopmusic();
        }
    }
    @Override
    protected void onStart() {//活动由不可见变为可见使调用
        super.onStart();
        //播放音乐
        playMusic=new PlayMusic(6);
    }

    @Override
    protected void onStop() {//活动完全不可见时调用
        super.onStop();
        playMusic.stopmusic();
    }



}
