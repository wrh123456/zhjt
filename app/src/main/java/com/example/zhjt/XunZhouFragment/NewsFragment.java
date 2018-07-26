package com.example.zhjt.XunZhouFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.zhjt.R;
import com.example.zhjt.Welcome;
import com.example.zhjt.XuZhouTraffic;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

@SuppressLint("ValidFragment")
public class NewsFragment extends Fragment implements ViewPager.OnPageChangeListener{
    private ViewPager viewPager;
    private List<ImageView> pagers;
    private LinearLayout linearLayout;//装点的容器
    private View mViewpoint;//选中的点
    private int mm;//点之间的距离
    private Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.activity_news,container,false);
        viewPager=(ViewPager)view.findViewById(R.id.welcome_pager);
        linearLayout=(LinearLayout)view.findViewById(R.id.layout);
        mViewpoint=(View)view.findViewById(R.id.view_write);
        initdata();
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
        return view;

    }
    public NewsFragment(Context context){
        this.context=context;
    }
    public void initdata(){
        pagers=new ArrayList<>();
        int[] imgrows=new int[]{
                R.drawable.news1,
                R.drawable.news2,
                R.drawable.news3
        };
        for(int i=0;i<imgrows.length;i++){
            ImageView iv=new ImageView(context);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            iv.setImageResource(imgrows[i]);
            pagers.add(iv);

            //动态添加点
            View point=new View(context);
            point.setBackgroundResource(R.drawable.news_point);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(20,20);
            if(i!=0){
                params.leftMargin=10;//设置间距
            }
            linearLayout.addView(point,params);
        }
        viewPager.setAdapter(new NewsPagerAdater());
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
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    class NewsPagerAdater extends PagerAdapter {

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
}
