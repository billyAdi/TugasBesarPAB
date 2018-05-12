package com.example.user.tugasbesarpab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * Created by user on 4/30/2018.
 */

public class HighScoreFragment extends Fragment {
    protected ListView highScoreListView;
    protected HighScoreAdapter highScoreAdapter;

    public HighScoreFragment() {
    }

    public static HighScoreFragment newInstance(){
        HighScoreFragment highScoreFragment=new HighScoreFragment();
        return highScoreFragment;
    }

    //jgn langsung pake get activity, nanti hrs diganti pake fragment listener
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.highscore_fragment,container,false);
        this.highScoreListView=view.findViewById(R.id.listview_highscore);
        this.highScoreAdapter=new HighScoreAdapter((MainActivity)getActivity());

        this.highScoreAdapter.setArray(((MainActivity) getActivity()).getPresenter().getHighScoreList());

        this.highScoreListView.setAdapter(this.highScoreAdapter);

        return view;
    }
    //jgn langsung pake get activity, nanti hrs diganti pake fragment listener
    public void updateListView(){
        if(highScoreAdapter!=null) {
            this.highScoreAdapter.setArray(((MainActivity) getActivity()).getPresenter().getHighScoreList());
        }
    }
}
