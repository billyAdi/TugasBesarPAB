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

import java.util.ArrayList;

import static android.content.Context.SENSOR_SERVICE;

/**
 * Created by user on 4/30/2018.
 */

public class CanvasFragment extends Fragment implements View.OnClickListener,SensorEventListener {
    protected FragmentListener fl;

    protected ImageView ivCanvas;
    protected TextView timeTv;
    protected Button btnNew,btnPause;
    private ArrayList<Lingkaran> obj;
    private ArrayList<Lingkaran> bonus;
    protected TimerAsyncTask timerAsyncTask;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    protected Canvas mCanvas;
    protected Bitmap mBitmap;
    protected Paint paint1,paint2,paint3;

    private float mAx;
    private float mAy;
    private float mDelay = 10f;

    private int radius1,radius2;

    private boolean isCanvasInitiated;
    private boolean isTimerStarted;
    private boolean status;
    private boolean isSet;
    private boolean isFinished;
    
    private int jumlahBonus;




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
        this.setting=((MainActivity)getActivity()).getPresenter().getSetting();
        this.obj=new ArrayList<Lingkaran>();
        this.bonus=new ArrayList<Lingkaran>();
        this.timeTv=view.findViewById(R.id.time_tv);
        this.ivCanvas=view.findViewById(R.id.iv_canvas);
        this.btnNew=view.findViewById(R.id.canvas_btn_new);
        this.btnPause=view.findViewById(R.id.canvas_pause_btn);

        mSensorManager = (SensorManager)getActivity().getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

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
        this.mDelay=this.setting.getSpeed();


        return view;
    }

    public void setTimeTv(String time){
        this.timeTv.setText(time);
    }

    //jangan langsung pake getActivity(), tar diganti pake fragment listener
    public void endGame(){
        this.stopTimer();
        this.isFinished=true;
        mSensorManager.unregisterListener(this);
        int score=((MainActivity)getActivity()).presenter.getScore(this.count,this.bonusCounter);
        this.btnPause.setText("EXIT");


        System.out.println(score);
        ((MainActivity)getActivity()).presenter.addNewScore(score);

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
        this.obj=new ArrayList<Lingkaran>();
        this.bonus=new ArrayList<Lingkaran>();
        
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
            else if(this.status==true&&this.isFinished==false){
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
        this.obj.add(new Lingkaran(radius1+(int)(Math.random() * (ivCanvas.getWidth()-2*radius1)),radius1+(int)(Math.random() * (ivCanvas.getHeight()-2*radius1)),radius1));
        this.obj.add(new Lingkaran(radius2+(int)(Math.random() * (ivCanvas.getWidth()-2*radius2)),radius2+(int)(Math.random() * (ivCanvas.getHeight()-2*radius2)),radius2));

        for(int i =0;i<jumlahBonus;i++){
            this.bonus.add(new Lingkaran(radius1+(int)(Math.random() * (ivCanvas.getWidth()-2*radius1)),radius1+(int)(Math.random() * (ivCanvas.getHeight()-2*radius1)),radius1));
            while (this.cekCollide(bonus.get(i),this.obj.get(1))){
                bonus.get(i).setPosX(radius1+(int)(Math.random() * (ivCanvas.getWidth()-2*radius1)));
                bonus.get(i).setPosY(radius1+(int)(Math.random() * (ivCanvas.getHeight()-2*radius1)));
            }
        }

    }

    public void resetCanvas(){
        if(this.isCanvasInitiated){
            this.mCanvas.drawColor(Color.WHITE);
            this.ivCanvas.invalidate();
        }
    }
    
   public boolean cekCollide(Lingkaran l1,Lingkaran l2){
        double xDif = l1.getPosX() - l2.getPosX();
        double yDif = l1.getPosY() - l2.getPosY();
        double distanceSquared = xDif * xDif + yDif * yDif;
        return distanceSquared < (l1.getRad() + l2.getRad()) * (l1.getRad() + l2.getRad());
    }

    public void draw(){
        this.mCanvas.drawCircle(obj.get(1).getPosX(),obj.get(1).getPosY(),obj.get(1).getRad(),this.paint2);
        this.mCanvas.drawCircle(obj.get(0).getPosX(),obj.get(0).getPosY(),obj.get(0).getRad(),this.paint1);
        if(this.cekCollide(obj.get(1),obj.get(0))){
            endGame();
        }
        
        for(int i =0;i<bonus.size();i++){
            if(this.cekCollide(bonus.get(i),obj.get(0))){
                this.bonusCounter++;
                bonus.remove(i);
            }
            else{
                this.mCanvas.drawCircle(bonus.get(i).getPosX(),bonus.get(i).getPosY(),bonus.get(i).getRad(),this.paint3);
            }
        }

        this.ivCanvas.invalidate();

    }

    public void redraw(int posxS,int posyS){
        if(this.isCanvasInitiated){
            this.resetCanvas();


            this.obj.get(0).setSpeedX(this.obj.get(0).getSpeedX()+posxS);
            this.obj.get(0).setSpeedY(this.obj.get(0).getSpeedY()-posyS);

            int temp = this.obj.get(0).getPosX()+this.obj.get(0).getSpeedX();
            if(temp<radius1){
                this.obj.get(0).setSpeedX(this.obj.get(0).getSpeedX()/2);
                temp=radius1;
            }else if(temp>=(ivCanvas.getWidth()-radius1)){
                this.obj.get(0).setSpeedX(this.obj.get(0).getSpeedX()/2);
                temp = ivCanvas.getWidth()-radius1;
            }
            this.obj.get(0).setPosX(temp);

            temp = this.obj.get(0).getPosY()+this.obj.get(0).getSpeedY();
            if(temp<radius1){
                this.obj.get(0).setSpeedY(this.obj.get(0).getSpeedY()/2);
                temp=radius1;
            }else if(temp>=(ivCanvas.getHeight()-radius1)){
                this.obj.get(0).setSpeedY(this.obj.get(0).getSpeedY()/2);
                temp = ivCanvas.getHeight()-radius1;
            }

            this.obj.get(0).setPosY(temp);


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

        mAx = Math.signum(mAx) * Math.abs(mAx);
        mAy = Math.signum(mAy) * Math.abs(mAy);
        redraw((int)mAx  ,(int)mAy);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
