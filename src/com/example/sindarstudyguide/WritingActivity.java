package com.example.sindarstudyguide;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.AttributeSet;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View.OnTouchListener;
import android.view.View;

import java.util.ArrayList;
import java.util.Iterator;

class MySurfaceView extends SurfaceView {
    
    private static final int width = 400;
    private static final int height = 400;
    Bitmap.Config config = Bitmap.Config.RGB_565;
    Bitmap bitmap = Bitmap.createBitmap(width, height, config);
    Canvas canvas = new Canvas(bitmap);
    private Path path;
    Paint paint;

    public MySurfaceView(Context context, AttributeSet attrs){
        super(context, attrs);
        this.paint = new Paint();
        this.paint.setColor(Color.BLACK);
        this.paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            this.path = new Path();
            this.path.moveTo(event.getX(), event.getY());
            this.path.lineTo(event.getX(), event.getY());
        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
            this.path.lineTo(event.getX(), event.getY());
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            this.path.lineTo(event.getX(), event.getY());
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawPath(this.path, this.paint);
    }
}

public class WritingActivity extends Activity {

    private ArrayList graphics  = new ArrayList();
    private Paint mPaint;
    private SurfaceView mySurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Intent intent = getIntent();
        Resources res = getResources();
        mySurfaceView = new SurfaceView(this);
        setContentView(mySurfaceView);        
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        switch (item.getItemId()) {
        case android.R.id.home:
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
/*    @Override
    public boolean onTouch(View v, MotionEvent event){
//        synchronized (event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                path = new Path();
                path.moveTo(event.getX(), event.getY());
                path.lineTo(event.getX(), event.getY());
            }else if(event.getAction() == MotionEvent.ACTION_MOVE){
                path.lineTo(event.getX(), event.getY());
            }else if(event.getAction() == MotionEvent.ACTION_UP){
                path.lineTo(event.getX(), event.getY());
                graphics.add(path);
           }
        return true;
//        }
    }

    @Override
    public void onDraw(Canvas canvas)
    {
        super.onDraw(canvas)
        for(Iterator<Path> graphicsIter = graphics.iterator(); graphicsIter.hasNext();) {
//        for(Path path : graphics){
//            canvas.drawPath(path, mPaint);
            canvas.drawPath(graphicsIter.next(), mPaint);
        }
    }
}*/
