package com.wenld.commonweight;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.wenld.commonweights.MicrophoneView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/1/21.
 */

public class RecordActivity extends Activity {
    Button btnBe;
    Button btnFlag;
    MicrophoneView microphoneView_layout_record;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int db = (int) (Math.random() * 80);
            microphoneView_layout_record.setVolume(db);
        }
    };
    private TimerTask timeTask;
    private Timer timeTimer = new Timer(true);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        btnBe = (Button) findViewById(R.id.btn_beging);
        btnFlag = (Button) findViewById(R.id.btn_flag);

        microphoneView_layout_record = (MicrophoneView) findViewById(R.id.microphoneView_layout_record);
        btnFlag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                microphoneView_layout_record.setModel(MicrophoneView.MODEL_LOADING);
                if (timeTask != null)
                    timeTask.cancel();
            }
        });
        btnBe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                microphoneView_layout_record.setModel(MicrophoneView.MODEL_RECORD);
                timeTimer.schedule(timeTask = new TimerTask() {
                    public void run() {
                        Message msg = new Message();
                        msg.what = 1;
                        handler.sendMessage(msg);
                    }
                }, 50, 100);
            }
        });
    }
}
