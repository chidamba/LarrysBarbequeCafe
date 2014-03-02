package com.nadig.mydevelopment.dinneractivities;

import java.math.BigDecimal;

import com.nadig.mydevelopment.MenuActivity;
import com.nadig.mydevelopment.MenuItem;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class DinnerTeasers extends Fragment implements OnClickListener 
{
	public static String dinnerTeasersOrder="";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View myView=inflater.inflate(R.layout.activity_dinnerteasers,container,false);
		
		myView.findViewById(R.id.textView2).setOnClickListener(this);
		myView.findViewById(R.id.textView3).setOnClickListener(this);
		myView.findViewById(R.id.textView4).setOnClickListener(this);
		myView.findViewById(R.id.textView5).setOnClickListener(this);
		myView.findViewById(R.id.textView6).setOnClickListener(this);
		myView.findViewById(R.id.textView7).setOnClickListener(this);
		myView.findViewById(R.id.textView8).setOnClickListener(this);

		return myView;

	}

	@Override
	public void onClick(View arg0)
	{
		// TODO Auto-generated method stub
		switch(arg0.getId())
		{
			case R.id.textView2:
			case R.id.textView3:
			case R.id.textView4:
			case R.id.textView5:
			case R.id.textView6:
			case R.id.textView7:				
			case R.id.textView8:
				TextView tv=(TextView)arg0;
				dinnerTeasersOrder+="\nDinner Teaser: "+tv.getText();
				Log.d("DinnerTeasers","Dinner Teaser: "+dinnerTeasersOrder);
				String[] temp=tv.getText().toString().split("[$]");
				Log.d("dinner teasers","Price is: "+temp[1]);
				MenuActivity.orderedItems.push(new MenuItem(temp[0],new BigDecimal(temp[1])));
				
				Context context = getActivity().getApplicationContext();
				CharSequence text = "Added To Order";
				int duration = Toast.LENGTH_SHORT;
				Log.d("dinnerteaser","Length of the toast: "+duration);
				Toast toast = Toast.makeText(context, text, duration);
				toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 15);
				toast.show();
				//MenuActivity.grossTotal+=Float.parseFloat(temp[1]);
				break;
			

		}

	}

}
