package com.example.user.tugasbesarpab;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by user on 4/30/2018.
 */

public class CanvasFragment extends Fragment implements View.OnClickListener{
    protected FragmentListener fl;

    protected ImageView ivCanvas;
    protected TextView timeTv;
    protected Button btnNew,btnExit;

    protected TimerAsyncTask timerAsyncTask;

    public CanvasFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof FragmentListener){
            this.fl=(FragmentListener)context;
        }
    }

    public static CanvasFragment newInstance(){
        CanvasFragment canvasFragment=new CanvasFragment();
        return canvasFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.canvas_fragment,container,false);
        this.timeTv=view.findViewById(R.id.time_tv);
        this.ivCanvas=view.findViewById(R.id.iv_canvas);
        this.btnNew=view.findViewById(R.id.canvas_btn_new);
        this.btnExit=view.findViewById(R.id.canvas_btn_exit);

        this.btnNew.setOnClickListener(this);
        this.btnExit.setOnClickListener(this);
        return view;
    }

    public void setTimeTv(String time){
        this.timeTv.setText(time);
    }

    public void startTimer(){
        this.timerAsyncTask=new TimerAsyncTask(this);
        this.timerAsyncTask.execute();
    }

    /**
     * method ini dipanggil gamenya udh beres
     */
    public void stopTimer(){
        this.timerAsyncTask.cancel(true);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==this.btnNew.getId()){
           this.startTimer();
        }
        else if(view.getId()==this.btnExit.getId()){
            this.fl.changePage(1);
        }
    }
}
