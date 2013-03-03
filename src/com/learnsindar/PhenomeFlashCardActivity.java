package com.learnsindar;

import java.util.Random;
import java.util.Vector;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Button;
import android.widget.CheckBox;
import com.learnsindar.MainActivity;


class FlashCard
{

    private Vector<String> sindarSide;
    private Vector<String> englishSide;
    private boolean cardFaceUp = true;
    private int currentSide;
    private Random r;
    private Typeface sindarTypeface;
    private Typeface englishTypeface;

    public FlashCard(Vector<String> sindarSide, Vector<String> englishSide, Resources resources)
    {
        this.sindarSide = sindarSide;
        this.englishSide = englishSide;
        this.r = new Random();
        this.updateCard();
        this.sindarTypeface = Typeface.createFromAsset(resources.getAssets(), "fonts/TengwarSindarin.ttf");
        this.englishTypeface = Typeface.SERIF;
    }

    public void updateCard()
    {
        int new_index = r.nextInt(this.sindarSide.size());
        this.currentSide = new_index;
    }

    public void transitionCard() 
    {
        if(!cardFaceUp)
        {
            this.updateCard();
        }
        cardFaceUp = !cardFaceUp;
    }

    public String getCurrentSide(Button drawButton)
    {
        String value = "ERROR";
        if(cardFaceUp)
        {
            drawButton.setTypeface(this.sindarTypeface);
            value = sindarSide.get(this.currentSide);
        }
        else
        {
            drawButton.setTypeface(this.englishTypeface);
            value = englishSide.get(this.currentSide);
        }
        return value;
    }
}

public class PhenomeFlashCardActivity extends Activity implements OnClickListener{

    public boolean cardFaceUp = true;
    public FlashCard flashCard;
    public Button flashCardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Intent intent = getIntent();

        boolean[] categories = intent.getBooleanArrayExtra(MainActivity.CATEGORY_STATES);

        this.findViewById(R.id.begin_phenome_flash_cards).setVisibility(View.GONE);
        this.findViewById(R.id.column_1).setVisibility(View.GONE);
        this.findViewById(R.id.column_2).setVisibility(View.GONE);
        this.findViewById(R.id.column_3).setVisibility(View.GONE);
        this.findViewById(R.id.column_4).setVisibility(View.GONE);
        this.findViewById(R.id.extra_characters).setVisibility(View.GONE);
        this.findViewById(R.id.tehtar).setVisibility(View.GONE);

        Resources res = getResources();
        Vector<Vector<String>> cards = this.getCategories(categories, res);
        Vector<String> english_cards = cards.get(0);
        Vector<String> sindar_cards = cards.get(1);
        flashCardButton = (Button) this.findViewById(R.id.flash); 
        flashCardButton.setVisibility(View.VISIBLE);
        flashCard = new FlashCard(sindar_cards, english_cards, res);
        this.flashCardButton.setOnClickListener(this);
        flashCardButton.setTextSize(60);
        flashCardButton.setText(flashCard.getCurrentSide(flashCardButton));
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

    @Override
    public void onClick(View v)
    {
        flashCard.transitionCard();
        flashCardButton.setText(flashCard.getCurrentSide(flashCardButton));
    }
}
