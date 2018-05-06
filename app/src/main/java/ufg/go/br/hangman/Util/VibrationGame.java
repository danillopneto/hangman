package ufg.go.br.hangman.Util;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Vibrator;

import ufg.go.br.hangman.R;

/**
 * Created by Vinicius on 30/04/2018.
 */

public class VibrationGame {

    Vibrator  mpBackground;
    Context context;

    public VibrationGame(Context ct){
        this.context = ct;
    }

    public void startVibrateBehind(){

        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(50);
    }

}
