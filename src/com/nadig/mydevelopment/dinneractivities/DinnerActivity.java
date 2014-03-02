package com.nadig.mydevelopment.dinneractivities;

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

public class DinnerActivity extends FragmentActivity implements OnClickListener, DressingDialog.NoticeDialogListener
{
	
	public static int dressingNumber;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dinner);

		findViewById(R.id.button3).setOnClickListener(this);

		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		DinnerTeasers dinnerTeasers=new DinnerTeasers();
		transaction.add(R.id.fragment_placeholder, dinnerTeasers);
		transaction.commit();

	}

	public void onSelectFragment(View view)
	{
		Fragment newFragment;

		if(view ==  findViewById(R.id.button1))
		{

			newFragment=new DinnerTeasers();

		}
		else if(view == findViewById(R.id.button2))
		{
			newFragment=new DinnerGrazing();
		}
		else
		{
			newFragment=new DinnerTeasers();

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
			if(DinnerTeasers.dinnerTeasersOrder.equals("") && DinnerGrazing.dinnerGrazingOrder.equals(""))
			{
				setResult(Activity.RESULT_CANCELED,intent);
			}

			else 
			{
				if(!DinnerTeasers.dinnerTeasersOrder.equals(""))
				{
					intent.putExtra("dinnerteasers",DinnerTeasers.dinnerTeasersOrder);
					setResult(Activity.RESULT_OK, intent);
					DinnerTeasers.dinnerTeasersOrder="";
				}
				if(!DinnerGrazing.dinnerGrazingOrder.equals(""))
				{ 	
					intent.putExtra("dinnergrazing",DinnerGrazing.dinnerGrazingOrder);
					setResult(Activity.RESULT_OK, intent);
					DinnerGrazing.dinnerGrazingOrder="";
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
		Log.d("DinnerActivity","this is the dressing number you've chosen: "+which);
		
	}

}
