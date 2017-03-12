package ro.pub.cs.systems.eim.practicaltest01var04;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class PracticalTest01Var03ChooseNumber extends Activity {

	EditText insert = null;
	Button play = null;
	
	private ButtonClickListener buttonClickListener = new ButtonClickListener();
	private class ButtonClickListener implements OnClickListener {

		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch(view.getId())
			{
			case R.id.play:
				Intent intent = new Intent(getApplicationContext(), PracticalTest01Var02PlayActivity.class);
				try{
				  int good_val = Integer.parseInt(insert.getText().toString());
				  if(good_val >= 0 && good_val <= 9)
				  {
			          intent.putExtra("good_val", good_val);
			          startActivityForResult(intent, 1);
				  }
				}
				catch(Exception e)
				{
					
				}
			          break;				
			}
		}
	
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practical_test01_var03_choose_number);
        
        insert = (EditText)findViewById(R.id.insert);
        play = (Button)findViewById(R.id.play);
        play.setOnClickListener(buttonClickListener);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.practical_test01_var03_choose_number, menu);
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
    
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 1) {
          Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
      }
}
