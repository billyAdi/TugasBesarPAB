package com.example.user.tugasbesarpab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Created by user on 4/30/2018.
 */

public class SettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    protected Spinner spinnerSpeed,spinnerColor1,spinnerColor2,spinnerBonus;


    public SettingsFragment() {
    }

    public static SettingsFragment newInstance(){
        SettingsFragment settingsFragment=new SettingsFragment();
        return settingsFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.settings_fragment,container,false);


        this.spinnerSpeed=view.findViewById(R.id.spinner_speed);
        this.spinnerColor1=view.findViewById(R.id.spinner_color1);
        this.spinnerColor2=view.findViewById(R.id.spinner_color2);
        this.spinnerBonus=view.findViewById(R.id.spinner_bonus);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getContext(),
                R.array.speedArray, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),
                R.array.color1Array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(getContext(),
                R.array.color2Array, android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(getContext(),
                R.array.bonusArray, android.R.layout.simple_spinner_item);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        this.spinnerSpeed.setAdapter(adapter1);
        this.spinnerColor1.setAdapter(adapter2);
        this.spinnerColor2.setAdapter(adapter3);
        this.spinnerBonus.setAdapter(adapter4);



        this.spinnerSpeed.setOnItemSelectedListener(this);
        this.spinnerColor1.setOnItemSelectedListener(this);
        this.spinnerColor2.setOnItemSelectedListener(this);
        this.spinnerBonus.setOnItemSelectedListener(this);

        return view;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(adapterView.getId()==this.spinnerSpeed.getId()){

        }
        else if(adapterView.getId()==this.spinnerColor1.getId()){
        }
        else if(adapterView.getId()==this.spinnerColor2.getId()){

        }
        else if(adapterView.getId()==this.spinnerBonus.getId()){

        }

        String speed=this.spinnerSpeed.getSelectedItem().toString();
        String color1=this.spinnerColor1.getSelectedItem().toString();
        String color2=this.spinnerColor2.getSelectedItem().toString();
        int bonus=Integer.parseInt(this.spinnerBonus.getSelectedItem().toString());

        ((MainActivity)getActivity()).getPresenter().saveSettings(speed,color1,color2,bonus);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
