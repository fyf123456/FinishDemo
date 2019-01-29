package com.example.demo.Ui;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.demo.R;
import com.example.demo.result.showresult;

public class playActivitty extends AppCompatActivity {
    private int count = 0;//点击次数
    private long firstClick = 0;//第一次点击时间
    private long secondClick = 0;//第二次点击时间
    private final int totalTime = 350;//两次点击时间间隔，单位毫秒
    private String string;
    private String useid;
    private String usename;
    private String imageurl;
    private String videourl;

    private FrameLayout frameLayout;
    private VideoView video;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=new MenuInflater(this);
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.shou:
            {
                showresult.INSTENTS.setnum2();
                showresult.INSTENTS.setArrayList2(string);
                Toast.makeText(playActivitty.this, "收藏成功", Toast.LENGTH_LONG).show();
            }break;
            case R.id.fenx:
            {
                showresult.INSTENTS.setnum3();
                showresult.INSTENTS.setArrayList3(string);
                Toast.makeText(playActivitty.this, "分享成功", Toast.LENGTH_LONG).show();
            }break;
            case R.id.jubao:
            {
                showresult.INSTENTS.setnum4();
                showresult.INSTENTS.setArrayList4(string);
                Toast.makeText(playActivitty.this, "举报成功", Toast.LENGTH_LONG).show();
            }break;
            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play);

        frameLayout=findViewById(R.id.search_edit_frame);
        frameLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_DOWN == event.getAction()) {//按下
                    count++;
                    if (1 == count) {
                        firstClick = System.currentTimeMillis();//记录第一次点击时间
                    } else if (2 == count) {
                        secondClick = System.currentTimeMillis();//记录第二次点击时间
                        if (secondClick - firstClick < totalTime) {//判断二次点击时间间隔是否在设定的间隔时间之内
                            showresult.INSTENTS.setnum1();
                            Toast.makeText(playActivitty.this, "点赞成功", Toast.LENGTH_SHORT).show();
                            showresult.INSTENTS.setArrayList1(string);
                            //弹出图标

                            count = 0;
                            firstClick = 0;
                        } else {
                            firstClick = secondClick;
                            count = 1;
                        }
                        secondClick = 0;
                    }
                }
                return true;
            }
        });



        video = findViewById(R.id.video);
        Intent intent = getIntent();
        Bundle bundle=intent.getExtras();
        useid=bundle.getString("useid");
        usename=bundle.getString("usename");
        imageurl=bundle.getString("imageurl");
        videourl=bundle.getString("videourl");
        string="useid:"+useid+"//usename:"+usename;

        TextView textView1=findViewById(R.id.btnname);
        textView1.append(" "+useid+"  "+usename);
        TextView textView2=findViewById(R.id.btntime);
        textView2.append(" "+System.currentTimeMillis());

        video.setVideoPath(videourl);
        android.widget.MediaController mc=new android.widget.MediaController(playActivitty.this);
        video.setMediaController(mc);
        video.requestFocus();
        video.start();
        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(playActivitty.this, "视频播放结束", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
