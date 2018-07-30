package com.example.zhjt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.example.zhjt.Utils.PlayMusic;

public class MyLocation extends AppCompatActivity{
    private static final String TAG = "MyLocation";
    private Button mylocation;
    private Button youlocation;

    public LocationClient mLocationClient;//移动客户端
    private MapView mapView;
    private BaiduMap baiduMap;
    private boolean isFirstLocate=true;
    private PlayMusic playMusic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mLocationClient=new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        SDKInitializer.initialize(getApplicationContext());//初始化

        setContentView(R.layout.activity_mylocation);
        playMusic=new PlayMusic(4);//播放音乐
        mylocation=(Button)findViewById(R.id.mylocation_me);
        youlocation=(Button)findViewById(R.id.mylocation_you);

        requestLocation();
        mapView=(MapView)findViewById(R.id.mapview);
        baiduMap=mapView.getMap();
        baiduMap.setMyLocationEnabled(true);//一定要打开，不然不显示位置

        mylocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isFirstLocate=true;
                mLocationClient=new LocationClient(getApplicationContext());
                mLocationClient.registerLocationListener(new MyLocationListener());
                requestLocation();



            }
        });

        youlocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLocationClient.stop();
                getAims();

            }
        });

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
    private void getAims() {
        LatLng p = new LatLng(37.52f,121.39f);
        MapStatus mMapStatus = new MapStatus.Builder().target(p).zoom(11)
                .build();
        MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory
                .newMapStatus(mMapStatus);
        baiduMap =mapView.getMap();
        baiduMap.setMapStatus(mMapStatusUpdate);

    }

    private void navigateTo(BDLocation bdLocation){
        if (isFirstLocate) {
                LatLng ll = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
                MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);//地图状态更新工厂
                baiduMap.animateMapStatus(update);//地图状态更新
                update = MapStatusUpdateFactory.zoomTo(16f);//设置缩放级别
                baiduMap.animateMapStatus(update);//地图状态更新
            isFirstLocate=false;
        }
        MyLocationData.Builder builder = new MyLocationData.Builder();
        builder.latitude(bdLocation.getLatitude());
        builder.longitude(bdLocation.getLongitude());
        MyLocationData locationData = builder.build();
        baiduMap.setMyLocationData(locationData);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }

    @Override
    protected void onStart() {//活动由不可见变为可见使调用
        super.onStart();
        //播放音乐
        if(playMusic==null) {
            playMusic = new PlayMusic(7);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(playMusic!=null) {
            playMusic.stopmusic();
        }
    }
}
