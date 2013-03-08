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
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

import java.util.Vector;
import java.util.Iterator;

import com.learnsindar.PhenomeFlashCardActivity;

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

    public void clearView(){
        this.path = new Path();
        this.paths.removeAllElements();
        invalidate();
    }

}

public class WritingPracticeActivity extends Activity {

    FlashCard flashCard;
    DrawView drawView;
    Button nextCharButton;
    RelativeLayout writingLayout;
    LinearLayout buttonLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Resources res = getResources();
        Intent intent = getIntent();
        boolean[] categories = intent.getBooleanArrayExtra(MainActivity.CATEGORY_STATES);
        Vector<Vector<String>> cards = PhenomeFlashCardActivity.getCategories(categories, res);
        Vector<String> english_cards = cards.get(0);
        Vector<String> sindar_cards = cards.get(1);
        LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1.0f);
        flashCard = new FlashCard(sindar_cards, english_cards, res);
        writingLayout = new RelativeLayout(this);
        buttonLayout = new LinearLayout(this);
        buttonLayout.setLayoutParams(params);
        nextCharButton = new Button(this);
        nextCharButton.setLayoutParams(params);
        nextCharButton.setTextSize(60);
        flashCard.transitionCard();
        nextCharButton.setText(flashCard.getCurrentSide(nextCharButton));
        nextCharButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v){
                if(flashCard.cardFaceUp){
                    drawView.clearView();
                }
                flashCard.transitionCard();
                nextCharButton.setText(flashCard.getCurrentSide(nextCharButton));
            }
        });
        nextCharButton.setVisibility(View.VISIBLE);
        drawView = new DrawView(this);
        writingLayout.addView(drawView);
        buttonLayout.addView(nextCharButton);
        writingLayout.addView(buttonLayout);
        setContentView(writingLayout);
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
