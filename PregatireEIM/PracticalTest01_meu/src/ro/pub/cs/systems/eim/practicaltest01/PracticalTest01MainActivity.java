package ro.pub.cs.systems.eim.practicaltest01;

import ro.pub.cs.systems.eim.practicaltest01service.Constants;
import ro.pub.cs.systems.eim.practicaltest01service.PracticalTest01Service;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PracticalTest01MainActivity extends Activity {

	Integer val1, val2;
	Button btn1;
	Button btn2;
	Button second;
	private int serviceStatus = Constants.SERVICE_STOPPED;
	
	private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
	private class MessageBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d("[Message]", intent.getStringExtra("message"));
		}
	}
	
	private IntentFilter intentFilter = new IntentFilter();	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_practical_test01_main);
		
		btn1 = (Button)findViewById(R.id.button1);
		btn2 = (Button)findViewById(R.id.button2);
		
		final TextView text1 = (TextView)findViewById(R.id.first_text);
		final TextView text2 = (TextView)findViewById(R.id.secondText);
		text1.setText("0");
		text2.setText("0");
		
		btn1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int val1 = Integer.parseInt(text1.getText().toString());
				val1++;
				text1.setText(String.valueOf(val1));
				int val2 = Integer.parseInt(text2.getText().toString());
				
				if(val1 + val2 > Constants.NUMBER_OF_CLICKS_THRESHOLD && serviceStatus == Constants.SERVICE_STOPPED)
				{
					Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
					intent.putExtra("VAL1", val1);
					intent.putExtra("VAL2", val2);
					getApplicationContext().startService(intent);
					serviceStatus = Constants.SERVICE_STARTED;					
				}
			}
			
		});
		
		btn2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int val2 = Integer.parseInt(text2.getText().toString());
				val2++;
				text2.setText(String.valueOf(val2));
			}
			
		});		
		
		second = (Button)findViewById(R.id.second);
		second.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int val1 = Integer.parseInt(text1.getText().toString());
				int val2 = Integer.parseInt(text2.getText().toString());
				
				Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
				intent.putExtra("CLICKS", val1+val2);
				startActivityForResult(intent, 1);

			}
			
		});
		
		for (int index = 0; index < Constants.actionTypes.length; index++) {
			      intentFilter.addAction(Constants.actionTypes[index]);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
	    if (requestCode == 1) {
	      Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
	    }
	  }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.practical_test01_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		TextView text1 = (TextView)findViewById(R.id.first_text);
		TextView text2 = (TextView)findViewById(R.id.secondText);
		savedInstanceState.putString("VAL1", text1.getText().toString());
		savedInstanceState.putString("VAL2", text2.getText().toString());
	}
	
	protected void onRestoreInstanceState(Bundle restoredInstanceState)
	{
		TextView text1 = (TextView)findViewById(R.id.first_text);
		TextView text2 = (TextView)findViewById(R.id.secondText);
		text1.setText(restoredInstanceState.getString("VAL1"));
		text2.setText(restoredInstanceState.getString("VAL2"));
	}
	
	@Override
	protected void onDestroy() {
	  Intent intent = new Intent(this, PracticalTest01Service.class);
	  stopService(intent);
	  super.onDestroy();
	}
	
	  @Override
	  protected void onResume() {
	    super.onResume();
	    registerReceiver(messageBroadcastReceiver, intentFilter);
	  }
	  
	  
	  @Override
	  protected void onPause() {
	    unregisterReceiver(messageBroadcastReceiver);
	    super.onPause();
	  }
	
}
