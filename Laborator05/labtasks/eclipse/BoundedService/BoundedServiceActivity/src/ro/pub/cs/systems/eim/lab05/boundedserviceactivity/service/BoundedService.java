package ro.pub.cs.systems.eim.lab05.boundedserviceactivity.service;

import java.util.Random;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import ro.pub.cs.systems.eim.lab05.boundedserviceactivity.general.Constants;

public class BoundedService extends Service {

	  final private IBinder boundedServiceBinder = new BoundedServiceBinder();
	  
	  public class BoundedServiceBinder extends Binder {
	    public BoundedService getService() {
	      return BoundedService.this;
	    }
	  }

    // TODO: exercise 9a - implement a IBinder public class to provide a reference
    // to the service instance through a getService() method

    // TODO: exercise 9f - override the lifecycle callback methods and log a message
    // of the moment they are invoked

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(Constants.TAG, "onBind() method was invoked");
        return boundedServiceBinder;
    }

    public String getMessage() {
        return Constants.MESSAGES.get(0);
    }
}
