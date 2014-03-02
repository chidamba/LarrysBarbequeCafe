package com.nadig.mydevelopment;

import java.math.BigDecimal;
import java.util.Calendar;

import org.json.JSONException;

import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;

public class FinalActivity extends Activity implements OnClickListener, OnFocusChangeListener
{
	public static EditText name;
	public static EditText timeOfPickUp;
	public static EditText phone;
	public static String order;
	public static EditText dateOfPickUp;
	public static EditText instructions;

	private static final String CONFIG_ENVIRONMENT = PaymentActivity.ENVIRONMENT_NO_NETWORK;
	private static final String CONFIG_CLIENT_ID = "AaGrjxBap0D4mt-uaBBB2qe4xaYcIHfUSY0SFdcrFBpWa7DdIOms45tMrHxQ";
	private static final String CONFIG_RECEIVER_EMAIL = "chidu.nadig1@gmail.com"; 



	//public static final int Date_dialog_id = 1;
	// date and time
	private int mYear;
	private int mMonth;
	private int mDay;

	private int mHour;
	private int mMinute;



	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_final);

		name=(EditText)findViewById(R.id.editText1);
		phone=(EditText)findViewById(R.id.editText2);		
		dateOfPickUp=(EditText)findViewById(R.id.editText3);
		timeOfPickUp=(EditText)findViewById(R.id.editText4);
		instructions=(EditText)findViewById(R.id.editText5);
		order=getIntent().getExtras().getString("order");

		//dateOfPickUp.setOnClickListener(this);
		dateOfPickUp.setOnFocusChangeListener(this);

		//timeOfPickUp.setOnClickListener(this);
		timeOfPickUp.setOnFocusChangeListener(this);

		findViewById(R.id.button1).setOnClickListener(this);
		findViewById(R.id.button2).setOnClickListener(this);
		findViewById(R.id.button3).setOnClickListener(this);







		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		updateDate();

		mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);
		updateTime();


		Intent intent = new Intent(this, PayPalService.class);
		intent.putExtra(PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT, CONFIG_ENVIRONMENT);
		intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, "AaGrjxBap0D4mt-uaBBB2qe4xaYcIHfUSY0SFdcrFBpWa7DdIOms45tMrHxQ");
		intent.putExtra(PaymentActivity.EXTRA_RECEIVER_EMAIL, CONFIG_RECEIVER_EMAIL);

		startService(intent);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.button1:
			finish();
			System.exit(0);
			break;


			//place order	
		case R.id.button2:

			PayPalPayment thingToBuy = new PayPalPayment(new BigDecimal(MenuActivity.grossTotal), "USD", "Your Order costs: ");
			Intent intent = new Intent(this, PaymentActivity.class);

			intent.putExtra(PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT, CONFIG_ENVIRONMENT);
			intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, CONFIG_CLIENT_ID);
			intent.putExtra(PaymentActivity.EXTRA_RECEIVER_EMAIL, CONFIG_RECEIVER_EMAIL);

			// It's important to repeat the clientId here so that the SDK has it if Android restarts your 
			// app midway through the payment UI flow.
			intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, CONFIG_CLIENT_ID);
			intent.putExtra(PaymentActivity.EXTRA_PAYER_ID, name.getText().toString()+phone.getText().toString());
			intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);

			startActivityForResult(intent, 0);
			break;

		case R.id.button3:
			intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			break;
		}

	}


	@Override
	@Deprecated
	protected void onPrepareDialog(int id, Dialog dialog) {
		// TODO Auto-generated method stub
		super.onPrepareDialog(id, dialog);

		((DatePickerDialog) dialog).updateDate(mYear, mMonth, mDay);

		((TimePickerDialog) dialog).updateTime(mHour, mMinute);

	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth)
		{
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			updateDate();
		}
	};

	private TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(android.widget.TimePicker view, int hourOfDay,
				int minute) {
			// TODO Auto-generated method stub
			mHour = hourOfDay;
			mMinute = minute;
			updateTime();

		}
	};

	@Override
	protected void onActivityResult (int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
			if (confirm != null) {
				try {
					Log.i("paymentExample", confirm.toJSONObject().toString(4));

					new SendOrder(this);

					// TODO: send 'confirm' to your server for verification.
					// see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
					// for more details.

				} catch (JSONException e) {
					Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
				}
			}
		}
		else if (resultCode == Activity.RESULT_CANCELED) {
			Log.i("paymentExample", "The user canceled.");
		}
		else if (resultCode == PaymentActivity.RESULT_PAYMENT_INVALID) {
			Log.i("paymentExample", "An invalid payment was submitted. Please see the docs.");
		}
	}

	private void updateDate()
	{
		dateOfPickUp.setText(new StringBuilder()
		// Month is 0 based so add 1
		.append(mMonth + 1).append("-").append(mDay).append("-").append(mYear));
	}
	private void updateTime()
	{
		// TODO Auto-generated method stub
		timeOfPickUp.setText(new StringBuilder().append(mHour).append(":").append(mMinute));
	}
	@Override
	public void onDestroy() {
		stopService(new Intent(this, PayPalService.class));
		super.onDestroy();
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus)
	{
		
		if(!hasFocus)
			return;
		
		DatePickerDialog DPD=null;
		TimePickerDialog TPD=null;
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		case R.id.editText3:
			if(hasFocus)
			DPD = new DatePickerDialog(FinalActivity.this, mDateSetListener, mYear, mMonth,mDay);
			DPD.show();
			break;

		case R.id.editText4:
			if(hasFocus)
			TPD = new TimePickerDialog(FinalActivity.this,mTimeSetListener, mHour, mMinute, false);
			TPD.show();
			break;
		}

	}

}
