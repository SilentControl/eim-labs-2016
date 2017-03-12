package ro.pub.cs.systems.pdsd.practicaltest02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import ro.pub.cs.systems.pdsd.practicaltest02.Constants;
import ro.pub.cs.systems.pdsd.practicaltest02.Utilities;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

public class ClientThread extends Thread {
	
	private String   address;
	private int      port;
	private String   key;
	private String   value;
	private EditText valshow;
	public int type_req;
	
	private Socket   socket;
	
	public ClientThread(
			String address,
			int port,
			String key,
			String value,
			int type_req,
			EditText valshow) {
		this.address                 = address;
		this.port                    = port;
		this.key                    = key;
		this.value         = value;
		this.valshow = valshow;
		this.type_req = type_req;
	}
	
	@Override
	public void run() {
		try {
			socket = new Socket(address, port);
			if (socket == null) {
				Log.e(Constants.TAG, "[CLIENT THREAD] Could not create socket!");
			}
			
			BufferedReader bufferedReader = Utilities.getReader(socket);
			PrintWriter    printWriter    = Utilities.getWriter(socket);
			if (bufferedReader != null && printWriter != null) {
				
				if(type_req == 1) // put
				{
					printWriter.println("put," + this.key + "," + value);
					printWriter.flush();
					
					Log.d("CEVA", "Send " + "put," + this.key + "," + value);
				}
				
				else
				if(type_req == 0) // get
				{
					printWriter.println("get," + this.key);
					printWriter.flush();
					
					Log.d("CEVA", "Send " + "get," + this.key);
					
					String info;
					while ((info = bufferedReader.readLine()) != null) {
						final String finalinfo = info;
						valshow.post(new Runnable() {
							@Override
							public void run() {
								valshow.append(finalinfo + "\n");
							}
						});
					}					
				}


			} else {
				Log.e(Constants.TAG, "[CLIENT THREAD] BufferedReader / PrintWriter are null!");
			}
			socket.close();
		} catch (IOException ioException) {
			Log.e(Constants.TAG, "[CLIENT THREAD] An exception has occurred: " + ioException.getMessage());
			if (Constants.DEBUG) {
				ioException.printStackTrace();
			}
		}
	}

}
