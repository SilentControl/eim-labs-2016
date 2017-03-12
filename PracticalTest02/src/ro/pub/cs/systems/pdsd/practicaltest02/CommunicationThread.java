package ro.pub.cs.systems.pdsd.practicaltest02;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/*import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;*/


import ro.pub.cs.systems.pdsd.practicaltest02.Constants;
import ro.pub.cs.systems.pdsd.practicaltest02.Utilities;
import ro.pub.cs.systems.pdsd.practicaltest02.WeatherForecastInformation;
import android.util.Log;

public class CommunicationThread extends Thread
{
	
	private ServerThread serverThread;
	private Socket       socket;
	private HashMap<String, ServData> data = null;
	
	public CommunicationThread(ServerThread serverThread, Socket socket, HashMap<String, ServData> data)
	{
		this.serverThread = serverThread;
		this.socket       = socket;
		this.data = data;
	}
	
	@Override
	public void run()
	{
		if (socket != null)
		{
				BufferedReader bufferedReader = null;
				try {
					bufferedReader = Utilities.getReader(socket);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				PrintWriter printWriter = null;
				try {
					printWriter = Utilities.getWriter(socket);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (bufferedReader != null && printWriter != null)
				{
					Log.i(Constants.TAG, "[COMMUNICATION THREAD] Waiting for parameters from client (city / information type)!");
					String req = null;
					try {
						req = bufferedReader.readLine();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					String result = new String("Placeholder");
					if (req != null && !req.isEmpty())
					{
							Log.i(Constants.TAG, "[COMMUNICATION THREAD] Getting the information from the webservice...");
							
	
							HttpClient httpClient = new DefaultHttpClient();
							HttpGet httpGet = new HttpGet("http://www.timeapi.org/utc/now");
							ResponseHandler<String> handler = new BasicResponseHandler();
							String pageSourceCode = null;
							try {
								pageSourceCode = httpClient.execute(httpGet, handler);
							} catch (ClientProtocolException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							String min = null;
							
							if(pageSourceCode != null)
							{
								min = pageSourceCode.substring(14, 16);
							}
														

							StringTokenizer st2 = new StringTokenizer(req, ",");
							String tip = st2.nextToken();
							String key_text = st2.nextToken();
							String val_text = "Empty";
							
							
							
							if(tip.equals("put"))
							{
								val_text = st2.nextToken();
								data.put(key_text, new ServData(val_text, Integer.parseInt(min)));
							}
							
							
							if(tip.equals("get"))
							{
								ServData retr = data.get(key_text);
								if(retr != null)
								{
								if(Integer.parseInt(min) - retr.timestamp > 1)
								{
									result = "too late";
								}
								
								else
								{
									result = "Val: " + retr.val;
								}
								}
								
								else
								{
									result += "none";
								}
							}
							
							

							result += " " + pageSourceCode + "  " + data.size() + "\n";
							//result = "Helo, " + req + "\n";
							printWriter.println(result);
							printWriter.flush();
					
					}
					else
					{
						Log.e(Constants.TAG, "[COMMUNICATION THREAD] Error receiving parameters from client (city / information type)!");
					}
				}
				
				else
				{
					Log.e(Constants.TAG, "[COMMUNICATION THREAD] BufferedReader / PrintWriter are null!");
				}
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

		}
		else 
		{
			Log.e(Constants.TAG, "[COMMUNICATION THREAD] Socket is null!");
		}
	}
}
