package com.nadig.mydevelopment;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Stack;

import com.nadig.mydevelopment.lunchactivities.LunchActivity;
import com.nadig.mydevelopment.dinneractivities.DinnerActivity;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MenuActivity extends Activity implements OnClickListener
{
	private Button buttonLunch;
	private Button buttonDinner;
	public String order;
	private TextView tv;
	private TextView total;
		
	Handler h1;

	public static Stack<MenuItem> orderedItems=new Stack<MenuItem>();
	public static double grossTotal=0;





	public MenuActivity()
	{
		order="";
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		h1=new Handler();
		tv=(TextView) findViewById(R.id.textView2);
		total=(TextView)findViewById(R.id.textView3);
		
		findViewById(R.id.button1).setOnClickListener(this);
		findViewById(R.id.button2).setOnClickListener(this);
		findViewById(R.id.button3).setOnClickListener(this);
		findViewById(R.id.button4).setOnClickListener(this);
		findViewById(R.id.button5).setOnClickListener(this);
		findViewById(R.id.button6).setOnClickListener(this);
		
		h1.post(new Runnable() {
			public void run()
			{
				tv.setText(order);
				total.setText("Total: "+BigDecimal.valueOf(grossTotal));

			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		//System.out.println("MenuActivity: Order Received");
		//	Log.d("MenuActivity","MenuActivity: Order Received");



		//Lunch Order
		if (requestCode == 1)
		{
			//Log.d("MenuActivity","RequestCode=1 Lunch Order");
			if(resultCode == RESULT_OK)
			{		
				//System.out.println("MenuActivity: RESULT_OK");

				/*if(data.getStringExtra("lunchteasers")!=null)
					order+=data.getStringExtra("lunchteasers");

				if(data.getStringExtra("lunchgrazing")!=null)
					order+=data.getStringExtra("lunchgrazing");*/

				//Log.d("MenuActivity","Order: "+order);
				
				order=printOrder(orderedItems);

				h1.post(new Runnable() {
					public void run()
					{
						tv.setText(order);
						total.setText("Total: $"+grossTotal);

					}
				});
			}
			else if (resultCode == RESULT_CANCELED)
			{
				//Log.d("MenuActivity","Result Cancelled");

			}

		}
		//Dinner Order
		else if(requestCode==2)
		{
			//Log.d("MenuActivity","RequestCode=2 Dinner Order");
			if(resultCode == RESULT_OK)
			{		
				//System.out.println("MenuActivity: RESULT_OK");

				/*if(data.getStringExtra("dinnerteasers")!=null)
					order+=data.getStringExtra("dinnerteasers");
				if(data.getStringExtra("dinnergrazing")!=null)
					order+=data.getStringExtra("dinnergrazing");*/
				
				order+=printOrder(orderedItems);

				Log.d("MenuActivity","Order: "+order);
				h1.post(new Runnable() {
					public void run()
					{
						tv.setText(order);
						total.setText("Total: "+BigDecimal.valueOf(grossTotal));

					}
				});
			}
			else if (resultCode == RESULT_CANCELED)
			{
				//Log.d("MenuActivity","Result Cancelled");

			}

		}
	}

	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.button1:
			Intent intent = new Intent(MenuActivity.this, LunchActivity.class);
			startActivityForResult(intent,1);
			break;

		case R.id.button2:
			intent = new Intent(MenuActivity.this, DinnerActivity.class);
			startActivityForResult(intent,2);
			break;

		case R.id.button3:
			finish();
			break;

			//Remove last item

		case R.id.button4:
			if(!orderedItems.empty())
				orderedItems.pop();
			order=printOrder(orderedItems);
			h1.post(new Runnable() {
				public void run()
				{
					tv.setText(order);
					total.setText("Total: $"+BigDecimal.valueOf(grossTotal));

				}
			});
			break;


			//Reset Order
		case R.id.button5:
			orderedItems.clear();
			order=printOrder(orderedItems);
			h1.post(new Runnable() {
				public void run()
				{
					tv.setText(order);
					total.setText("Total: $"+BigDecimal.valueOf(grossTotal));

				}
			});
			break;

			//Proceed to checkout

		case R.id.button6:
			intent = new Intent(MenuActivity.this, FinalActivity.class);
			intent.putExtra("order",order);
			startActivity(intent);
			break;
		}
	}
	public String printOrder(Stack<MenuItem> a)
	{
		String t="";
		int j=1;
		grossTotal=0;
		
		if(a.empty())
			return "";
		
		for(MenuItem i : a)
		{
			t=t+j+" : "+i.item+"\n";
			j++;
			grossTotal+=i.price.doubleValue();
		}
		return t;
	}
}
