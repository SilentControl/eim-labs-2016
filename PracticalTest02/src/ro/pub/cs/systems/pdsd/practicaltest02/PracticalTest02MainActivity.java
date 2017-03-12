package ro.pub.cs.systems.pdsd.practicaltest02;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class PracticalTest02MainActivity extends Activity {

	
	// Server widgets
	
	// Client widgets
	private EditText     clientAddressEditText    = null;
	private EditText     clientPortEditText       = null;
	private EditText     key           = null;
	private EditText     value           = null;
	
	private Button      put            = null;
	private Button       get           = null;
	
	private ServerThread serverThread             = null;
	private ClientThread clientThread             = null;
	
	
	private GetListener getListener = new GetListener();
	private class GetListener implements Button.OnClickListener {
		
		@Override
		public void onClick(View view) {
			String clientAddress = clientAddressEditText.getText().toString();
			String clientPort    = clientPortEditText.getText().toString();
			if (clientAddress == null || clientAddress.isEmpty() ||
				clientPort == null || clientPort.isEmpty()) {
				Toast.makeText(
					getApplicationContext(),
					"Client connection parameters should be filled!",
					Toast.LENGTH_SHORT
				).show();
				return;
			}
			
			if (serverThread == null || !serverThread.isAlive()) {
				Log.e(Constants.TAG, "[MAIN ACTIVITY] There is no server to connect to!");
				return;
			}
			
			String get_text = get.getText().toString();
			if (get_text == null || get_text.isEmpty()) {
				Toast.makeText(
					getApplicationContext(),
					"Parameters from client (city / information type) should be filled!",
					Toast.LENGTH_SHORT
				).show();
				return;
			}
			
			value.setText(Constants.EMPTY_STRING);
			
			clientThread = new ClientThread(
					clientAddress,
					Integer.parseInt(clientPort),
					get_text,
					new String("emptykey"),
					0,
					value);
			clientThread.start();
		}
	}

	private PutListener putListener = new PutListener();
	private class PutListener implements Button.OnClickListener {
		
		@Override
		public void onClick(View view) {
			String clientAddress = clientAddressEditText.getText().toString();
			String clientPort    = clientPortEditText.getText().toString();
			if (clientAddress == null || clientAddress.isEmpty() ||
				clientPort == null || clientPort.isEmpty()) {
				Toast.makeText(
					getApplicationContext(),
					"Client connection parameters should be filled!",
					Toast.LENGTH_SHORT
				).show();
				return;
			}
			
			if (serverThread == null || !serverThread.isAlive()) {
				Log.e(Constants.TAG, "[MAIN ACTIVITY] There is no server to connect to!");
				return;
			}
			
			String get_text = get.getText().toString();
			if (get_text == null || get_text.isEmpty()) {
				Toast.makeText(
					getApplicationContext(),
					"Parameters from client (city / information type) should be filled!",
					Toast.LENGTH_SHORT
				).show();
				return;
			}
			
			//value.setText(Constants.EMPTY_STRING);
			String val_text = value.getText().toString();
			
			clientThread = new ClientThread(
					clientAddress,
					Integer.parseInt(clientPort),
					get_text,
					val_text,
					1,
					value);
			clientThread.start();
		}
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_practical_test02_main);
		
		serverThread = new ServerThread(2016);
		if (serverThread.getServerSocket() != null) {
			serverThread.start();
		} else {
			Log.e(Constants.TAG, "[MAIN ACTIVITY] Could not creat server thread!");
		}
		
		
		
		clientAddressEditText = (EditText)findViewById(R.id.address);
		clientPortEditText = (EditText)findViewById(R.id.port);
		
		key = (EditText)findViewById(R.id.key);
		value = (EditText)findViewById(R.id.value);
		
		get = (Button)findViewById(R.id.get);
		put = (Button)findViewById(R.id.put);
		
		get.setOnClickListener(getListener);
		put.setOnClickListener(putListener);
	}
	
	@Override
	protected void onDestroy() {
		if (serverThread != null) {
			serverThread.stopThread();
		}
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.practical_test02_main, menu);
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
}
