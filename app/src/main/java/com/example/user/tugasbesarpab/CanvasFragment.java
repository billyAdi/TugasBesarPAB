package com.example.user.tugasbesarpab;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by user on 4/30/2018.
 */

public class CanvasFragment extends Fragment implements View.OnClickListener,SensorEventListener {
    protected FragmentListener fl;

    protected ImageView ivCanvas;
    protected TextView timeTv;
    protected Button btnNew,btnPause;

    protected TimerAsyncTask timerAsyncTask;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    protected Canvas mCanvas;
    protected Bitmap mBitmap;
    protected Paint paint1,paint2,paint3;

    private float mAx;
    private float mAy;
    private float mDelay;

    private int radius1,radius2;

    private boolean isCanvasInitiated;
    private boolean isTimerStarted;
    private boolean status;
    private boolean isSet;
    private boolean isFinished;
    
    private int jumlahBonus;

    private Presenter presenter;


    private int bonusCounter;
    
    private float pitch,roll;

    private int count;

    private Setting setting;

    public CanvasFragment() {
    }

    public void setCount(int count) {
        this.count = count;
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

        if(fl!=null){
            this.presenter=((MainActivity)fl).getPresenter();

        }
        else {
            this.presenter = ((MainActivity) getActivity()).getPresenter();
        }
        this.setting=presenter.getSetting();

        this.timeTv=view.findViewById(R.id.time_tv);
        this.ivCanvas=view.findViewById(R.id.iv_canvas);
        this.btnNew=view.findViewById(R.id.canvas_btn_new);
        this.btnPause=view.findViewById(R.id.canvas_pause_btn);

        mSensorManager = (SensorManager)getActivity().getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        System.out.println(this.setting.getSpeed());
        this.mDelay=this.setting.getSpeed();

        mSensorManager.registerListener(this, mAccelerometer, (int) mDelay);
                   

        this.btnNew.setOnClickListener(this);
        this.btnPause.setOnClickListener(this);


        this.isCanvasInitiated=false;
        this.isTimerStarted=false;
        this.status=false;
        this.isSet=false;
        this.isFinished=false;

        this.bonusCounter=0;

        this.pitch=0;
        this.roll=0;
        this.mAx=0;
        this.mAy=0;

        this.setTimeTv("00 : 00");


        this.jumlahBonus=this.setting.getBonus();



        return view;
    }

    public void setTimeTv(String time){
        this.timeTv.setText(time);
    }


    public void endGame(){
        this.stopTimer();
        this.isFinished=true;
        mSensorManager.unregisterListener(this);
        int score=presenter.getScore(this.count,this.bonusCounter);
        this.btnPause.setText("EXIT");



        presenter.addNewScore(score);

        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        alertDialog.setMessage("Permainan selesai. Score akhir: "+score);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();


    }

    public void startTimer(){
        this.timerAsyncTask=new TimerAsyncTask(this,this.count);
        this.timerAsyncTask.execute();
    }


    public void stopTimer(){
        if(this.isTimerStarted){
            this.timerAsyncTask.cancel(true);
            this.isTimerStarted=false;
        }
    }

    public void unregisterListener(){
        this.mSensorManager.unregisterListener(this);
    }

    public void resetFragment(){
        this.stopTimer();
        this.resetCanvas();

        this.isCanvasInitiated=false;
        this.isTimerStarted=false;
        this.status=false;
        this.isSet=false;
        this.isFinished=false;
        this.presenter.renewArray();
        
        this.bonusCounter=0;

        this.pitch=0;
        this.roll=0;
        this.mAx=0;
        this.mAy=0;
        this.count=0;

        mSensorManager.registerListener(this, mAccelerometer, (int) mDelay);

        this.btnPause.setText("PAUSE");
    }

    @Override
    public void onClick(View view) {

        if(view.getId()==this.btnNew.getId()){
            this.setTimeTv("00 : 00");

            this.resetFragment();

            this.initializeCanvas();
            this.draw();
            this.startTimer();
            this.btnPause.setText("PAUSE");
            isFinished=false;
            this.isTimerStarted=true;
            this.isCanvasInitiated=true;

        }
        else if(view.getId()==this.btnPause.getId()){

            if(this.status==false&&this.isCanvasInitiated&&this.isFinished==false){
                mSensorManager.unregisterListener(this);
                this.btnPause.setText("RESUME");
                this.stopTimer();
                this.status=true;
            }
            else if(this.status==true){
                mSensorManager.registerListener(this, mAccelerometer, (int) mDelay);
                this.btnPause.setText("PAUSE");
                this.startTimer();
                this.isTimerStarted=true;
                this.status=false;
            }
            else if(isFinished){
                this.fl.changePage(1);
            }


        }
       
    }

