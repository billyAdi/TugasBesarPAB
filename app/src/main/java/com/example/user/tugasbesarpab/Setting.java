package com.example.user.tugasbesarpab;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by user on 5/12/2018.
 */

public class Setting {
    private String preFile="com.example.user.tugasbesarpab";
    protected SharedPreferences sharedPreferences;
    public Setting(Context context) {
        this.sharedPreferences=context.getSharedPreferences(preFile,Context.MODE_PRIVATE);
    }

    public void saveData(String speed,String color1, String color2,int bonus){
        SharedPreferences.Editor editor=this.sharedPreferences.edit();
        editor.putString("speed",speed);
        editor.putString("color1",color1);
        editor.putString("color2",color2);
        editor.putInt("bonus",bonus);
        editor.apply();
    }

    public void loadData(){
        System.out.println("data");
        System.out.println(this.sharedPreferences.getString("speed",""));
        System.out.println(this.sharedPreferences.getString("color1",""));
        System.out.println(this.sharedPreferences.getString("color2",""));
        System.out.println(this.sharedPreferences.getInt("bonus",-1));
    }
}
