
package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


public class Command {


	public static final String COMMAND_UPDATE = "UPDATE";

	public static final String COMMAND_CONNECT = "CONNECT";

	public static final String COMMAND_DISCONNECT = "DISCONNECT";

	public static final String COMMAND_GET = "GET-STATE";
	

	private String command;
	

	private String[] data;

	public Command(String command, String... data) {
		this.command = command;
		this.data = data;
	}

	public String send(String host, int port) {
		
		String data = getOutput(), response = "";
		try {
			
			// Write the response
			Socket s = new Socket(host, port);
			PrintWriter writer = new PrintWriter(s.getOutputStream());
			writer.println(data);
			writer.flush();

			// Get the response
			BufferedReader br = new BufferedReader(new InputStreamReader(
					s.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				response += line + "\n";
			}
			if (!response.isEmpty()) {
				response = response.substring(0, response.length() - 1);
			}
			s.close();
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return response;
	}

	public String getOutput() {
		
		String out = command;
		
		// Add the lines until the first null value
		int n = data == null? 0 : data.length;
		for (int i = 0; i < n; i ++) {
			if (data[i] == null) {
				break;
			}
			out += "\n" + data[i];
		}
		
		return out;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String[] getData() {
		return data;
	}

	public void setData(String[] data) {
		this.data = data;
	}
}
