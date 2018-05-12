package com.example.user.tugasbesarpab;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 5/1/2018.
 */

public class Presenter {
    private MainActivity ui;
    protected ArrayList<Integer> highScoreArrayList;
    private PenghitungScore penghitungScore;
    private SettingManager settingManager;
    private Setting setting;

    public Presenter(MainActivity ui) {
        this.ui = ui;
        this.highScoreArrayList=new ArrayList<Integer>();
        this.getHighScoreFromWebService(1);
        this.penghitungScore=new PenghitungScore();

        this.settingManager =new SettingManager(this.ui);


    }

    //yg disimpen tuh index spinner nya
    public void saveSettings(int speed,int color1, int color2,int bonus){
        this.settingManager.saveSettings(speed,color1,color2,bonus);
    }

    //load index nya ke settings fragment
    public int[] loadSettings(){
        return this.settingManager.loadSettings();
    }



    public ArrayList<Integer> getHighScoreList(){
        return this.highScoreArrayList;
    }

    public void updateHighScoreArray(){


        Collections.sort(this.highScoreArrayList, Collections.reverseOrder());
        int size= this.highScoreArrayList.size();
        this.highScoreArrayList.remove(size-1);

        this.ui.updateListView();


         this.updateHighScoreToWebService();

    }

    public Setting getSetting(){
        Setting setting=new Setting(this.ui.settingsFragment.getSpinnerSpeedValue(),this.ui.settingsFragment.getSpinnerColor1Value(),this.ui.settingsFragment.getSpinnerColor2Value(),this.ui.settingsFragment.getSpinnerBonusValue());
        return setting;
    }

    public void addNewScore(int score){

        this.highScoreArrayList.add(score);
        this.updateHighScoreArray();
    }

    public void getHighScoreFromWebService(final int page){
        if(page<8){
            RequestQueue queue = Volley.newRequestQueue(this.ui);
            String url ="http://pab.labftis.net/api.php?api_key=2015730053&page="+page;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                System.out.println(response);
                                JSONObject json=new JSONObject(response);
                                JSONArray data=json.getJSONArray("data");

                                for (int i=0;i<data.length();i++){
                                    JSONObject object=data.getJSONObject(i);
                                    int score=object.getInt("value");
                                    highScoreArrayList.add(score);
                                }

                                getHighScoreFromWebService(page+1);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });

            queue.add(stringRequest);
        }
        else if(page==8){
              this.ui.updateListView();
        }
    }

    public void updateHighScoreToWebService(){
        for(int i=0;i<this.highScoreArrayList.size();i++){
            RequestQueue queue = Volley.newRequestQueue(this.ui);
            String url ="http://pab.labftis.net/api.php";
            final int index=i;
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            System.out.println(response);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            })
            {
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String, String>();
                    params.put("api_key","2015730053");
                    params.put("order",index+1+"");
                    params.put("value", highScoreArrayList.get(index)+"");

                    return params;
                }

            };

            queue.add(stringRequest);
        }

    }

    public int getScore(int count,int bonusCounter){
        return this.penghitungScore.getScore(count,bonusCounter);
    }


}
