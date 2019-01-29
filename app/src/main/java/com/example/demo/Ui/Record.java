package com.example.demo.Ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

import com.example.demo.R;

public class Record extends AppCompatActivity {
    private VideoView videoView;

    private static final int REQUEST_VIDEO_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_video);

        videoView = findViewById(R.id.img);
        findViewById(R.id.btn_record).setOnClickListener(v -> {

            Intent takePictureIntent =new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            startActivityForResult(takePictureIntent,REQUEST_VIDEO_CAPTURE);

        });
        findViewById(R.id.btn_play).setOnClickListener(v ->{
            if(videoView.isPlaying()){
                videoView.pause();
            }
            else{
                videoView.start();
            }
        });

        findViewById(R.id.btn_p).setOnClickListener(v ->{
            startActivity(new Intent(this, Post.class));
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            //todo 播放刚才录制的视频
            Uri videoUri=intent.getData();
            videoView.setVideoURI(videoUri);
            videoView.start();
        }
    }
}
