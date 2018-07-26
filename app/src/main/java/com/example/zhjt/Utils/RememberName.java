package com.example.zhjt.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;

public class RememberName {
    private static SharedPreferences sp;
    private static String key="zhName";

    public static SharedPreferences getSp(Context context) {
        if(sp==null){
            sp=context.getSharedPreferences(key,Context.MODE_PRIVATE);
        }
        return sp;
    }

    public static String getName(String namekey,Context context) {
            SharedPreferences sp=getSp(context);
            String name2=sp.getString(namekey,"");
        return name2;
    }
    public static  void setName(String namekey,Context context,String name){
        SharedPreferences.Editor editor=getSp(context).edit();
        editor.putString(namekey,name);
        editor.commit();
    }
}
