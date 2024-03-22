package com.scanner.hardware.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.scanner.hardware.R;


public class SoundPlayUtil {
    @SuppressWarnings("deprecation")
    public static SoundPool mSoundPlayer = new SoundPool(10, AudioManager.STREAM_MUSIC, 5);
    public static SoundPlayUtil soundPlayUtil;
    public static Context mContext;
    public static int soundID;

    public static SoundPlayUtil init(Context context, int voiceState) {
        if (soundPlayUtil == null) soundPlayUtil = new SoundPlayUtil();
        mContext = context;
        switch (voiceState) {
            case 1 -> soundID = mSoundPlayer.load(mContext, R.raw.voice1, 1);
            case 2 -> soundID = mSoundPlayer.load(mContext, R.raw.voice2, 1);
            default -> soundID = mSoundPlayer.load(mContext, R.raw.voice, 1);
        }

        return soundPlayUtil;
    }

    public static void play() {
        mSoundPlayer.play(soundID, 1, 1, 0, 0, 1);
    }

    public static void release() {
        mSoundPlayer.unload(soundID);
    }
}