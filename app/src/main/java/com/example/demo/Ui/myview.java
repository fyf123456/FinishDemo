package com.example.demo.Ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.example.demo.R;

public class myview extends View {
    public float bitmapx;
    public float bitmapy;
    public myview(Context context) {
        super(context);
        bitmapx=300;
        bitmapy=600;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint=new Paint();
        Bitmap bitmap= BitmapFactory.decodeResource(this.getResources(), R.mipmap.addd);
        canvas.drawBitmap(bitmap,bitmapx,bitmapy,paint);
        if(bitmap.isRecycled())
        {
            bitmap.recycle();
        }
    }
}
