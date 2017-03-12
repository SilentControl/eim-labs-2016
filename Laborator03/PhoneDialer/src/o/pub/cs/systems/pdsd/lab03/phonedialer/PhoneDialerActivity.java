package o.pub.cs.systems.pdsd.lab03.phonedialer;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class PhoneDialerActivity extends Activity {

	static int buttonids[] = {R.id.Button0, R.id.Button01, R.id.Button02, R.id.Button03,
		R.id.Button04, R.id.Button05, R.id.Button06, R.id.Button07, R.id.Button08,
		R.id.Button09, R.id.Button10, R.id.Button11, R.id.imageButton1, R.id.imageButton2, R.id.imageButton3};
	
	public StringBuffer text;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_dialer);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        text = new StringBuffer("");
        
        for(int i = 0; i < 10; i++)
        {
        	final Button btn = (Button)findViewById(buttonids[i]);
        	btn.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					text.append(btn.getText().toString());
					EditText txt1 = (EditText)findViewById(R.id.editText1);
					txt1.setText(text);
					System.out.println(text);
					Log.d("alfa", text.toString());
				}
        	});
        }
        
        final Button star = (Button)findViewById(buttonids[10]);
    	star.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				text.append("*");
				EditText txt1 = (EditText)findViewById(R.id.editText1);
				txt1.setText(text);				
			}
    	});       
        
        final Button hash = (Button)findViewById(buttonids[11]);
    	hash.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				text.append("#");
				EditText txt1 = (EditText)findViewById(R.id.editText1);
				txt1.setText(text);				
			}
    	});
    	
    	final ImageButton backspace = (ImageButton)findViewById(R.id.imageButton1);
    	backspace.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(text.length() > 0)
				{
					text.setLength(text.length() - 1);
					EditText txt1 = (EditText)findViewById(R.id.editText1);
					txt1.setText(text);					
				}
			}
    	});
    	
    	final ImageButton call = (ImageButton)findViewById(R.id.imageButton2);
    	call.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_CALL);
				intent.setData(Uri.parse("tel:"+text));
				startActivity(intent);
			}
    	});
    	
    	final ImageButton end = (ImageButton)findViewById(R.id.imageButton3);
    	end.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
    	});    	
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.phone_dialer, menu);
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
