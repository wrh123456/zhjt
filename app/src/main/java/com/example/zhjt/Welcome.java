package com.example.zhjt;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Welcome extends AppCompatActivity implements ViewPager.OnPageChangeListener,View.OnClickListener{
    private ViewPager viewPager;
    private List<ImageView> pagers;
    private LinearLayout linearLayout;//装点的容器
    private View mViewpoint;//选中的点
    private int mm;//点之间的距离
    private Button button_login;//点击进去登录

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //去除ActionBar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        viewPager=(ViewPager)findViewById(R.id.welcome_pager);
        linearLayout=(LinearLayout)findViewById(R.id.layout);
        mViewpoint=(View)findViewById(R.id.view_write);
        button_login=(Button)findViewById(R.id.button_login_welcome);
        button_login.setOnClickListener(this);

        initViewPager();//初始化数据

        mViewpoint.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if(pagers==null){
                    return;
                }
                mViewpoint.getViewTreeObserver().removeGlobalOnLayoutListener(this);//移除监听，只进行一次计算
                mm=linearLayout.getChildAt(1).getLeft()-linearLayout.getChildAt(0).getLeft();
            }
        });



    }
    private void initViewPager(){
        int[] imgRes=new int[]{
                R.drawable.guide_1,
                R.drawable.guide_2,
                R.drawable.guide_3
        };
        pagers=new ArrayList<>();
        for(int i=0;i<imgRes.length;i++){
            ImageView imageView=new ImageView(this);
            imageView.setImageResource(imgRes[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            pagers.add(imageView);

            //动态添加点
            View point=new View(this);
            point.setBackgroundResource(R.drawable.welcome_point);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(20,20);
            if(i!=0){
                params.leftMargin=10;//设置间距
            }
            linearLayout.addView(point,params);

        }
        viewPager.setAdapter(new WelcomePagerAdater());
        viewPager.setOnPageChangeListener(this);


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //当页面进行滑动时调用
        RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) mViewpoint.getLayoutParams();//调用移动距离
        params.leftMargin=(int)(mm*position+mm*positionOffset+0.5f);
        mViewpoint.setLayoutParams(params);

    }

    @Override
    public void onPageSelected(int position) {
        //在最后一个页面时
        button_login.setVisibility(position==pagers.size()-1?View.VISIBLE:View.GONE);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    class WelcomePagerAdater extends PagerAdapter{

        @Override
        public int getCount() {
            if(pagers!=null){
                return pagers.size();
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view==object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView view=pagers.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View)object);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_login_welcome:
                Intent intent=new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(Welcome.this,Login.class);
                startActivity(intent);
                break;
            default:break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
