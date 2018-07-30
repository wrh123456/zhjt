package com.example.zhjt.Utils;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.zhjt.Bean.title;
import com.example.zhjt.R;
import com.example.zhjt.XuZhouTraffic;
import com.example.zhjt.XunZhouFragment.JunshiFragment;
import com.example.zhjt.XunZhouFragment.NewsFragment;

import java.util.List;

public class TitleRecyclerAdapter extends RecyclerView.Adapter<TitleRecyclerAdapter.ViewHolder>{
    private List<title> mtitletext;
    private XuZhouTraffic mcontext;
    private Button mbutton;

    public TitleRecyclerAdapter(List<title> list, XuZhouTraffic context) {
        mtitletext=list;
        mcontext=context;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        Button buttontext;

        public ViewHolder(View itemView) {
            super(itemView);
            buttontext=(Button) itemView.findViewById(R.id.title_buttontext);
            //标记button

        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.title_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.buttontext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mbutton!=holder.buttontext){
                    mbutton.setBackgroundResource(R.drawable.button_title_false);
                    mbutton=holder.buttontext;
                }
                holder.buttontext.setBackgroundResource(R.drawable.button_title);
                String a=holder.buttontext.getText().toString();
                switch (a){
                    case "要闻":setFragment(new NewsFragment(mcontext));break;
                    case "军事":
                    case "财经":
                    case "社会":
                    case "体育":setFragment(new JunshiFragment());break;
                    default:break;
                }
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final title t = mtitletext.get(position);
        holder.buttontext.setText(t.getTitletext());
        if (position == 0) {
            holder.buttontext.setBackgroundResource(R.drawable.button_title);
            mbutton=holder.buttontext;
        }


    }
    private void setFragment(Fragment fragment){
        FragmentManager fragmentManager=mcontext.getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.replace(R.id.xunzou_content_fragment,fragment);
        transaction.commit();
    }

    @Override
    public int getItemCount() {
        return mtitletext.size();
    }
}
