package com.example.user.tugasbesarpab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 5/1/2018.
 */

public class HighScoreAdapter extends BaseAdapter {
    protected ArrayList<Integer> highScoreArrayList;
    protected MainActivity ui;

    public HighScoreAdapter(MainActivity ui) {
        this.ui = ui;
    }

    public void setArray(ArrayList<Integer> highScore){
        this.highScoreArrayList=highScore;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.highScoreArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return this.highScoreArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view==null){
            view= LayoutInflater.from(this.ui).inflate(R.layout.highscore_item,viewGroup,false);
            viewHolder=new ViewHolder(view,i);
            view.setTag(viewHolder);
        }
        else{
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.setPosition(i);
        viewHolder.updateView(this.highScoreArrayList.get(i));

        return view;
    }

    private class ViewHolder{
        private int position;
        protected TextView orderTV,highScoreTV;

        public ViewHolder( View view,int position) {
            this.position = position;
            this.orderTV=view.findViewById(R.id.order_tv);
            this.highScoreTV=view.findViewById(R.id.highscore_tv);
        }

        public void updateView(int angka){
            this.orderTV.setText(this.position+1+"");
            this.highScoreTV.setText(angka+"");
        }

        public void setPosition(int position){
            this.position=position;
        }

    }
}
