package ro.pub.cs.systems.eim.practicaltest01var04;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.Random;

import ro.pub.cs.systems.eim.service.PracticalTest01Var04Service;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PracticalTest01Var02PlayActivity extends Activity {

	TextView guess_text = null;
	TextView score = null;
	Button generate = null;
	Button check = null;
	Button back = null;
	Random rand = new Random();
	int good_val = 0;
	
	private IntentFilter intentFilter = new IntentFilter();
	
	private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
	private class MessageBroadcastReceiver extends BroadcastReceiver {
	  @Override
	  public void onReceive(Context context, Intent intent) {
	    Log.d("[Message]", intent.getStringExtra("message"));
	  }
	}
	
	private ButtonClickListener buttonClickListener = new ButtonClickListener();
	  private class ButtonClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch(view.getId()){
				case R.id.generate:
					guess_text.setText(String.valueOf(rand.nextInt(10)));
					break;
				case R.id.back:
					setResult(1, null);
					finish();
					break;
				case R.id.check:
					if(Integer.parseInt(guess_text.getText().toString()) == good_val)
					{
						int crt_score = Integer.parseInt(score.getText().toString());
						crt_score++;
						score.setText(String.valueOf(crt_score));
						
					}
					break;
			}
			
		}
	  }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_practical_test01_var02_play);
		
		guess_text = (TextView)findViewById(R.id.guess_text);
		score = (TextView)findViewById(R.id.score);
		generate = (Button)findViewById(R.id.generate);
		check = (Button)findViewById(R.id.check);
		back = (Button)findViewById(R.id.back);
		
		Intent intent = getIntent();
	    if (intent != null && intent.getExtras().containsKey("good_val")) {
	      good_val = intent.getIntExtra("good_val", 0);
	    }
		
		int def = 0;
		score.setText(String.valueOf(def));
		guess_text.setText(String.valueOf(-1));
		
		generate.setOnClickListener(buttonClickListener);
		check.setOnClickListener(buttonClickListener);
		back.setOnClickListener(buttonClickListener);
		
		intentFilter.addAction("gen");
		
		Intent intent1 = new Intent(getApplicationContext(), PracticalTest01Var04Service.class);
		getApplicationContext().startService(intent1);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.practical_test01_var02_play, menu);
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
	    savedInstanceState.putString("score", score.getText().toString());
	    savedInstanceState.putString("guess", guess_text.getText().toString());
	  }
	
	 protected void onRestoreInstanceState(Bundle savedInstanceState) {
		    if (savedInstanceState.containsKey("score")) {
		      score.setText(savedInstanceState.getString("score"));
		    }
		    
		    if (savedInstanceState.containsKey("guess")) {
		     guess_text.setText(savedInstanceState.getString("guess"));

		  }
	 }
	 
	 @Override
	 protected void onDestroy() {
	   Intent intent = new Intent(this, PracticalTest01Var04Service.class);
	   stopService(intent);
	   super.onDestroy();
	 }
	 
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
