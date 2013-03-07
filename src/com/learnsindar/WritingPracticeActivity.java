package com.learnsindar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Vector;
import java.util.Iterator;

class DrawView extends View implements OnTouchListener {
    
    Vector<Path> paths = new Vector<Path>();
    Path path;
    Paint paint = new Paint();

    public DrawView(Context context){
        super(context);
        setFocusableInTouchMode(true);
        path = new Path();
        this.setOnTouchListener(this);
        paint.setColor(Color.WHITE);
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas){
        for(Path path : paths){
            canvas.drawPath(path, this.paint);
        }
        canvas.drawPath(this.path, this.paint);
    }

    public boolean onTouch(View view, MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            this.path = new Path();
            this.path.moveTo(event.getX(), event.getY());
            this.path.lineTo(event.getX(), event.getY());
        }else if(event.getAction() == MotionEvent.ACTION_MOVE){
            this.path.lineTo(event.getX(), event.getY());
        }else if(event.getAction() == MotionEvent.ACTION_UP){
            this.path.lineTo(event.getX(), event.getY());
            this.paths.add(this.path);
        }
        invalidate();
        return true;
    }

}

public class WritingPracticeActivity extends Activity {

    DrawView drawView;
    Button nextCharButton;
    RelativeLayout writingLayout;
    TextView charDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        writingLayout = new RelativeLayout(this);
        charDisplay = new TextView(this);
        charDisplay.setText("asdfasdf");
        nextCharButton = new Button(this);
        nextCharButton.setText("FUCK");
        nextCharButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v){
            nextCharButton.setText("OMG");
            }
        });
        nextCharButton.setVisibility(View.VISIBLE);
        drawView = new DrawView(this);
        writingLayout.setGravity(1);
        writingLayout.addView(drawView);
        writingLayout.addView(nextCharButton);
        writingLayout.addView(charDisplay);
        setContentView(writingLayout);
    }

    void
    nextWritingChar(View view){
        int x= 0;
    }
}
