package com.example.demo;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import static android.support.v7.widget.RecyclerView.ViewHolder;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.Intent;
import com.bumptech.glide.Glide;
import com.example.demo.Ui.Post;
import com.example.demo.Ui.Record;
import com.example.demo.Ui.playActivitty;
import com.example.demo.bean.Feed;
import com.example.demo.bean.FeedResponse;
import com.example.demo.newtork.IMiniDouyinService;
import com.example.demo.newtork.RetrofitManager;
import com.example.demo.result.myresult;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO};
    public static class MyViewHolder extends ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
    private Button mBtnRefresh;
    private ImageButton addbtn;
    private RecyclerView mRv;
    private List<Feed> mFeeds = new ArrayList<>();
    private static final String TAG = "MainActivity";
    private static final int REQUEST_EXTERNAL_CAMERA = 101;

    // 选择上下文菜单
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getGroupId()) {
            case 0:
            {
                switch (item.getItemId()) {
                    case 1:
                    {
                        Intent intent3=new Intent(MainActivity.this, Post.class);
                        startActivity(intent3);
                        Toast.makeText(MainActivity.this, "本地上传", Toast.LENGTH_LONG).show();
                    }break;
                    case 2:
                    {
                        Intent intent4=new Intent(MainActivity.this, Record.class);
                        startActivity(intent4);
                        Toast.makeText(MainActivity.this, "拍摄上传", Toast.LENGTH_LONG).show();
                    }break;
                    case 3:
                    {
                        Toast.makeText(MainActivity.this, "我的记录", Toast.LENGTH_LONG).show();
                        Intent intent2=new Intent(MainActivity.this, myresult.class);
                        startActivity(intent2);
                    }break;
                    default:break;
                }
            }
            default:break;
        }
        return super.onContextItemSelected(item);
    }


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRecyclerView();
        //fetchFeed();//先刷一编
        mBtnRefresh = findViewById(R.id.btn_refresh);
        mBtnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchFeed();
            }
        });

