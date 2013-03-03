package com.example.sindarstudyguide;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.CheckBox;
import android.util.Log;
import com.example.sindarstudyguide.DisplayMessageActivity;

public class MainActivity extends FragmentActivity
{

    public final static String CATEGORY_STATES = "com.example.sindarstudyguide.CATEGORY_STATES";
    public static final String logtag = "DAVEDEBUG";

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        this.findViewById(R.id.flash).setVisibility(View.GONE);
    }

public void beginWritingPractice(View view){
        boolean[] categories = this.getCategories();
        boolean atLeastOneCategory = false;
        for(int i = 0; i < categories.length; i++)
        {
            atLeastOneCategory = categories[i] || atLeastOneCategory;
        }
        if(!atLeastOneCategory)
        {
            NoCategoryDialogFragment msg = new NoCategoryDialogFragment();
            msg.show(getSupportFragmentManager(), "some tag");
            return;
        }
        Intent intent = new Intent(this, WritingActivity.class);
        intent.putExtra(this.CATEGORY_STATES, categories);
        startActivity(intent);
    }
    

public void beginPhenomeFlashCards(View view)
    {
        boolean[] categories = this.getCategories();
        boolean atLeastOneCategory = false;
        for(int i = 0; i < categories.length; i++)
        {
            atLeastOneCategory = categories[i] || atLeastOneCategory;
        }
        if(!atLeastOneCategory)
        {
            NoCategoryDialogFragment msg = new NoCategoryDialogFragment();
            msg.show(getSupportFragmentManager(), "some tag");
            return;
        }
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        intent.putExtra(this.CATEGORY_STATES, categories);
        startActivity(intent);
    }

    private boolean[] getCategories()
    {
        boolean[] categories = new boolean[6];

        CheckBox column_1 = (CheckBox) this.findViewById(R.id.column_1);
        if(column_1.isChecked())
            categories[0] = true;

        if(((CheckBox)this.findViewById(R.id.column_2)).isChecked())
            categories[1] = true;
        
        if(((CheckBox)this.findViewById(R.id.column_3)).isChecked())
            categories[2] = true;
        
        if(((CheckBox)this.findViewById(R.id.column_4)).isChecked())
            categories[3] = true;
        
        if(((CheckBox)this.findViewById(R.id.extra_characters)).isChecked())
            categories[4] = true;
        
        if(((CheckBox)this.findViewById(R.id.tehtar)).isChecked())
            categories[5] = true;

        return categories; 
    }
}

class NoCategoryDialogFragment extends DialogFragment
{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.no_categories_selected);
        return builder.create();
    }
}
