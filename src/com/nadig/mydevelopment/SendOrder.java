package com.nadig.mydevelopment;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;

public class SendOrder
{
	public Context _context;

	public SendOrder(Context context)
	{
		_context=context;
		new Task().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}

	
	public class Task extends AsyncTask<Void, String, Void>
	{

		@Override
		protected Void doInBackground(Void... params) 
		{
			// TODO Auto-generated method stub

			java.util.Date date= new java.util.Date();
			String to = "chidu.nadig1@gmail.com";
			String subject = "order: "+new Timestamp(date.getTime());
			
			String sname=FinalActivity.name.getText().toString();
			String sphone=FinalActivity.phone.getText().toString();
			String message=subject
					+"\nName: "+sname
					+"\nPhone: "+sphone
					+"\nDate of Pick Up: "+FinalActivity.dateOfPickUp.getText()
					+"\nTime of Pick Up: "+FinalActivity.timeOfPickUp.getText()
					+"\n"+FinalActivity.order
					+"\nExtra Instructions: "+FinalActivity.instructions.getText();

			Intent email = new Intent(Intent.ACTION_SEND);
			email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
			//email.putExtra(Intent.EXTRA_CC, new String[]{ to});
			//email.putExtra(Intent.EXTRA_BCC, new String[]{to});
			email.putExtra(Intent.EXTRA_SUBJECT, subject);
			email.putExtra(Intent.EXTRA_TEXT, message);

			//need this to prompts email client only
			email.setType("message/rfc822");

			_context.startActivity(Intent.createChooser(email, "Choose an Email client :"));
			try
			{
				URL yahoo = new URL("http://192.168.1.103:8080/Echo.jsp?");
				URLConnection yc = yahoo.openConnection();
				BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
				

				
				in.close();
			}
			catch(Exception e)
			{
				System.out.println("not happening"+e);
			}
			FinalActivity.dateOfPickUp.setText("");
			FinalActivity.timeOfPickUp.setText("");
			FinalActivity.name.setText("");
			FinalActivity.phone.setText("");
			FinalActivity.instructions.setText("");
			return null;
		}
		
	}
}
