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
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View.OnTouchListener;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
        Vector<Vector<String>> cards = this.getCategories(categories, res);
        Vector<String> english_cards = cards.get(0);
        Vector<String> sindar_cards = cards.get(1);
        flashCard = new FlashCard(sindar_cards, english_cards, res);
        writingLayout = new RelativeLayout(this);
        LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1.0f);
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

    private Vector<String> addStringArrayToVector(Vector<String> v, String[] s)
    {
        for(int i = 0; i < s.length; i++)
        {
            v.add(s[i]);
        }
        return v;
    }

    private Vector<Vector<String>> getCategories(boolean[] categories, Resources resource)
    {
        Vector<String> english_cards = new Vector();
        Vector<String> sindar_cards= new Vector();

        if(categories[0])
        {
            english_cards = addStringArrayToVector(english_cards, resource.getStringArray(R.array.english_column_1));
            sindar_cards = addStringArrayToVector(sindar_cards, resource.getStringArray(R.array.sindar_column_1));
        } 

        if(categories[1])
        {
            english_cards = addStringArrayToVector(english_cards, resource.getStringArray(R.array.english_column_2));
            sindar_cards = addStringArrayToVector(sindar_cards, resource.getStringArray(R.array.sindar_column_2));
        } 
        
        if(categories[2])
        {
            english_cards = addStringArrayToVector(english_cards, resource.getStringArray(R.array.english_column_3));
            sindar_cards = addStringArrayToVector(sindar_cards, resource.getStringArray(R.array.sindar_column_3));
        } 
        
        if(categories[3])
        {
            english_cards = addStringArrayToVector(english_cards, resource.getStringArray(R.array.english_column_4));
            sindar_cards = addStringArrayToVector(sindar_cards, resource.getStringArray(R.array.sindar_column_4));
        } 
        
        if(categories[4])
        {
            english_cards = addStringArrayToVector(english_cards, resource.getStringArray(R.array.english_extra_characters));
            sindar_cards = addStringArrayToVector(sindar_cards, resource.getStringArray(R.array.sindar_extra_characters));
        } 
        
        if(categories[5])
        {
            english_cards = addStringArrayToVector(english_cards, resource.getStringArray(R.array.english_tehtar));
            sindar_cards = addStringArrayToVector(sindar_cards, resource.getStringArray(R.array.sindar_tehtar));
        } 
        Vector<Vector<String>> returnValues = new Vector();
        returnValues.add(english_cards);
        returnValues.add(sindar_cards);
        return returnValues; 
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
