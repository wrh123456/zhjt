package com.example.zhjt.Utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhjt.Bean.Coupon;
import com.example.zhjt.R;

import java.util.List;

public class LifeAdapter extends ArrayAdapter<Coupon> {
    public LifeAdapter(@NonNull Context context, int resource, List<Coupon> list) {
        super(context, resource,list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Coupon c=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(R.layout.life_item,parent,false);
        TextView left_money=(TextView)view.findViewById(R.id.life_money);
        TextView left_type=(TextView)view.findViewById(R.id.life_type);
        TextView left_text=(TextView)view.findViewById(R.id.life_text);
        TextView left_manjian=(TextView)view.findViewById(R.id.life_manjian);
        TextView left_time=(TextView)view.findViewById(R.id.life_time);
        View left_view=(View)view.findViewById(R.id.life_color);
        Button left_button=(Button) view.findViewById(R.id.life_button);
        left_money.setText(c.getMoney());
        left_type.setText(c.getCouponType());
        left_text.setText(c.getCouponText());
        left_manjian.setText(c.getFullReduce());
        left_time.setText(c.getTime());
        if(c.getCouponType().equals("立减券")){
            left_view.setBackgroundResource(R.drawable.life_view_yellow);
            left_money.setTextColor(getContext().getResources().getColor(R.color.coloryellow));
            left_button.setTextColor(getContext().getResources().getColor(R.color.coloryellow));
        }
        return view;
    }
}
