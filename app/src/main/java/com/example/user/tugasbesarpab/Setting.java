package com.example.user.tugasbesarpab;

import android.graphics.Color;

/**
 * Created by user on 5/12/2018.
 */

public class Setting {
    private float speed;
    private int color1,color2,bonus;

    public Setting(String speed,String color1,String color2,int bonus) {


        if(color1.equals("BLACK")){
            this.color1= Color.BLACK;
        }
        else if(color1.equals("BLUE")){
            this.color1=Color.BLUE;
        }
        else if(color1.equals("YELLOW")){
            this.color1= Color.YELLOW;
        }
        else if(color1.equals("GREEN")){
            this.color1= Color.GREEN;
        }

        if(color2.equals("GRAY")){
            this.color2= Color.GRAY;
        }
        else if(color2.equals("RED")){
            this.color2=Color.RED;
        }
        else if(color2.equals("MAGENTA")){
            this.color2= Color.MAGENTA;
        }

        if(speed.equals("Slow")){
            //atur speednya
        }
        else if(speed.equals("Normal")){
            //atur speednya
        }
        else if(speed.equals("Fast")){
            //atur speednya
        }


        this.bonus=bonus;
    }

    public float getSpeed() {
        return this.speed;
    }

    public int getColor1() {
        return this.color1;
    }

    public int getColor2() {
        return this.color2;
    }

    public int getBonus() {
        return this.bonus;
    }
}
