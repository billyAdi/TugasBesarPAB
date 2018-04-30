package com.example.user.tugasbesarpab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by user on 4/30/2018.
 */

public class HighScoreFragment extends Fragment {
    public HighScoreFragment() {
    }

    public static HighScoreFragment newInstance(){
        HighScoreFragment highScoreFragment=new HighScoreFragment();
        return highScoreFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.highscore_fragment,container,false);
        return view;
    }
}
