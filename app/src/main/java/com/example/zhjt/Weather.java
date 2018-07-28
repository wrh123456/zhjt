package com.example.zhjt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zhjt.Bean.Data;
import com.example.zhjt.Bean.Forecast;
import com.example.zhjt.Bean.WeatherData;
import com.example.zhjt.Bean.Yesterday;
import com.example.zhjt.Utils.PlayMusic;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Weather extends AppCompatActivity {
    private TextView diqu;//地区
    private ImageView weather_img1;//顶部图标
    private TextView weather_du;//顶部温度
    private TextView weather_diqu;//顶部地区
    private ImageView weather_buttom1;//底部图片一
    private ImageView weather_buttom2;//底部图片二
    private ImageView weather_buttom3;//底部图片三
    private ImageView weather_buttom4;//底部图片四
    private ImageView weather_buttom5;//底部图片五
    private TextView weather_text1;//星期1
    private TextView weather_text2;//星期2
    private TextView weather_text3;//星期3
    private TextView weather_text4;//星期4
    private TextView weather_text5;//星期5
    private RelativeLayout relativeLayout;
    private TextView weather_wen1;//温度1
    private TextView weather_wen2;//温度2
    private TextView weather_wen3;//温度3
    private TextView weather_wen4;//温度4
    private TextView weather_wen5;//温度5
    private PlayMusic playMusic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        playMusic=new PlayMusic(7);//播放音乐

        diqu=(TextView)findViewById(R.id.weather_diqu);
        weather_img1=(ImageView)findViewById(R.id.weather_imgtop);
        weather_du=(TextView)findViewById(R.id.weather_tv_du);
        weather_diqu=(TextView)findViewById(R.id.weather_diqu2);
        weather_buttom1=(ImageView)findViewById(R.id.weather_img_1);
        weather_buttom2=(ImageView)findViewById(R.id.weather_img_2);
        weather_buttom3=(ImageView)findViewById(R.id.weather_img_3);
        weather_buttom4=(ImageView)findViewById(R.id.weather_img_4);
        weather_buttom5=(ImageView)findViewById(R.id.weather_img_5);
        weather_text1=(TextView)findViewById(R.id.weather_button_1);
        weather_text2=(TextView)findViewById(R.id.weather_button_2);
        weather_text3=(TextView)findViewById(R.id.weather_button_3);
        weather_text4=(TextView)findViewById(R.id.weather_button_4);
        weather_text5=(TextView)findViewById(R.id.weather_button_5);
        relativeLayout=(RelativeLayout)findViewById(R.id.rela_bg);
        weather_wen1=(TextView)findViewById(R.id.weather_wen1);
        weather_wen2=(TextView)findViewById(R.id.weather_wen2);
        weather_wen3=(TextView)findViewById(R.id.weather_wen3);
        weather_wen4=(TextView)findViewById(R.id.weather_wen4);
        weather_wen5=(TextView)findViewById(R.id.weather_wen5);


        //获取天气数据
        actionWeather();


    }
    private void actionWeather(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client=new OkHttpClient();
                    Request request=new Request.Builder()
                            .url("http://192.168.3.102:9090/a.json")
                            .build();
                    Response response=client.newCall(request).execute();
                    String resopnseData=response.body().string();
                    //开始解析json数据
                    paesrJsonWeather(resopnseData);
                    Log.d("Weather", "run: "+resopnseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
    private void paesrJsonWeather(String data){
        try {
            WeatherData weather=new WeatherData();
            JSONObject object=new JSONObject(data);
                //获得Data的内容
                JSONObject ob1=object.getJSONObject("data");
                Data data2=new Data();
                    JSONObject ob2=ob1.getJSONObject("yesterday");
                    Yesterday yesterday=new Yesterday();
                    yesterday.date=ob2.getString("date");
                    yesterday.high=ob2.getString("high");
                    yesterday.fx=ob2.getString("fx");
                    yesterday.low=ob2.getString("low");
                    yesterday.fl=ob2.getString("fl");
                    yesterday.type=ob2.getString("type");
                data2.yesterday=yesterday;
                data2.city=ob1.getString("city");
                final String weatherdiqu=data2.city.toString();///////////////////////////////////////////////////////////////
                final String[] weatherxingqi=new String[5];///////////////////////////////////////////////////////////////////
                final String[] weatherxingqi_type=new String[5];///////////////////////////////////////////////////////////////////
                final String[] weather_wen =new String[5];
                data2.aqi=ob1.getString("aqi");
                    List<Forecast> forecast=new ArrayList<>();
                    JSONArray array= ob1.getJSONArray("forecast");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject ob3 = array.getJSONObject(i);

                        weatherxingqi[i]=ob3.getString("date");///////////////////////////////////////////
                        weatherxingqi_type[i]=ob3.getString("type");///////////////////////////////////////
                        weather_wen[i]=ob3.getString("high");

                        Forecast ff=new Forecast();
                        ff.date=ob3.getString("date");
                        ff.high=ob3.getString("high");
                        ff.fengli=ob3.getString("fengli");
                        ff.low=ob3.getString("low");
                        ff.fengxiang=ob3.getString("fengxiang");
                        ff.type=ob3.getString("type");
                        forecast.add(ff);
                    }
                    JSONObject ob4 = array.getJSONObject(0);
                    final String weathertype=ob4.getString("type");//////////////////////////////////////////////////////
                    final String weatherhigh=ob4.getString("high");/////////////////////////////////////////////////////


                data2.forecast=forecast;
                data2.ganmao=ob1.getString("ganmao");
                data2.wendu=ob1.getString("wendu");
            weather.status=object.getString("status");
            weather.desc=object.getString("desc");
            weather.data=data2;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    diqu.setText(weatherdiqu);//地区
                    int tian=chatian(weathertype.toString());//顶部天气
                    weather_img1.setImageResource(tian);
                    int bgtian=bgtian(weathertype.toString());
                    relativeLayout.setBackgroundResource(bgtian);

                    weather_du.setText(weatherhigh);//顶部温度
                    weather_diqu.setText(weatherdiqu);//顶部地区

                    int a0=chatian(weatherxingqi_type[0]);
                    weather_buttom1.setImageResource(a0);//底部图片一
                    int a1=chatian(weatherxingqi_type[1]);
                    weather_buttom2.setImageResource(a1);;//底部图片二
                    int a2=chatian(weatherxingqi_type[2]);
                    weather_buttom3.setImageResource(a2);;//底部图片三
                    int a3=chatian(weatherxingqi_type[3]);
                    weather_buttom4.setImageResource(a3);;//底部图片四
                    int a4=chatian(weatherxingqi_type[4]);
                    weather_buttom5.setImageResource(a4);;//底部图片五
                    weather_text1.setText(weatherxingqi[0]);//星期1
                    weather_text2.setText(weatherxingqi[1]);//星期2
                    weather_text3.setText(weatherxingqi[2]);//星期3
                    weather_text4.setText(weatherxingqi[3]);//星期4
                    weather_text5.setText(weatherxingqi[4]);

                    weather_wen1.setText(weather_wen[0]);//温度1
                    weather_wen2.setText(weather_wen[1]);//温度2
                    weather_wen3.setText(weather_wen[2]);//温度3
                    weather_wen4.setText(weather_wen[3]);//温度4
                    weather_wen5.setText(weather_wen[4]);//温度5

                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public int chatian(String aa){
        int i=R.drawable.a1;
        if(aa.equals("多云")) {
            i=R.drawable.a5;
        }else if(aa.equals("晴")){
            i=R.drawable.a1;
        }else if(aa.equals("阴")){
            i=R.drawable.a2;
        }else if(aa.equals("小雨")){
            i=R.drawable.a3;
        }else if(aa.equals("中雨")){
            i=R.drawable.a3;
        }else if(aa.equals("暴雨")){
            i=R.drawable.a3;
        }else if(aa.equals("雷阵雨")){
            i=R.drawable.a3;
        }else if(aa.equals("雪")){
            i=R.drawable.a4;
        }
        return i;
    }
    public int bgtian(String aa){
        int i=R.drawable.a1;
        if(aa.equals("多云")) {
            i=R.drawable.qingtian;
        }else if(aa.equals("晴")){
            i=R.drawable.qingtian;
        }else if(aa.equals("阴")){
            i=R.drawable.yintian;
        }else if(aa.equals("小雨")){
            i=R.drawable.xiayu;
        }else if(aa.equals("中雨")){
            i=R.drawable.xiayu;
        }else if(aa.equals("暴雨")){
            i=R.drawable.xiayu;
        }else if(aa.equals("雷阵雨")){
            i=R.drawable.xiayu;
        }else if(aa.equals("雪")){
            i=R.drawable.xiaxue;
        }
        return i;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        playMusic.stopmusic();
    }
    @Override
    protected void onStart() {//活动由不可见变为可见使调用
        super.onStart();
        //播放音乐
        playMusic=new PlayMusic(7);
    }

    @Override
    protected void onStop() {//活动完全不可见时调用
        super.onStop();
        playMusic.stopmusic();
    }

}
