package ro.pub.cs.systems.eim.practicaltest01service;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
 

public class PracticalTest01Service extends Service{

	private ProcessingThread processingThread = null;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		int first_num = intent.getIntExtra("VAL1", -1);
		int second_num = intent.getIntExtra("VAL2", 1);
		processingThread = new ProcessingThread(this, first_num, second_num);
		processingThread.start();
		return Service.START_REDELIVER_INTENT;
	}
	
	  @Override
	  public void onDestroy() {
	    processingThread.stopThread();
	  }
}
