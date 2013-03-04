package com.learnsindar;

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
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View.OnTouchListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

class DrawView extends View implements OnTouchListener {
    
    List<Point> points  = new ArrayList<Point>();
    Paint paint = new Paint();

    public DrawView(Context context){
        super(context);
        Log.v(MainActivity.logtag, "creating draw view");
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setOnTouchListener(this);
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        Log.v(MainActivity.logtag, "created draw view");
    }

    @Override
    protected void onDraw(Canvas canvas){
        for(Point point : points){
            canvas.drawCircle(point.x, point.y, 5, paint);
        }
        //canvas.drawPath(this.path, this.paint);
    }

    public boolean onTouch(View view, MotionEvent event){
        /*if(event.getAction() == MotionEvent.ACTION_DOWN){
            this.path = new Path();
            this.path.moveTo(event.getX(), event.getY());
            this.path.lineTo(event.getX(), event.getY());
        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
            this.path.lineTo(event.getX(), event.getY());
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            this.path.lineTo(event.getX(), event.getY());
        }
        return true;*/
        Point point = new Point();
        point.x = event.getX();
        point.y = event.getY();
        points.add(point);
        invalidate();
        return true;       
    }

}

class Point{
    float x, y;
    } 

public class WritingPracticeActivity extends Activity {

    DrawView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.v(MainActivity.logtag, "creating writing practice activity");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        drawView = new DrawView(this);
        setContentView(drawView);
        drawView.requestFocus();
        Log.v(MainActivity.logtag, "Created writing practice activity");
        //Intent intent = getIntent();
        //Resources res = getResources();
    }
}
