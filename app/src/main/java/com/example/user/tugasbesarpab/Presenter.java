package com.example.user.tugasbesarpab;

import java.util.ArrayList;

/**
 * Created by user on 5/1/2018.
 */

public class Presenter {
    private MainActivity ui;
    protected ArrayList<Integer> highScoreArrayList;

    public Presenter(MainActivity ui) {
        this.ui = ui;
        this.highScoreArrayList=new ArrayList<Integer>();
        for(int i=0;i<20;i++){
            this.highScoreArrayList.add(0);
        }
        //hrs nya ambil highscore dari web service
    }

    public ArrayList<Integer> getHighScoreList(){
        return this.highScoreArrayList;
    }

    public void updateHighScoreArray(){
        //tiap kali beres game, udpate ke web service
        // masukin score baru ke array list, sorting, buang elemen paling ujung.
    }




}
