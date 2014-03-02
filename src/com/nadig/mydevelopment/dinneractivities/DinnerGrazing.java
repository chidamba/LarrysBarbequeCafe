package com.nadig.mydevelopment.dinneractivities;



import java.math.BigDecimal;
import com.nadig.mydevelopment.MenuActivity;
import com.nadig.mydevelopment.MenuItem;
import com.nadig.mydevelopment.DressingDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class DinnerGrazing extends Fragment implements OnClickListener
{
	public static String dinnerGrazingOrder="";

	
	
	//public Dialog dialog=null;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) 
	{
		View myView=inflater.inflate(R.layout.activity_dinnergrazing,container,false);

		myView.findViewById(R.id.textView2).setOnClickListener(this);
		myView.findViewById(R.id.textView3).setOnClickListener(this);
		myView.findViewById(R.id.textView4).setOnClickListener(this);
		myView.findViewById(R.id.textView5).setOnClickListener(this);



		/*
		dialog = new Dialog(getActivity());
		dialog.setContentView(R.layout.dialog_dressing);
		dialog.setTitle("Select Dressing");
	

		Button btnDone = (Button)dialog.findViewById(R.id.button1);
		btnDone.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v)
			{
				Log.d("OnClickListener","Done button has been clicked");
				RadioGroup dressingChoices=(RadioGroup)dialog.findViewById(R.id.radioGroup1);
				RadioButton dressing=(RadioButton)dialog.findViewById(dressingChoices.getCheckedRadioButtonId());
				String s=dressing.getText()+"";
				Log.d("OnClickListener","This is the dressing which has been chosen: "+s);
				dialog.dismiss();
				Dressing=s;
			}
		});	*/

		return myView;
		//return inflater.inflate(R.layout.activity_dinnergrazing, container, false);

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
			DressingDialog testDialog=new DressingDialog();			
			android.app.FragmentManager fm = getActivity().getFragmentManager();				        
	        testDialog.setRetainInstance(true);
	        testDialog.show(fm, "fragment_name");
			Log.d("DinnerGrazing","Main Item: "+tv.getText());
			Log.d("DinnerGrazing","Dressing is: "+DinnerActivity.dressingNumber);
			dinnerGrazingOrder+="\nDinner Grazing: "+tv.getText()+"\nDressing: "+DinnerActivity.dressingNumber;
			String[] temp=tv.getText().toString().split("[$]");
			Log.d("lunch grazing","Price is: "+temp[1]);
			MenuActivity.orderedItems.push(new MenuItem(temp[0],new BigDecimal(temp[1])));
			
			
			Context context = getActivity().getApplicationContext();
			CharSequence text = "Added To Order";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 15);
			toast.show();
			//MenuActivity.grossTotal+=Float.parseFloat(temp[1]);
			break;
		}
	}
}