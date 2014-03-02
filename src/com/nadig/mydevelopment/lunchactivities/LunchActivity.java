package com.nadig.mydevelopment.lunchactivities;

import com.nadig.mydevelopment.DressingDialog;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class LunchActivity extends FragmentActivity implements OnClickListener, DressingDialog.NoticeDialogListener
{
	
	public static int dressingNumber;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lunch);

		findViewById(R.id.button3).setOnClickListener(this);

		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		LunchTeasers dinnerTeasers=new LunchTeasers();
		transaction.add(R.id.fragment_placeholder, dinnerTeasers);
		transaction.commit();

	}

	public void onSelectFragment(View view)
	{
		Fragment newFragment;

		if(view ==  findViewById(R.id.button1))
		{

			newFragment=new LunchTeasers();

		}
		else if(view == findViewById(R.id.button2))
		{
			newFragment=new LunchGrazing();
		}
		else
		{
			newFragment=new LunchTeasers();

		}

		FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();

		transaction.replace(R.id.fragment_placeholder,newFragment);
		transaction.addToBackStack(null);
		transaction.commit();

	}

	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		Intent intent=getIntent();

		switch(v.getId())
		{
		case R.id.button3:
			if(LunchTeasers.lunchTeasersOrder.equals("") && LunchGrazing.lunchGrazingOrder.equals(""))
			{
				setResult(Activity.RESULT_CANCELED,intent);
			}

			else 
			{
				if(!LunchTeasers.lunchTeasersOrder.equals(""))
				{
					intent.putExtra("lunchteasers",LunchTeasers.lunchTeasersOrder);
					setResult(Activity.RESULT_OK, intent);
					LunchTeasers.lunchTeasersOrder="";
				}
				if(!LunchGrazing.lunchGrazingOrder.equals(""))
				{ 	
					intent.putExtra("lunchgrazing",LunchGrazing.lunchGrazingOrder);
					setResult(Activity.RESULT_OK, intent);
					LunchGrazing.lunchGrazingOrder="";
				}
			}
			finish();
			break;
			
			
		}

	}

	@Override
	public void onDialogPositiveClick(int which) {
		// TODO Auto-generated method stub
		dressingNumber=which;
		Log.d("LunchActivity","this is the dressing number you've chosen: "+which);
		
	}

}
