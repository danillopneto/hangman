package ufg.go.br.hangman.Util;
import android.content.Context;
import android.media.MediaPlayer;
import ufg.go.br.hangman.R;

/**
 * Created by Vinicius on 25/04/2018.
 */

public class SoundGame {
s
    MediaPlayer mp;

    Context context;

    public SoundGame(Context ct){
        this.context = ct;
    }



    public void playMusicBehind(){
        mp = MediaPlayer.create(context, R.raw.kalimba);
        mp.start();
        mp.setLooping(true);
    }


    public void playMusicButton(){
        mp = MediaPlayer.create(context, R.raw.facebook);
        mp.start();
    }


}
