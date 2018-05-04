package com.example.user.tugasbesarpab;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

    protected Canvas mCanvas;
    protected Bitmap mBitmap;
    protected Paint paint1,paint2;

    private boolean isCanvasInitiated;
    private boolean isTimerStarted;

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

        this.isCanvasInitiated=false;
        this.isTimerStarted=false;

        this.setTimeTv("00 : 00");
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
        if(this.isTimerStarted){
            this.timerAsyncTask.cancel(true);
            this.isTimerStarted=false;
        }
    }

    @Override
    public void onClick(View view) {
        this.setTimeTv("00 : 00");
        if(view.getId()==this.btnNew.getId()){
            if(this.isCanvasInitiated==false){
                this.isCanvasInitiated=true;
                this.initializeCanvas();
            }
            this.resetCanvas();
            this.draw();

            if(this.isTimerStarted==false){
                this.isTimerStarted=true;
            }
            else{
                this.stopTimer();
            }
            this.startTimer();

        }
        else if(view.getId()==this.btnExit.getId()){
            this.fl.changePage(1);
            this.resetCanvas();
            this.stopTimer();
        }
    }

    public void initializeCanvas(){
        this.mBitmap=Bitmap.createBitmap(this.ivCanvas.getWidth(),this.ivCanvas.getHeight(),Bitmap.Config.ARGB_8888);
        this.ivCanvas.setImageBitmap(this.mBitmap);
        this.mCanvas=new Canvas(this.mBitmap);

        this.paint1=new Paint();
        this.paint2=new Paint();

        this.paint1.setStyle(Paint.Style.FILL);
        this.paint2.setStyle(Paint.Style.FILL);

        this.paint1.setColor(Color.BLACK);
        this.paint2.setColor(Color.RED);

    }

    public void resetCanvas(){
        if(this.isCanvasInitiated){
            this.mCanvas.drawColor(Color.WHITE);
            this.ivCanvas.invalidate();
        }
    }

    public void draw(){
        //draw 2 cirle (pertama kali draw), blm di random

        this.mCanvas.drawCircle(50,50,15,this.paint1);
        this.mCanvas.drawCircle(100,100,15,this.paint2);
        this.ivCanvas.invalidate();
    }
}
