package com.example.user.tugasbesarpab;

/**
 * Created by user on 5/12/2018.
 */

public class Setting {
    private String speed,color1,color2;
    private int bonus;

    public Setting(String speed,String color1,String color2,int bonus) {
        this.speed=speed;
        this.color1=color1;
        this.color2=color2;
        this.bonus=bonus;
    }

    public String getSpeed() {
        return this.speed;
    }

    public String getColor1() {
        return this.color1;
    }

    public String getColor2() {
        return this.color2;
    }

    public int getBonus() {
        return this.bonus;
    }
}
