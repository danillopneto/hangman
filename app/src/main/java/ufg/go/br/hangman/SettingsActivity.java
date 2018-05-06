package ufg.go.br.hangman;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;
import android.widget.Switch;
import ufg.go.br.hangman.Util.SoundGame;
import ufg.go.br.hangman.Util.VibrationGame;

public class SettingsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

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

        // setta os valores do shared preferences, caso não tenha nenhum o valor padrão é falso
        switchsound.setChecked(getSwitch("switchsound"));
        switchvibrate.setChecked(getSwitch("switchvibrate"));
        switchreveal.setChecked(getSwitch("switchreveal"));

        setMusicOnOff();
        setVibrateOnOff();
        setRevealOnOff();
    }

    public void setMusicOnOff() {
        switchsound = (Switch) findViewById(R.id.switchsound);
        switchsound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switchsound.isChecked()){
                    saveSwitch("switchsound", true);
                }
                else {
                    saveSwitch("switchsound", false);
                }
            }
        });

    }

    public void setVibrateOnOff() {
        switchvibrate = (Switch) findViewById(R.id.switchvibrate);
        switchvibrate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switchvibrate.isChecked()){
                    saveSwitch("switchvibrate", true);
                }
                else {
                    saveSwitch("switchvibrate", false);
                }
            }
        });
    }


    public void setRevealOnOff() {
        switchreveal = (Switch) findViewById(R.id.switchreveal);
        switchreveal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(switchreveal.isChecked()){
                    saveSwitch("switchreveal", true);
                }
                else {
                    saveSwitch("switchreveal", false);
                }
            }
        });
    }

    private boolean getSwitch(String key){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("settings", android.content.Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }
    private void saveSwitch(String key,boolean value){
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("settings", android.content.Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch(buttonView.getId()){
            case R.id.switchreveal:
                saveSwitch("switchreveal",isChecked);
                break;
            case R.id.switchvibrate:
                saveSwitch("switchvibrate",isChecked);
                break;
            case R.id.switchsound:
                saveSwitch("switchsound",isChecked);
                break;
        }
    }
}
