package ufg.go.br.hangman;

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

    }



    public void setMusicOnOff(View v) {

        switchsound = (Switch) findViewById(R.id.switchsound);

        if(switchsound.isChecked()){
            sg.playMusicBehind();
        }
        else {
            sg.stopMusicBehind();
        }
    }

    public void setVibrateOnOff(View v) {

        switchvibrate = (Switch) findViewById(R.id.switchvibrate);

        if(switchvibrate.isChecked()){
            vb.startVibrateBehind();
        }
    }


    public void setRevealOnOff(View v) {


    }

}
