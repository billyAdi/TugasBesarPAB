package com.example.user.tugasbesarpab;

import android.graphics.Canvas;
import android.os.AsyncTask;

/**
 * Created by user on 5/1/2018.
 */

public class TimerAsyncTask extends AsyncTask<Void,Integer,String> {
    private int count;
    protected CanvasFragment canvasFragment;

    public TimerAsyncTask(CanvasFragment canvasFragment) {
        this.canvasFragment=canvasFragment;
        this.count=0;
    }

    @Override
    protected String doInBackground(Void... voids) {
        for(int i=0;i<3600;i++){

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.count++;
            this.publishProgress(this.count);

        }
        return "finished";
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);

        int minutes=this.count/60;
        int secondes=this.count%60;

        String min="";
        String sec="";

        if(minutes<10){
            min="0"+minutes;
        }
        else{
            min=minutes+"";
        }

        if(secondes<10){
            sec="0"+secondes;
        }
        else{
            sec=secondes+"";
        }

        this.canvasFragment.setTimeTv(min+" : "+sec);
    }

    @Override
    protected void onPostExecute(String result){
        System.out.println(result);
    }


}