//        FrameLayout frameLayout=findViewById(R.id.mylayout);
//        final myview add=new myview(this);
//        add.setOnTouchListener(new View.OnTouchListener()
//        {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                boolean mScrolling=true;
//                long currentMS = 0;
//                float movex=0;
//                float movey=0;
//                float qianx=0;
//                float qiany=0;
//                add.bitmapx = event.getX()-20;
//                add.bitmapx = event.getY()-20;
////                switch (event.getAction()) {
////                    case MotionEvent.ACTION_DOWN:
////                        qianx = event.getX();
////                        qiany = event.getY();
////                        movex=0;
////                        movey=0;
////                        currentMS = System.currentTimeMillis();//long currentMS     获取系统时间
////                        mScrolling = false;
////                        break;
////                    case MotionEvent.ACTION_MOVE:
////                        movex +=Math.abs(event.getX()-qianx);
////                        movey +=Math.abs(event.getY()-qiany);
////                        break;
////                    case MotionEvent.ACTION_UP:
////                        long movetime=System.currentTimeMillis()-currentMS;
////                        if(movetime>150&&(movex>20||movey>20))
////                        {
////                            add.invalidate();
////                            return true;
////                        }
////                        else
////                        {
////
////                        }
////                        break;
////                }
////                return false;
//                add.invalidate();
//                return true;
//            }
//        });
        List<String> permissionList = new ArrayList<>();
        for (int i = 0; i < permissions.length; i++) {//for循环把需要授权的权限都添加进来
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {  //未授权就进行授权
                permissionList.add(permissions[i]);
            }
        }
        //如果permissionList是空的，说明没有权限需要授权,什么都不做，否则就发起授权请求
        if (!permissionList.isEmpty()) {
            startRequestPermission(permissionList);
        }


        addbtn=findViewById(R.id.addview);
        addbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                addbtn.showContextMenu();
            }
        });
        // 创建上下文菜单listener
        addbtn.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(0,1,0,"本地上传");
                menu.add(0,2,0,"拍摄上传");
                menu.add(0,3,0,"我的记录");
            }
        });
        //frameLayout.addView(myview);
    }

    // 开始提交请求权限
    private void startRequestPermission(List<String> permissionList) {
        if (!permissionList.isEmpty()) {//不为空就进行授权申请
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_EXTERNAL_CAMERA);
        }
    }
    //回调结果
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_CAMERA: {
                //todo 判断权限是否已经授予
                if (grantResults.length > 0) {
                    //安全写法，如果小于0，肯定会出错了
                    for (int i = 0; i < grantResults.length; i++) {
                        int grantResult = grantResults[i];
                        switch (grantResult) {
                            case PackageManager.PERMISSION_GRANTED://同意授权0
                                break;
                            case PackageManager.PERMISSION_DENIED://拒绝授权-1
                                Log.e("-------->", "授权请求被拒绝");
                                Toast.makeText(getApplicationContext(), "您禁止了此权限,请在设置中开启", Toast.LENGTH_SHORT).show();
                                finish();
                                break;
                        }
                    }
                }
            }
        }
    }

    private void initRecyclerView() {
        mRv = findViewById(R.id.rv);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mRv.setAdapter(new RecyclerView.Adapter() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                ImageView imageView = new ImageView(viewGroup.getContext());
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                imageView.setAdjustViewBounds(true);
                return new MainActivity.MyViewHolder(imageView);
            }

            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                ImageView iv = (ImageView) viewHolder.itemView;
                iv.setMinimumHeight(mRv.getHeight());
                Glide.with(iv.getContext()).load(mFeeds.get(i).getImageUrl()).into(iv);


                Bundle bundle = new Bundle();
                bundle.putString("useid",mFeeds.get(i).getStudentId());
                bundle.putString("usename",mFeeds.get(i).getUserName());
                bundle.putString("imageurl",mFeeds.get(i).getImageUrl());
                bundle.putString("videourl",mFeeds.get(i).getVideoUrl());
                final Intent intent= new Intent(MainActivity.this, playActivitty.class);
                intent.putExtras(bundle);

                iv.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(MainActivity.this, intent.getExtras().getString("videourl"), Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    }
                });
            }

            @Override public int getItemCount() {
                return mFeeds.size();
            }
        });
        //Toast.makeText(MainActivity.this, "size:"+mFeeds.size(), Toast.LENGTH_LONG).show();//0
    }

    public void fetchFeed() {
        mBtnRefresh.setText("requesting...");
        mBtnRefresh.setEnabled(false);
        RetrofitManager.get(IMiniDouyinService.HOST).create(IMiniDouyinService.class).fetchFeed().enqueue(new Callback<FeedResponse>() {
            @Override
            public void onResponse(Call<FeedResponse> call, Response<FeedResponse> response) {
                Log.d(TAG, "onResponse() called with: call = [" + call + "], response = [" + response.body() + "]");
                if (response.isSuccessful()) {
                    mFeeds = response.body().getFeeds();
                    mRv.getAdapter().notifyDataSetChanged();

                    //Toast.makeText(MainActivity.this, "size:"+mFeeds.size(), Toast.LENGTH_LONG).show();
                } else {
                    Log.d(TAG, "onResponse() called with: response.errorBody() = [" + response.errorBody() + "]");
                    Toast.makeText(MainActivity.this, "fetch feed failure!", Toast.LENGTH_LONG).show();
                }
                resetBtn();
            }

            @Override public void onFailure(Call<FeedResponse> call, Throwable t) {
                Log.d(TAG, "onFailure() called with: call = [" + call + "], t = [" + t + "]");
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                resetBtn();
            }

            private void resetBtn() {
                mBtnRefresh.setText(R.string.refresh_feed);
                mBtnRefresh.setEnabled(true);
            }
        });
    }
}
