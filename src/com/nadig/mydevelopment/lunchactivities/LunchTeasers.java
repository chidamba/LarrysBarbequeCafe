package com.nadig.mydevelopment.lunchactivities;

import java.math.BigDecimal;
import com.nadig.mydevelopment.MenuActivity;
import com.nadig.mydevelopment.MenuItem;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class LunchTeasers extends Fragment implements OnClickListener 
{
	public static String lunchTeasersOrder="";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View myView=inflater.inflate(R.layout.activity_lunchteasers,container,false);
		
		myView.findViewById(R.id.textView2).setOnClickListener(this);
		myView.findViewById(R.id.textView3).setOnClickListener(this);
		myView.findViewById(R.id.textView4).setOnClickListener(this);
		myView.findViewById(R.id.textView5).setOnClickListener(this);

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
				TextView tv=(TextView)arg0;
				lunchTeasersOrder+="\nLunch Teaser: "+tv.getText();
				Log.d("LunchTeasers","Lunch Teaser: "+lunchTeasersOrder);
				String[] temp=tv.getText().toString().split("[$]");
				Log.d("lunch teasers","Price is: "+temp[1]);
				MenuActivity.orderedItems.push(new MenuItem(temp[0],new BigDecimal(temp[1])));
				
				Context context = getActivity().getApplicationContext();
				CharSequence text = "Added To Order";
				int duration = Toast.LENGTH_SHORT;
				Log.d("lunchteaser","Length of the toast: "+duration);
				Toast toast = Toast.makeText(context, text, duration);
				toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 15);
				toast.show();
				//MenuActivity.grossTotal+=Float.parseFloat(temp[1]);
				break;
		}

	}

}
