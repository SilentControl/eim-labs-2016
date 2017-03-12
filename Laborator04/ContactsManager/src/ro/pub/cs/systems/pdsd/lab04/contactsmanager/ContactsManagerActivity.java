package ro.pub.cs.systems.pdsd.lab04.contactsmanager;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;


public class ContactsManagerActivity extends Activity {

    Button show;
    Button save;
    Button cancel;
    LinearLayout hidden;
    boolean showstate;
    EditText name, phone, email, address, job, company;

    class ButtonListener implements OnClickListener
    {

    	@Override
    	public void onClick(View view)
    	{
    		switch(view.getId())
    		{
    			case R.id.button1:
    				if(show != null)
    				{
    					if(showstate == true)
    					{
    						hidden.setVisibility(View.GONE);
    						StringBuffer buf = new StringBuffer("Show Additional Fields");
    						show.setText(buf);
    					}
    					
    					else
    					{
    						hidden.setVisibility(View.VISIBLE);
    						StringBuffer buf = new StringBuffer("Hide Additional Fields");
    						show.setText(buf);    						
    					}
    					
    					showstate = !showstate;
    				}
    				break;
    			case R.id.button2:
    				Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
    				intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
    				if (name.getText() != null) {
    				  intent.putExtra(ContactsContract.Intents.Insert.NAME, name.getText().toString());
    				}
    				if (phone.getText() != null) {
    				  intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone.getText().toString());
    				}
    				if (email.getText() != null) {
    				  intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email.getText().toString());
    				}
    				if (address.getText() != null) {
    				  intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address.getText().toString());
    				}
    				if (job.getText() != null) {
    				  intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, job.getText().toString());
    				}
    				if (company.getText() != null) {
    				  intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company.getText().toString());
    				}
    				
    				startActivityForResult(intent, 22);    				
    				break;
    			case R.id.button3:
    				setResult(Activity.RESULT_CANCELED, new Intent());
    				finish();
    				break;
    			default:
    				break;
    		}
    	}
    	
    }    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_manager);
        
        show = (Button)findViewById(R.id.button1);
        save = (Button)findViewById(R.id.button2);
        cancel = (Button)findViewById(R.id.button3);
        hidden = (LinearLayout)findViewById(R.id.layout_down);
        showstate = true;
        
        name = (EditText)findViewById(R.id.editText1);
        phone = (EditText)findViewById(R.id.editText2);
        email = (EditText)findViewById(R.id.editText3);
        address = (EditText)findViewById(R.id.editText4);
        job = (EditText)findViewById(R.id.editText5);
        company = (EditText)findViewById(R.id.editText6);
        
        
        ButtonListener listen = new ButtonListener();
        show.setOnClickListener(listen);
        save.setOnClickListener(listen);
        cancel.setOnClickListener(listen);
        
        Intent intent1 = getIntent();
        if (intent1 != null)
        {
        	String phone1 = intent1.getStringExtra("ro.pub.cs.systems.pdsd.lab04.contactsmanager.PHONE_NUMBER_KEY");
        	  if (phone1 != null) {
        		    phone.setText(phone1);
        	  }
        }
        
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contacts_manager, menu);
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
    
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
    	switch(requestCode) {
    	  case 22:
    	    setResult(resultCode, new Intent());
    	    finish();
    	    break;
    	  }
    	}    
}
