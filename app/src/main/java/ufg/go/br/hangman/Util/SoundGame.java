package ufg.go.br.hangman.Util;
import android.content.Context;
import android.media.MediaPlayer;
import ufg.go.br.hangman.R;

/**
 * Created by Vinicius on 25/04/2018.
 */

public class SoundGame {
    MediaPlayer mpBackground;
    MediaPlayer mpButton;
    Context context;

    public SoundGame(Context ct){
        this.context = ct;
    }

    public void playMusicBehind(){
        mpBackground = MediaPlayer.create(context, R.raw.kalimba);
        if (!mpBackground.isPlaying()) {
            mpBackground.start();
            mpBackground.setLooping(true);
        }
    }

    public void playMusicButton(){
        mpButton = MediaPlayer.create(context, R.raw.facebook);
        mpButton.start();
    }

    public void stopMusicBehind() {
        if (mpBackground != null) {
            mpBackground.stop();
        }
    }
}
