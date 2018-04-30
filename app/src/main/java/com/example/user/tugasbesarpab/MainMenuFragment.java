package com.example.user.tugasbesarpab;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by user on 4/30/2018.
 */

public class MainMenuFragment extends Fragment implements View.OnClickListener {
    protected Button exitButton, newButton;
    protected FragmentListener fl;

    public MainMenuFragment() {
    }

    public static MainMenuFragment newInstance(){
        MainMenuFragment mainMenuFragment=new MainMenuFragment();
        return mainMenuFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof FragmentListener){
            this.fl=(FragmentListener)context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.main_fragment,container,false);
        this.exitButton=view.findViewById(R.id.btn_exit);
        this.newButton=view.findViewById(R.id.btn_new);

        this.newButton.setOnClickListener(this);
        this.exitButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==this.newButton.getId()){
            this.fl.changePage(2);
        }
        else if(view.getId()==this.exitButton.getId()){
            getActivity().onBackPressed();
        }
    }
}
