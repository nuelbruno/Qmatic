package ae.qmatic.tacme.utils;

import android.content.Context;
import android.media.MediaPlayer;

import ae.qmatic.tacme.R;

/**
 * Created by mdev3 on 8/25/16.
 */
public class MediaUtils {
    MediaPlayer mPlayer;
    Context mContext;

    MediaUtils(Context mContext) {

        this.mContext = mContext;

    }

    public void startPlayer() {

        mPlayer = MediaPlayer.create(mContext, R.raw.beep);
        mPlayer.start();

    }
    public void stopPlayer(){
       if(mPlayer.isPlaying()){
           mPlayer.stop();
           mPlayer.release();
           mPlayer =null;
       }
    }

}