    public void initializeCanvas(){
        this.mBitmap=Bitmap.createBitmap(this.ivCanvas.getWidth(),this.ivCanvas.getHeight(),Bitmap.Config.ARGB_8888);
        this.ivCanvas.setImageBitmap(this.mBitmap);
        this.mCanvas=new Canvas(this.mBitmap);

        this.paint1=new Paint();
        this.paint2=new Paint();
        this.paint3=new Paint();

        this.paint1.setStyle(Paint.Style.FILL);
        this.paint2.setStyle(Paint.Style.FILL);
        this.paint3.setStyle(Paint.Style.FILL);

        this.paint1.setColor(this.setting.getColor1());
        this.paint2.setColor(this.setting.getColor2());
        this.paint3.setColor(Color.CYAN);

        radius1=10;
        radius2=15;
        this.presenter.addObj(radius1+(int)(Math.random() * (ivCanvas.getWidth()-2*radius1)),radius1+(int)(Math.random() * (ivCanvas.getHeight()-2*radius1)),radius1);
        this.presenter.addObj(radius2+(int)(Math.random() * (ivCanvas.getWidth()-2*radius2)),radius2+(int)(Math.random() * (ivCanvas.getHeight()-2*radius2)),radius2);

        for(int i =0;i<jumlahBonus;i++){
            this.presenter.addBonus(radius1+(int)(Math.random() * (ivCanvas.getWidth()-2*radius1)),radius1+(int)(Math.random() * (ivCanvas.getHeight()-2*radius1)),radius1);
            while (this.presenter.cekCollide(presenter.getPlayer(),this.presenter.getBonus(i))){
                this.presenter.getBonus(i).setPosX(radius1+(int)(Math.random() * (ivCanvas.getWidth()-2*radius1)));
                this.presenter.getBonus(i).setPosY(radius1+(int)(Math.random() * (ivCanvas.getHeight()-2*radius1)));
            }
        }

    }

    public void resetCanvas(){
        if(this.isCanvasInitiated){
            this.mCanvas.drawColor(Color.WHITE);
            this.ivCanvas.invalidate();
        }
    }
    


    public void draw(){
        Lingkaran end = this.presenter.getEnd();
        Lingkaran player = this.presenter.getPlayer();
        this.mCanvas.drawCircle(end.getPosX(),end.getPosY(),end.getRad(),this.paint2);
        this.mCanvas.drawCircle(player.getPosX(),player.getPosY(),player.getRad(),this.paint1);
        if(this.presenter.cekCollide(player,end)){
            endGame();
        }
        
        for(int i =0;i<jumlahBonus;i++){
            Lingkaran bonus = this.presenter.getBonus(i);
            if(this.presenter.cekCollide(bonus,player)){
                this.bonusCounter++;
                presenter.removeBonus(i);
            }
            else{
                this.mCanvas.drawCircle(bonus.getPosX(),bonus.getPosY(),bonus.getRad(),this.paint3);
            }
        }

        this.ivCanvas.invalidate();

    }

    public void redraw(int posxS,int posyS){
        if(this.isCanvasInitiated){
            this.resetCanvas();
           this.presenter.gerakPlayer(posxS,posyS,ivCanvas.getWidth(),ivCanvas.getHeight());

            this.draw();

        }


    }
    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        if(this.isFinished==false){
            this.stopTimer();

            if(this.status==false&&this.isCanvasInitiated){
                this.btnPause.setText("RESUME");
                this.status=true;
            }
        }

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(!this.isSet){
            this.isSet=true;
            this.roll=sensorEvent.values[0];
            this.pitch=sensorEvent.values[1];
        }
        float bedaX=this.roll-sensorEvent.values[0];
        float bedaY=this.pitch-sensorEvent.values[1];

        mAx = bedaX;
        mAy = bedaY;

        mAx = presenter.hitungArah(mAx);
        mAy =  presenter.hitungArah(mAy);
        redraw((int)mAx  ,(int)mAy);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
