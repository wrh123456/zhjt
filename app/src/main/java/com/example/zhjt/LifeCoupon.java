package com.example.zhjt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zhjt.Bean.Coupon;
import com.example.zhjt.Utils.LifeAdapter;

import java.util.ArrayList;
import java.util.List;

import static com.example.zhjt.R.drawable.life_view_yellow;

public class LifeCoupon extends AppCompatActivity implements View.OnClickListener{
    private ListView listView;
    private List<Coupon> couponList;
    private View life_viewcolor;
    private Button life_buttoncolor;
    private Button fanhui;
    private Button stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecoupon);
        initCouponData();
        listView=(ListView)findViewById(R.id.life_list);
        LifeAdapter adapter=new LifeAdapter(this,R.layout.life_item,couponList);
        listView.setAdapter(adapter);
        fanhui=(Button)findViewById(R.id.tv);
        stop=(Button)findViewById(R.id.stop);
        fanhui.setOnClickListener(this);
        stop.setOnClickListener(this);

    }
    private void initCouponData(){
        couponList=new ArrayList<>();
        Coupon coupon=new Coupon("¥8","立减券","家政洗衣按摩美业芬","2016.03.23-2106.03.26有效","");
        couponList.add(coupon);
        for(int i=0;i<10;i++){
            Coupon c=new Coupon("¥50","满减券","仅限昆仑山使用","2016.03.23-2106.03.26有效","满99可用");
            couponList.add(c);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv:
                finish();
                break;
            case R.id.stop:
                finish();
                break;
                default:break;
        }
    }
}
