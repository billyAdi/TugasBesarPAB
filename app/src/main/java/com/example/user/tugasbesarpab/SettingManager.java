package com.example.user.tugasbesarpab;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by user on 5/12/2018.
 */

public class SettingManager {
    private String preFile="com.example.user.tugasbesarpab";
    protected SharedPreferences sharedPreferences;
    public SettingManager(Context context) {
        this.sharedPreferences=context.getSharedPreferences(preFile,Context.MODE_PRIVATE);
    }

    public void saveSettings(int speed,int color1, int color2,int bonus){
        SharedPreferences.Editor editor=this.sharedPreferences.edit();
        editor.putInt("speed",speed);
        editor.putInt("color1",color1);
        editor.putInt("color2",color2);
        editor.putInt("bonus",bonus);
        editor.apply();
    }

    public int[] loadSettings(){
        int[] settings=new int[4];
        settings[0]=this.sharedPreferences.getInt("speed",-1);
        settings[1]=this.sharedPreferences.getInt("color1",-1);
        settings[2]=this.sharedPreferences.getInt("color2",-1);
        settings[3]=this.sharedPreferences.getInt("bonus",-1);

        return settings;
    }
}
