package com.wenld.commonweight;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.wenld.commonweights.WaveLoadingView;


public class WaveActivity extends AppCompatActivity {
    private SeekBar mSeekBar;
    private WaveLoadingView mWaveLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWaveLoadingView = (WaveLoadingView) findViewById(R.id.waveLoading);
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mWaveLoadingView.setPercent(progress);
//                mWaveLoadingView.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
