package com.example.user.tugasbesarpab;

/**
 * Created by user on 5/10/2018.
 */

public class PenghitungScore {


    public int getScore(int count,int bonusCounter){


        int result=10000-(count*10);
        if(result<100){
            result=100;
        }
        return result+bonusCounter*100;
    }


}
