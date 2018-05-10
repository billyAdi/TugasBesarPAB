package com.example.user.tugasbesarpab;

import android.content.Context;
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
    private int[][] posisi;
    protected TimerAsyncTask timerAsyncTask;

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;

    protected Canvas mCanvas;
    protected Bitmap mBitmap;
    protected Paint paint1,paint2;

    private float mAx;
    private float mAy;
    private final float mDelay = 2f;

    private boolean isCanvasInitiated;
    private boolean isTimerStarted;
    private boolean status;
    private boolean isSet;

    private float pitch,roll;

    private int count;

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
        this.timeTv=view.findViewById(R.id.time_tv);
        this.ivCanvas=view.findViewById(R.id.iv_canvas);
        this.btnNew=view.findViewById(R.id.canvas_btn_new);
        this.btnPause=view.findViewById(R.id.canvas_pause_btn);
        mSensorManager = (SensorManager)getActivity().getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        this.btnNew.setOnClickListener(this);
        this.btnPause.setOnClickListener(this);


        this.isCanvasInitiated=false;
        this.isTimerStarted=false;
        this.status=false;
        this.isSet=false;

        this.posisi=new int[2][2];

        this.pitch=0;
        this.roll=0;
        this.mAx=0;
        this.mAy=0;

        this.setTimeTv("00 : 00");
        return view;
    }

    public void setTimeTv(String time){
        this.timeTv.setText(time);
    }

    public void startTimer(){
        this.timerAsyncTask=new TimerAsyncTask(this,this.count);
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

    public void resetFragment(){
        this.stopTimer();
        this.resetCanvas();

        this.isCanvasInitiated=false;
        this.isTimerStarted=false;
        this.status=false;
        this.isSet=false;

        this.posisi=new int[2][2];

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
           /** if(this.isCanvasInitiated==false){
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
            */
           /**
            this.stopTimer();
            this.resetCanvas();

            this.isCanvasInitiated=false;
            this.isTimerStarted=false;
            this.status=false;
            this.isSet=false;

            this.posisi=new int[2][2];

            this.pitch=0;
            this.roll=0;
            this.azimuth=0;
            this.mAx=0;
            this.mAy=0;

            mSensorManager.registerListener(this, mAccelerometer, (int) mDelay);

            this.btnPause.setText("PAUSE");
        */
            this.resetFragment();

            this.initializeCanvas();
            this.draw();
            this.startTimer();

            this.isTimerStarted=true;
            this.isCanvasInitiated=true;

        }
        else if(view.getId()==this.btnPause.getId()){
            if(this.status==false){
                mSensorManager.unregisterListener(this);
                this.btnPause.setText("RESUME");
                this.stopTimer();
                this.status=true;
                System.out.println(this.count);
            }
            else {
                mSensorManager.registerListener(this, mAccelerometer, (int) mDelay);
                this.btnPause.setText("PAUSE");
                this.startTimer();
                this.isTimerStarted=true;
                this.status=false;
            }

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


        int posX = (int)(Math.random() * ivCanvas.getWidth());
        int posY = (int)(Math.random() * ivCanvas.getHeight());
        posisi[0][0]=posX;
        posisi[0][1]=posY;

        this.mCanvas.drawCircle(posX,posY,10,this.paint1);
        posX = (int)(Math.random() * ivCanvas.getWidth());
        posY = (int)(Math.random() * ivCanvas.getHeight());
        posisi[1][0]=posX;
        posisi[1][1]=posY;
        this.mCanvas.drawCircle(posX,posY,15,this.paint2);
        this.ivCanvas.invalidate();
    }

    public void redraw(int posxS,int posyS){
        if(this.isCanvasInitiated){
            this.resetCanvas();
            this.posisi[0][0]=(int)(this.posisi[0][0]+(posxS));
            this.posisi[0][1]=(int)(this.posisi[0][1]-(posyS));
            if(this.posisi[0][0]<0){
                this.posisi[0][0]=1;
            }
            else if(this.posisi[0][0]>ivCanvas.getWidth()){
                this.posisi[0][0]=ivCanvas.getWidth()-1;
            }
            if(this.posisi[0][1]<0){
                this.posisi[0][1]=1;
            }
            else if(this.posisi[0][1]>ivCanvas.getHeight()){
                this.posisi[0][1]=ivCanvas.getHeight()-1;
            }
            this.mCanvas.drawCircle( this.posisi[0][0],this.posisi[0][1],10,this.paint1);
            this.mCanvas.drawCircle(this.posisi[1][0],this.posisi[1][1],15,this.paint2);
            this.ivCanvas.invalidate();
        }


    }
    @Override
    public void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, (int) mDelay);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
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
