package ufg.go.br.hangman;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;

import ufg.go.br.hangman.Util.SoundGame;
import ufg.go.br.hangman.Util.VibrationGame;

public class SettingsActivity extends AppCompatActivity {

    Switch switchsound;
    Switch switchvibrate;
    Switch switchreveal;
    SoundGame sg;
    VibrationGame vb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        switchsound = (Switch) findViewById(R.id.switchsound);
        switchvibrate = (Switch) findViewById(R.id.switchvibrate);
        switchreveal = (Switch) findViewById(R.id.switchreveal);


        SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed  = preferences.edit();

        switchsound.setChecked(preferences.getBoolean("switchsound",true));
        switchvibrate.setChecked(preferences.getBoolean("switchvibrate",true));
        switchreveal.setChecked(preferences.getBoolean("switchreveal",true));



    }


    public void setMusicOnOff(View v) {

        switchsound = (Switch) findViewById(R.id.switchsound);

        switchsound.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed  = preferences.edit();

                if(switchsound.isChecked()){
                    ed.putBoolean("switchsound", true);
                }
                else {
                    ed.putBoolean("switchsound", false);
                }
            }
        });



    }

    public void setVibrateOnOff(View v) {

        switchvibrate = (Switch) findViewById(R.id.switchvibrate);

        switchvibrate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed  = preferences.edit();

                if(switchvibrate.isChecked()){
                    ed.putBoolean("switchvibrate", true);
                }
                else {
                    ed.putBoolean("switchvibrate", false);
                }
            }
        });



    }


    public void setRevealOnOff(View v) {

        switchreveal = (Switch) findViewById(R.id.switchreveal);

        switchreveal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SharedPreferences preferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
                SharedPreferences.Editor ed  = preferences.edit();

                if(switchsound.isChecked()){
                    ed.putBoolean("switchreveal", true);
                }
                else {
                    ed.putBoolean("switchreveal", false);
                }
            }
        });



    }

}
